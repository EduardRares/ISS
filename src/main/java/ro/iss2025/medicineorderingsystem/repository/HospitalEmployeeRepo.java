package ro.iss2025.medicineorderingsystem.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Privilege;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class HospitalEmployeeRepo implements HospitalEmployeeRepoInterface {
    private static final Logger logger= LogManager.getLogger();
    private JdbcUtils dbUtils;
    public HospitalEmployeeRepo(Properties props) {
        logger.info("Initializing HospitalEmployeeRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(HospitalEmployee entity) {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("insert into employee(email, password, privilege) values(?,?,?)")) {
            preStmt.setString(1, entity.getEmail());
            preStmt.setString(2, entity.getPassword());
            preStmt.setString(3, entity.getPrivilege().toString());
            preStmt.executeUpdate();
            logger.info("Employee added successfully");
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void update(HospitalEmployee entity) {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("update employee set email=?, password=?, privilege=? where id=?")) {
            preStmt.setString(1, entity.getEmail());
            preStmt.setString(2, entity.getPassword());
            preStmt.setString(3, entity.getPrivilege().toString());
            preStmt.setInt(4, entity.getId());
            preStmt.executeUpdate();
            logger.info("Employee updated successfully");
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void delete(HospitalEmployee entity) {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("delete from employee where id=?")) {
            preStmt.setInt(1, entity.getId());
            preStmt.executeUpdate();
            logger.info("Employee deleted successfully");
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public List<HospitalEmployee> findAll() {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        List<HospitalEmployee> hospitalEmployees = new ArrayList<>();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from employee")) {
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String privilege = rs.getString("privilege");
                    HospitalEmployee hospitalEmployee = new HospitalEmployee(email, password);
                    if("ADMIN".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.ADMIN);
                    else if("MEDICAL_STAFF".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.MEDICAL_STAFF);
                    else if("PHARMACIST".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.PHARMACIST);
                    hospitalEmployee.setId(id);
                    hospitalEmployees.add(hospitalEmployee);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(hospitalEmployees);
        return hospitalEmployees;
    }

    @Override
    public HospitalEmployee findById(int id) {
        return null;
    }

    @Override
    public Optional<HospitalEmployee> findByCredentials(HospitalEmployee hospitalEmployee) {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from employee where email = ? and password = ?")) {
            preStmt.setString(1, hospitalEmployee.getEmail());
            preStmt.setString(2, hospitalEmployee.getPassword());
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String privilege = rs.getString("privilege");
                    hospitalEmployee.setId(id);
                    if("ADMIN".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.ADMIN);
                    else if("MEDICAL_STAFF".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.MEDICAL_STAFF);
                    else if("PHARMACIST".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.PHARMACIST);
                    logger.traceExit(hospitalEmployee);
                    return Optional.of(hospitalEmployee);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(hospitalEmployee);
        return Optional.empty();
    }
}
