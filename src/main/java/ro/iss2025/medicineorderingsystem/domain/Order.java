package ro.iss2025.medicineorderingsystem.domain;

public class Order extends Entity<Integer> {
    private Medicine medicine;
    private Integer quantity;
    private Status status;
    public Order(Medicine medicine, Integer quantity, Status status) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
