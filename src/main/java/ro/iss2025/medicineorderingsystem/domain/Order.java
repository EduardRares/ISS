package ro.iss2025.medicineorderingsystem.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
public class Order implements ro.iss2025.medicineorderingsystem.domain.Entity<Integer>, Comparable<HospitalEmployee>, Serializable {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicineid", nullable = false)
    private Medicine medicine;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeid", nullable = false)
    private HospitalEmployee employee;
    @Column(name = "quantity")
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "orderDate")
    private LocalDateTime orderDate;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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

    public Order() {}

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

    @Override
    public int compareTo(HospitalEmployee o) {
        return this.id.compareTo(o.getId());
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }
}
