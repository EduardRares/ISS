package ro.iss2025.medicineorderingsystem.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order extends Entity<Integer> {
    private Medicine medicine;
    private HospitalEmployee employee;
    private Integer quantity;
    private Status status;
    private LocalDateTime orderDate;
    public Order(Medicine medicine, HospitalEmployee hospitalEmployee, Integer quantity, Status status, LocalDateTime orderDate) {
        this.medicine = medicine;
        this.employee = hospitalEmployee;
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order(Medicine medicine, Integer quantity, LocalDateTime orderDate) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public HospitalEmployee getEmployee() {
        return employee;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setEmployee(HospitalEmployee employee) {
        this.employee = employee;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
