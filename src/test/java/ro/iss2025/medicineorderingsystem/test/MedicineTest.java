package ro.iss2025.medicineorderingsystem.test;

import org.junit.jupiter.api.Test;
import ro.iss2025.medicineorderingsystem.domain.Medicine;

import static org.junit.jupiter.api.Assertions.*;

class MedicineTest {

    @Test
    void testDefaultConstructor() {
        Medicine medicine = new Medicine();
        assertEquals("", medicine.getName());
        assertEquals("", medicine.getType());
        assertEquals("", medicine.getDescription());
        assertEquals(-1, medicine.getId()); // Default ID is -1
    }

    @Test
    void testParameterizedConstructor() {
        String name = "Paracetamol";
        String type = "Analgesic";
        String description = "Relieves pain and fever";
        Medicine medicine = new Medicine(name, type, description);
        assertEquals(name, medicine.getName());
        assertEquals(type, medicine.getType());
        assertEquals(description, medicine.getDescription());
        assertEquals(-1, medicine.getId()); // Default ID is -1 even with this constructor
    }

    @Test
    void testGetNameAndSetName() {
        Medicine medicine = new Medicine();
        String expectedName = "Aspirin";
        medicine.setName(expectedName);
        assertEquals(expectedName, medicine.getName());
    }

    @Test
    void testGetTypeAndSetType() {
        Medicine medicine = new Medicine();
        String expectedType = "NSAID";
        medicine.setType(expectedType);
        assertEquals(expectedType, medicine.getType());
    }

    @Test
    void testGetDescriptionAndSetDescription() {
        Medicine medicine = new Medicine();
        String expectedDescription = "Anti-inflammatory";
        medicine.setDescription(expectedDescription);
        assertEquals(expectedDescription, medicine.getDescription());
    }

    @Test
    void testGetIdAndSetId() {
        Medicine medicine = new Medicine();
        Integer expectedId = 101;
        medicine.setId(expectedId);
        assertEquals(expectedId, medicine.getId());
    }

    @Test
    void testCompareTo() {
        Medicine medicine1 = new Medicine();
        Medicine medicine2 = new Medicine();
        Medicine medicine3 = new Medicine();

        medicine1.setId(1);
        medicine2.setId(2);
        medicine3.setId(1);

        assertTrue(medicine1.compareTo(medicine2) < 0);
        assertTrue(medicine2.compareTo(medicine1) > 0);
        assertEquals(0, medicine1.compareTo(medicine3));
    }

    @Test
    void testToString() {
        String name = "Ibuprofen";
        String type = "NSAID";
        String description = "Pain reliever and fever reducer";
        Medicine medicine = new Medicine(name, type, description);
        String expectedToString = "Medicine{name=Ibuprofen, type=NSAID, description=Pain reliever and fever reducer}";
        assertEquals(expectedToString, medicine.toString());
    }
}
