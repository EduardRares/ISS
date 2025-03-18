package ro.iss2025.medicineorderingsystem.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.iss2025.medicineorderingsystem.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class OrderRepo implements OrderRepoInterface{
    private static final Logger logger= LogManager.getLogger();
    private JdbcUtils dbUtils;
    public OrderRepo(Properties props) {
        logger.info("Initializing OrderRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Order entity) {
        logger.traceEntry("saving order {} ", entity);
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("insert into 'order'(medicineId, employeeId, quantity, status, orderDate) values (?, ?, ?, ?, ?)")) {
            preStmt.setInt(1, entity.getMedicine().getId());
            preStmt.setInt(2, entity.getEmployee().getId());
            preStmt.setInt(3, entity.getQuantity());
            preStmt.setString(4, entity.getStatus().toString());
            preStmt.setDate(5, new java.sql.Date(entity.getOrderDate().toLocalDate().toEpochDay()));
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void update(Order entity) {
        logger.traceEntry("updating order {} ", entity);
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("update 'order' set status = ? where id=?")) {
            preStmt.setString(1, entity.getStatus().toString());
            preStmt.setInt(2, entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void delete(Order entity) {
        logger.traceEntry("deleting order {} ", entity);
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("delete from 'order' where id=?")) {
            preStmt.setInt(1, entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public List<Order> findAll() {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from 'order'")) {
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int medicineId = rs.getInt("medicineId");
                    int employeeId = rs.getInt("employeeId");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                    Medicine medicine = findMedicineById(medicineId);
                    HospitalEmployee employee = findEmployeeById(employeeId);
                    Order order = new Order(medicine, quantity, orderDate);
                    if("PENDING".equals(status)) order.setStatus(Status.PENDING);
                    if("DELIVERED".equals(status)) order.setStatus(Status.DELIVERED);
                    if("REJECTED".equals(status)) order.setStatus(Status.REJECTED);
                    order.setEmployee(employee);
                    order.setId(id);
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(orders);
        return orders;
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public Medicine findMedicineById(Integer medicineId) {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from medicine where id=?")) {
            preStmt.setInt(1, medicineId);
            try(ResultSet rs = preStmt.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    Medicine medicine = new Medicine(name, type, description);
                    medicine.setId(id);
                    return medicine;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        return null;
    }

    public HospitalEmployee findEmployeeById(Integer employeeId) {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from employee where id = ?")) {
            preStmt.setInt(1, employeeId);
            try(ResultSet rs = preStmt.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String privilege = rs.getString("privilege");
                    HospitalEmployee hospitalEmployee = new HospitalEmployee(email, password);
                    if("ADMIN".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.ADMIN);
                    else if("MEDICAL_STAFF".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.MEDICAL_STAFF);
                    else if("PHARMACIST".equals(privilege)) hospitalEmployee.setPrivilege(Privilege.PHARMACIST);
                    hospitalEmployee.setId(id);
                    return hospitalEmployee;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        return null;
    }

    @Override
    public List<Order> findPendingOrders() {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from 'order' where status = 'PENDING'")) {
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int medicineId = rs.getInt("medicineId");
                    int employeeId = rs.getInt("employeeId");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                    Medicine medicine = findMedicineById(medicineId);
                    HospitalEmployee employee = findEmployeeById(employeeId);
                    Order order = new Order(medicine, quantity, orderDate);
                    order.setStatus(Status.PENDING);
                    order.setEmployee(employee);
                    order.setId(id);
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(orders);
        return orders;
    }

    @Override
    public List<Order> findProcessedOrders() {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from 'order' where status != 'PENDING'")) {
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int medicineId = rs.getInt("medicineId");
                    int employeeId = rs.getInt("employeeId");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                    Medicine medicine = findMedicineById(medicineId);
                    HospitalEmployee employee = findEmployeeById(employeeId);
                    Order order = new Order(medicine, quantity, orderDate);
                    if("DELIVERED".equals(status)) order.setStatus(Status.DELIVERED);
                    if("REJECTED".equals(status)) order.setStatus(Status.REJECTED);
                    order.setEmployee(employee);
                    order.setId(id);
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(orders);
        return orders;
    }
}
