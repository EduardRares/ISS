package ro.iss2025.medicineorderingsystem.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;
import ro.iss2025.medicineorderingsystem.domain.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try(PreparedStatement preStmt = conn.prepareStatement("insert into order(medicineId, quantity, status) values (?, ?, ?)")) {
            preStmt.setInt(1, entity.getMedicine().getId());
            preStmt.setInt(2, entity.getQuantity());
            preStmt.setString(3, entity.getStatus().toString());
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
        try(PreparedStatement preStmt = conn.prepareStatement("update order set status = ? where id=?")) {
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

    }

    @Override
    public List<Order> findAll() {
        logger.traceEntry();
        Connection conn= dbUtils.getConnection();
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from order")) {
            try(ResultSet rs = preStmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int medicineId = rs.getInt("medicineId");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    Medicine medicine = findMedicineById(medicineId);
                    Order order = new Order(medicine, quantity);
                    if("PENDING".equals(status)) order.setStatus(Status.PENDING);
                    if("DELIVERED".equals(status)) order.setStatus(Status.DELIVERED);
                    if("REJECTED".equals(status)) order.setStatus(Status.REJECTED);
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
}
