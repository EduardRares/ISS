package ro.iss2025.medicineorderingsystem.repository;

import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;

public interface OrderRepoInterface extends Repository<Integer, Order> {
    public  Medicine findMedicineById(Integer medicineId);
}
