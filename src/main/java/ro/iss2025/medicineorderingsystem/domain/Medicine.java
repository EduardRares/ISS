package ro.iss2025.medicineorderingsystem.domain;

public class Medicine extends Entity<Integer>{
    private String name;
    private String type;
    private String description;

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
}
