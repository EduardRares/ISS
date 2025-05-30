package ro.iss2025.medicineorderingsystem.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.iss2025.medicineorderingsystem.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Medicine medicine;
    private HospitalEmployee employee;
    private Order order;

    @BeforeEach
    void setUp() {
        medicine = new Medicine("Ibuprofen", "Analgesic", "Pain relief");
        employee = new HospitalEmployee("john.doe@hospital.com", "password", Privilege.PHARMACIST);
        order = new Order();
    }

    @Test
    void testDefaultConstructor() {
        assertNull(order.getMedicine());
        assertNull(order.getEmployee());
        assertNull(order.getQuantity());
        assertNull(order.getStatus());
        assertNull(order.getOrderDate());
        assertNull(order.getId());
    }

    @Test
    void testParameterizedConstructor_AllFields() {
        Integer quantity = 10;
        Status status = Status.PENDING;
        LocalDateTime orderDate = LocalDateTime.now();

        Order fullOrder = new Order(medicine, employee, quantity, status, orderDate);

        assertSame(medicine, fullOrder.getMedicine());
        assertSame(employee, fullOrder.getEmployee());
        assertEquals(quantity, fullOrder.getQuantity());
        assertEquals(status, fullOrder.getStatus());
        assertEquals(orderDate, fullOrder.getOrderDate());
        assertNull(fullOrder.getId());
    }

    @Test
    void testParameterizedConstructor_MedicineQuantityDate() {
        Integer quantity = 5;
        LocalDateTime orderDate = LocalDateTime.now().minusDays(1);

        Order partialOrder = new Order(medicine, quantity, orderDate);

        assertSame(medicine, partialOrder.getMedicine());
        assertEquals(quantity, partialOrder.getQuantity());
        assertEquals(orderDate, partialOrder.getOrderDate());
        assertNull(partialOrder.getEmployee());
        assertNull(partialOrder.getStatus());
        assertNull(partialOrder.getId());
    }

    @Test
    void testGetAndSetMedicine() {
        order.setMedicine(medicine);
        assertSame(medicine, order.getMedicine());
    }

    @Test
    void testGetAndSetEmployee() {
        order.setEmployee(employee);
        assertSame(employee, order.getEmployee());
    }

    @Test
    void testGetQuantity_IsSetByConstructor() {
        Integer expectedQuantity = 25;
        Order orderWithQuantity = new Order(medicine, expectedQuantity, LocalDateTime.now());
        assertEquals(expectedQuantity, orderWithQuantity.getQuantity());
    }

    @Test
    void testGetAndSetStatus() {
        Status expectedStatus = Status.DELIVERED;
        order.setStatus(expectedStatus);
        assertEquals(expectedStatus, order.getStatus());
    }

    @Test
    void testGetAndSetOrderDate() {
        LocalDateTime expectedDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        order.setOrderDate(expectedDate);
        assertEquals(expectedDate, order.getOrderDate());
    }

    @Test
    void testGetAndSetId() {
        Integer expectedId = 101;
        order.setId(expectedId);
        assertEquals(expectedId, order.getId());
    }

    @Test
    void testCompareTo() {
        Order order1 = new Order();
        order1.setId(10);

        HospitalEmployee employeeComparing = new HospitalEmployee();
        employeeComparing.setId(5);
        assertTrue(order1.compareTo(employeeComparing) > 0);

        employeeComparing.setId(10);
        assertEquals(0, order1.compareTo(employeeComparing));

        employeeComparing.setId(15);
        assertTrue(order1.compareTo(employeeComparing) < 0);
    }

    @Test
    void testCompareTo_OrderIdNull_ThrowsNullPointerException() {
        Order orderWithNullId = new Order();
        HospitalEmployee employee = new HospitalEmployee();
        employee.setId(1);

        assertThrows(NullPointerException.class, () -> {
            orderWithNullId.compareTo(employee);
        });
    }

    @Test
    void testCompareTo_EmployeeIdNull_ThrowsNullPointerException() {
        Order order1 = new Order();
        order1.setId(10);
        HospitalEmployee employeeWithNullId = new HospitalEmployee();

        assertThrows(NullPointerException.class, () -> {
            order1.compareTo(employeeWithNullId);
        });
    }
}