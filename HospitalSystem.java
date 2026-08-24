package com.mycompany.medicarehospital;

public class HospitalSystem {

    private final PatientManager patientManager;
    private final BedManager bedManager;

    public HospitalSystem() {
        patientManager = new PatientManager();
        bedManager = new BedManager();
    }

    public PatientManager getPatientManager() {
        return patientManager;
    }

    public BedManager getBedManager() {
        return bedManager;
    }

    // Display hospital report
    public void generateReport() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("        MEDICARE HOSPITAL REPORT");
        System.out.println("======================================");

        System.out.println(
                "Total Registered Patients: "
                + patientManager.getPatientCount());

        System.out.println(
                "Total Occupied Beds: "
                + bedManager.getOccupiedBedCount());

        System.out.println(
                "Total Available Beds: "
                + bedManager.getAvailableBedCount());

        System.out.printf(
                "Ward Occupancy: %.2f%%%n",
                bedManager.getOccupancyPercentage());

        System.out.println("======================================");
    }
}
