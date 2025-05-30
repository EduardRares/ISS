package ro.iss2025.medicineorderingsystem.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Privilege;

import static org.junit.jupiter.api.Assertions.*;

class HospitalEmployeeTest {

    private HospitalEmployee employee;

    @BeforeEach
    void setUp() {
        employee = new HospitalEmployee(); // For tests that need a fresh instance
    }

    @Test
    void testDefaultConstructor() {
        HospitalEmployee defaultEmployee = new HospitalEmployee();
        assertEquals("", defaultEmployee.getEmail(), "Default constructor should initialize email to empty string.");
        assertEquals("", defaultEmployee.getPassword(), "Default constructor should initialize password to empty string.");
        assertNull(defaultEmployee.getPrivilege(), "Default constructor should initialize privilege to null.");
        assertNull(defaultEmployee.getId(), "Default constructor should initialize id to null.");
    }

    @Test
    void testParameterizedConstructor_AllFields() {
        String email = "test@example.com";
        String password = "password123";
        Privilege privilege = Privilege.ADMIN;

        HospitalEmployee fullEmployee = new HospitalEmployee(email, password, privilege);

        assertEquals(email, fullEmployee.getEmail(), "Constructor should set email correctly.");
        assertEquals(password, fullEmployee.getPassword(), "Constructor should set password correctly.");
        assertEquals(privilege, fullEmployee.getPrivilege(), "Constructor should set privilege correctly.");
        assertNull(fullEmployee.getId(), "ID should be null initially unless set by persistence.");
    }

    @Test
    void testParameterizedConstructor_EmailPasswordOnly() {
        String email = "user@example.com";
        String password = "securepassword";

        HospitalEmployee partialEmployee = new HospitalEmployee(email, password);

        assertEquals(email, partialEmployee.getEmail(), "Constructor should set email correctly.");
        assertEquals(password, partialEmployee.getPassword(), "Constructor should set password correctly.");
        assertNull(partialEmployee.getPrivilege(), "Privilege should be null for this constructor.");
        assertNull(partialEmployee.getId(), "ID should be null initially unless set by persistence.");
    }

    @Test
    void testGetAndSetEmail() {
        String expectedEmail = "new.email@example.com";
        employee.setEmail(expectedEmail);
        assertEquals(expectedEmail, employee.getEmail());
    }

    @Test
    void testGetAndSetPassword() {
        String expectedPassword = "newPassword!@#";
        employee.setPassword(expectedPassword);
        assertEquals(expectedPassword, employee.getPassword());
    }

    @Test
    void testGetAndSetPrivilege() {
        Privilege expectedPrivilege = Privilege.MEDICAL_STAFF;
        employee.setPrivilege(expectedPrivilege);
        assertEquals(expectedPrivilege, employee.getPrivilege());
    }

    @Test
    void testGetAndSetId() {
        Integer expectedId = 201;
        employee.setId(expectedId);
        assertEquals(expectedId, employee.getId());
    }

    @Test
    void testCompareTo() {
        HospitalEmployee employee1 = new HospitalEmployee();
        employee1.setId(10);

        HospitalEmployee employee2 = new HospitalEmployee();
        employee2.setId(5); // employee2 id < employee1 id
        assertTrue(employee1.compareTo(employee2) > 0, "Employee1 ID 10 vs Employee2 ID 5 should be positive.");

        employee2.setId(10); // employee2 id == employee1 id
        assertEquals(0, employee1.compareTo(employee2), "Employee1 ID 10 vs Employee2 ID 10 should be zero.");

        employee2.setId(15); // employee2 id > employee1 id
        assertTrue(employee1.compareTo(employee2) < 0, "Employee1 ID 10 vs Employee2 ID 15 should be negative.");
    }

    @Test
    void testCompareTo_ThisIdNull_ThrowsNullPointerException() {
        HospitalEmployee employeeWithNullId = new HospitalEmployee(); // ID is null by default
        HospitalEmployee otherEmployee = new HospitalEmployee();
        otherEmployee.setId(1);

        // The current implementation this.id.compareTo(o.getId()) will throw NPE if this.id is null.
        assertThrows(NullPointerException.class, () -> {
            employeeWithNullId.compareTo(otherEmployee);
        }, "Comparing with a null 'this.id' should throw NullPointerException.");
    }

    @Test
    void testCompareTo_OtherIdNull_ThrowsNullPointerException() {
        HospitalEmployee employee1 = new HospitalEmployee();
        employee1.setId(10);
        HospitalEmployee otherEmployeeWithNullId = new HospitalEmployee(); // other.getId() will be null

        // The current implementation this.id.compareTo(o.getId()) will throw NPE if o.getId() is null.
        assertThrows(NullPointerException.class, () -> {
            employee1.compareTo(otherEmployeeWithNullId);
        }, "Comparing with an employee having null ID should throw NullPointerException.");
    }
}
