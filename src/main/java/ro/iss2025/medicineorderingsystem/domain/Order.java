package ro.iss2025.medicineorderingsystem.domain;

public class Order extends Entity<Integer> {
    private Medicine medicine;
    private HospitalEmployee employee;
    private Integer quantity;
    private Status status;
    public Order(Medicine medicine, HospitalEmployee hospitalEmployee, Integer quantity, Status status) {
        this.medicine = medicine;
        this.employee = hospitalEmployee;
        this.quantity = quantity;
        this.status = status;
    }

    public Order(Medicine medicine, Integer quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
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
}
