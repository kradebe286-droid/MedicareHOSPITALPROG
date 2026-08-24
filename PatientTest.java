package com.mycompany.medicarehospital;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PatientTest {

    private PatientManager manager;

    @BeforeEach
    public void setUp() {
        manager = new PatientManager();
    }

    @Test
    public void testRegisterPatient() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        boolean result = manager.registerPatient(patient);

        assertTrue(result);
        assertEquals(1, manager.getPatientCount());
    }

    @Test
    public void testSearchPatient() {

        Patient patient = new Patient(
                "P002",
                "Mary",
                "Jones",
                25,
                "Female",
                "Fever",
                PatientCategory.EMERGENCY
        );

        manager.registerPatient(patient);

        Patient result = manager.searchPatient("P002");

        assertNotNull(result);
        assertEquals("Mary", result.getFirstName());
    }

    @Test
    public void testUpdatePatient() {

        Patient patient = new Patient(
                "P003",
                "Peter",
                "Brown",
                40,
                "Male",
                "Headache",
                PatientCategory.OUTPATIENT
        );

        manager.registerPatient(patient);

        boolean result = manager.updatePatient(
                "P003",
                "Peter",
                "Green",
                41,
                "Male",
                "Back Pain",
                PatientCategory.OUTPATIENT
        );

        assertTrue(result);

        Patient updated = manager.searchPatient("P003");

        assertEquals("Green", updated.getLastName());
        assertEquals(41, updated.getAge());
        assertEquals("Back Pain",
                updated.getMedicalCondition());
    }

    @Test
    public void testDeletePatient() {

        Patient patient = new Patient(
                "P004",
                "Sarah",
                "Williams",
                28,
                "Female",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        manager.registerPatient(patient);

        boolean result = manager.deletePatient("P004");

        assertTrue(result);
        assertNull(manager.searchPatient("P004"));
    }

    @Test
    public void testDuplicatePatientID() {

        Patient patient1 = new Patient(
                "P005",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P005",
                "Peter",
                "Brown",
                40,
                "Male",
                "Fever",
                PatientCategory.EMERGENCY
        );

        assertTrue(manager.registerPatient(patient1));
        assertFalse(manager.registerPatient(patient2));
    }

    @Test
    public void testSortBySurname() {

        Patient patient1 = new Patient(
                "P002",
                "John",
                "Zulu",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Mary",
                "Adams",
                25,
                "Female",
                "Fever",
                PatientCategory.OUTPATIENT
        );

        manager.registerPatient(patient1);
        manager.registerPatient(patient2);

        manager.sortBySurname();

        assertEquals(
                "Adams",
                manager.getPatients().get(0).getLastName()
        );
    }

    @Test
    public void testSortByPatientID() {

        Patient patient1 = new Patient(
                "P002",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Mary",
                "Jones",
                25,
                "Female",
                "Fever",
                PatientCategory.OUTPATIENT
        );

        manager.registerPatient(patient1);
        manager.registerPatient(patient2);

        manager.sortByPatientID();

        assertEquals(
                "P001",
                manager.getPatients().get(0).getPatientID()
        );
    }
}