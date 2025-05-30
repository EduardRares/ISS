package ro.iss2025.medicineorderingsystem.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(name = "employee")
public class HospitalEmployee implements ro.iss2025.medicineorderingsystem.domain.Entity<Integer>, Comparable<HospitalEmployee>, Serializable {
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "privilege")
    private Privilege privilege;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public HospitalEmployee(String email, String password, Privilege privilege) {
        this.email = email;
        this.password = password;
        this.privilege = privilege;
    }

    public HospitalEmployee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public HospitalEmployee() {
        email = "";
        password = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }

    @Override
    public int compareTo(HospitalEmployee o) {
        return this.id.compareTo(o.getId());
    }
}
