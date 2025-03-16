package ro.iss2025.medicineorderingsystem.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.iss2025.medicineorderingsystem.domain.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MedicineRepo implements MedicineRepoInterface{
    private static final Logger logger= LogManager.getLogger();
    private JdbcUtils dbUtils;
    public MedicineRepo(Properties props) {
        logger.info("Initializing MedicineRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Medicine entity) {

    }

    @Override
    public void update(Medicine entity) {

    }

    @Override
    public void delete(Medicine entity) {

    }

    @Override
    public List<Medicine> findAll() {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        List<Medicine> medicines = new ArrayList<>();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from medicine")) {
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    Medicine medicine = new Medicine(name, type, description);
                    medicine.setId(id);
                    medicines.add(medicine);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(medicines);
        return medicines;
    }

    @Override
    public Medicine findById(int id) {
        return null;
    }
}
