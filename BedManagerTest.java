package com.mycompany.medicarehospital;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BedManagerTest {

    private BedManager bedManager;

    @BeforeEach
    public void setUp() {
        bedManager = new BedManager();
    }

    @Test
    public void testAllocateBed() {

        Inpatient patient = createPatient("P001");

        boolean result =
                bedManager.allocateBed(patient, "B01");

        assertTrue(result);
        assertEquals(1,
                bedManager.getOccupiedBedCount());

        assertEquals(
                "B01",
                patient.getBedNumber());
    }

    @Test
    public void testReleaseBed() {

        Inpatient patient = createPatient("P002");

        bedManager.allocateBed(patient, "B02");

        boolean result =
                bedManager.releaseBed("B02");

        assertTrue(result);

        assertEquals(
                0,
                bedManager.getOccupiedBedCount());
    }

    @Test
    public void testPreventOccupiedBedAllocation() {

        Inpatient patient1 = createPatient("P003");
        Inpatient patient2 = createPatient("P004");

        assertTrue(
                bedManager.allocateBed(patient1, "B03"));

        assertFalse(
                bedManager.allocateBed(patient2, "B03"));
    }

    @Test
    public void testPreventAllBedsOccupied() {

        for (int i = 1; i <= 20; i++) {

            Inpatient patient =
                    createPatient("P" + i);

            String bedNumber =
                    String.format("B%02d", i);

            assertTrue(
                    bedManager.allocateBed(
                            patient,
                            bedNumber));
        }

        assertEquals(
                20,
                bedManager.getOccupiedBedCount());

        assertTrue(
                bedManager.areAllBedsOccupied());

        Inpatient extraPatient =
                createPatient("P21");

        assertFalse(
                bedManager.allocateBed(
                        extraPatient,
                        "B01"));
    }

    @Test
    public void testPreventSamePatientFromHavingTwoBeds() {

        Inpatient patient = createPatient("P005");

        assertTrue(
                bedManager.allocateBed(
                        patient,
                        "B05"));

        assertFalse(
                bedManager.allocateBed(
                        patient,
                        "B06"));
    }

    @Test
    public void testOccupancyPercentage() {

        Inpatient patient = createPatient("P006");

        bedManager.allocateBed(
                patient,
                "B06");

        assertEquals(
                5.0,
                bedManager.getOccupancyPercentage());
    }

    private Inpatient createPatient(String id) {

        return new Inpatient(
                id,
                "Test",
                "Patient",
                30,
                "Male",
                "Flu",
                PatientCategory.INPATIENT,
                "Ward 1",
                "Not Allocated"
        );
    }
}