package ro.iss2025.medicineorderingsystem.repository;

import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;

import java.util.List;

public interface OrderRepoInterface extends Repository<Integer, Order> {
    public  Medicine findMedicineById(Integer medicineId);
    public HospitalEmployee findEmployeeById(Integer employeeId);
    public List<Order> findPendingOrders();
    public List<Order> findProcessedOrders();
    public List<Order> findOrdersByEmployeeId(Integer employeeId);
}
