package ro.iss2025.medicineorderingsystem.domain;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "medicine" )
public class Medicine implements ro.iss2025.medicineorderingsystem.domain.Entity<Integer>, Comparable<Medicine>, Serializable {
    @Column( name = "name")
    private String name;
    @Column( name = "type")
    private String type;
    @Column( name = "description")
    private String description;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = -1;

    public Medicine(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Medicine() {
        name = "";
        type = "";
        description = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Medicine{" + "name=" + name + ", type=" + type + ", description=" + description + '}';
    }

    @Override
    public int compareTo(Medicine o) {
        return this.id.compareTo(o.id);
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
