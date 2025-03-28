package ro.iss2025.medicineorderingsystem.domain;

public class HospitalEmployee extends Entity<Integer> {
    private String email;
    private String password;
    private Privilege privilege;

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
}
