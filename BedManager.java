package com.mycompany.medicarehospital;

public class BedManager {

    private final int TOTAL_BEDS = 20;

    private final Inpatient[] beds;

    public BedManager() {
        beds = new Inpatient[TOTAL_BEDS];
    }

    // Allocate a bed
    public boolean allocateBed(Inpatient patient, String bedNumber) {

        if (patient == null) {
            return false;
        }

        if (patient.getCategory() != PatientCategory.INPATIENT) {
            return false;
        }

        int bedIndex = getBedIndex(bedNumber);

        if (bedIndex == -1) {
            return false;
        }

        // Check if requested bed is already occupied
        if (beds[bedIndex] != null) {
            return false;
        }

        // Check if patient already has a bed
        if (hasPatientBed(patient)) {
            return false;
        }

        beds[bedIndex] = patient;

        patient.setBedNumber(bedNumber);
        patient.setWardNumber("Ward 1");

        return true;
    }

    // Release a bed
    public boolean releaseBed(String bedNumber) {

        int bedIndex = getBedIndex(bedNumber);

        if (bedIndex == -1) {
            return false;
        }

        if (beds[bedIndex] == null) {
            return false;
        }

        beds[bedIndex].setBedNumber("Not Allocated");
        beds[bedIndex].setWardNumber("Ward 1");

        beds[bedIndex] = null;

        return true;
    }

    // Check if a patient already has a bed
    private boolean hasPatientBed(Inpatient patient) {

        for (Inpatient inpatient : beds) {

            if (inpatient != null
                    && inpatient.getPatientID().equalsIgnoreCase(
                            patient.getPatientID())) {

                return true;
            }
        }

        return false;
    }

    // Convert B01-B20 to an array index
    private int getBedIndex(String bedNumber) {

        if (bedNumber == null) {
            return -1;
        }

        if (!bedNumber.toUpperCase().startsWith("B")) {
            return -1;
        }

        try {

            int number = Integer.parseInt(
                    bedNumber.substring(1));

            if (number < 1 || number > 20) {
                return -1;
            }

            return number - 1;

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Display ward layout
    public void displayWardLayout() {

        System.out.println();
        System.out.println("========== WARD LAYOUT ==========");

        for (int i = 0; i < TOTAL_BEDS; i++) {

            String bedNumber = String.format("B%02d", i + 1);

            if (beds[i] == null) {
                System.out.print("[" + bedNumber + " Available] ");
            } else {
                System.out.print("[" + bedNumber + " Occupied] ");
            }

            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("=================================");
    }

    // Display available beds
    public void displayAvailableBeds() {

        System.out.println();
        System.out.println("========== AVAILABLE BEDS ==========");

        boolean found = false;

        for (int i = 0; i < TOTAL_BEDS; i++) {

            if (beds[i] == null) {

                System.out.print(
                        String.format("B%02d ", i + 1));

                found = true;
            }
        }

        if (!found) {
            System.out.println("No beds available.");
        } else {
            System.out.println();
        }
    }

    // Display occupied beds
    public void displayOccupiedBeds() {

        System.out.println();
        System.out.println("========== OCCUPIED BEDS ==========");

        boolean found = false;

        for (int i = 0; i < TOTAL_BEDS; i++) {

            if (beds[i] != null) {

                System.out.println(
                        String.format("B%02d - %s %s",
                                i + 1,
                                beds[i].getFirstName(),
                                beds[i].getLastName()));

                found = true;
            }
        }

        if (!found) {
            System.out.println("No beds are occupied.");
        }
    }

    public int getOccupiedBedCount() {

        int count = 0;

        for (Inpatient bed : beds) {

            if (bed != null) {
                count++;
            }
        }

        return count;
    }

    public int getAvailableBedCount() {
        return TOTAL_BEDS - getOccupiedBedCount();
    }

    public double getOccupancyPercentage() {

        return (getOccupiedBedCount()
                / (double) TOTAL_BEDS) * 100;
    }

    public Inpatient getPatientInBed(String bedNumber) {

        int index = getBedIndex(bedNumber);

        if (index == -1) {
            return null;
        }

        return beds[index];
    }

    public boolean areAllBedsOccupied() {
        return getOccupiedBedCount() == TOTAL_BEDS;
    }
}