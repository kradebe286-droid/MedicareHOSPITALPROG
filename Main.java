package com.mycompany.medicarehospital;

import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final HospitalSystem hospital = new HospitalSystem();

    public static void main(String[] args) {

        int choice;

        do {
            displayMenu();
            choice = getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> registerPatient();

                case 2 -> searchPatient();

                case 3 -> updatePatient();

                case 4 -> deletePatient();

                case 5 -> hospital.getPatientManager().displayAllPatients();

                case 6 -> allocateBed();

                case 7 -> releaseBed();

                case 8 -> hospital.getBedManager().displayWardLayout();

                case 9 -> hospital.getBedManager().displayAvailableBeds();

                case 10 -> hospital.getBedManager().displayOccupiedBeds();

                case 11 -> hospital.generateReport();

                case 12 -> sortPatients();

                case 0 -> System.out.println("Thank you for using MediCare Hospital System.");

                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    private static void displayMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       MEDICARE HOSPITAL SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Allocate Bed");
        System.out.println("7. Release Bed");
        System.out.println("8. Display Ward Layout");
        System.out.println("9. Display Available Beds");
        System.out.println("10. Display Occupied Beds");
        System.out.println("11. Generate Report");
        System.out.println("12. Sort Patients");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    private static void registerPatient() {

        System.out.println();
        System.out.println("========== REGISTER PATIENT ==========");

        String id = getString("Patient ID: ");

        if (hospital.getPatientManager().searchPatient(id) != null) {
            System.out.println("Patient ID already exists.");
            return;
        }

        String fName = getString("First Name: ");
        String lName = getString("Last Name: ");
        int age = getInt("Age: ");
        String gender = getString("Gender: ");
        String condition = getString("Medical Condition: ");

        PatientCategory category = getCategory();

        Patient patient;

        if (category == PatientCategory.INPATIENT) {

            patient = new Inpatient(
                    id,
                    fName,
                    lName,
                    age,
                    gender,
                    condition,
                    category,
                    "Ward 1",
                    "Not Allocated"
            );

        } else {

            patient = new Patient(
                    id,
                    fName,
                    lName,
                    age,
                    gender,
                    condition,
                    category
            );
        }

        if (hospital.getPatientManager().registerPatient(patient)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Patient could not be registered.");
        }
    }

    private static PatientCategory getCategory() {

        System.out.println();
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");

        int choice = getInt("Enter category: ");

        switch (choice) {
            case 1 -> {
                return PatientCategory.INPATIENT;
            }
            case 2 -> {
                return PatientCategory.OUTPATIENT;
            }
            case 3 -> {
                return PatientCategory.EMERGENCY;
            }
            default -> {
                System.out.println("Invalid category. Emergency selected.");
                return PatientCategory.EMERGENCY;
            }
        }
    }

    private static void searchPatient() {

        String id = getString("Enter Patient ID: ");

        Patient patient =
                hospital.getPatientManager().searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
        } else {
            patient.displayDetails();
        }
    }

    private static void updatePatient() {

        System.out.println();
        System.out.println("========== UPDATE PATIENT ==========");

        String id = getString("Enter Patient ID: ");

        Patient patient =
                hospital.getPatientManager().searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        String fName = getString("New First Name: ");
        String lName = getString("New Last Name: ");
        int age = getInt("New Age: ");
        String gender = getString("New Gender: ");
        String condition = getString("New Medical Condition: ");
        PatientCategory category = getCategory();

        boolean updated =
                hospital.getPatientManager().updatePatient(
                        id,
                        fName,
                        lName,
                        age,
                        gender,
                        condition,
                        category
                );

        if (updated) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Patient could not be updated.");
        }
    }

    private static void deletePatient() {

        String id = getString("Enter Patient ID to delete: ");

        Patient patient =
                hospital.getPatientManager().searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Release the bed before deleting the patient
        if (patient instanceof Inpatient inpatient) {


            if (!inpatient.getBedNumber().equals("Not Allocated")) {
                hospital.getBedManager().releaseBed(
                        inpatient.getBedNumber());
            }
        }

        if (hospital.getPatientManager().deletePatient(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient could not be deleted.");
        }
    }

    private static void allocateBed() {

        System.out.println();
        System.out.println("========== ALLOCATE BED ==========");

        String id = getString("Enter Inpatient ID: ");

        Patient patient =
                hospital.getPatientManager().searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (!(patient instanceof Inpatient)) {
            System.out.println("Only Inpatients can be allocated a bed.");
            return;
        }

        Inpatient inpatient = (Inpatient) patient;

        if (!inpatient.getBedNumber().equals("Not Allocated")) {
            System.out.println("This patient already has a bed.");
            return;
        }

        if (hospital.getBedManager().areAllBedsOccupied()) {
            System.out.println("No beds are available.");
            return;
        }

        hospital.getBedManager().displayAvailableBeds();

        String bedNumber =
                getString("Enter bed number (example B01): ");

        if (hospital.getBedManager().allocateBed(
                inpatient, bedNumber)) {

            System.out.println(
                    "Bed " + bedNumber + " allocated successfully.");

        } else {
            System.out.println(
                    "Bed allocation failed.");
        }
    }

    private static void releaseBed() {

        System.out.println();
        System.out.println("========== RELEASE BED ==========");

        String bedNumber =
                getString("Enter bed number: ");

        if (hospital.getBedManager().releaseBed(bedNumber)) {
            System.out.println(
                    "Bed " + bedNumber + " released successfully.");
        } else {
            System.out.println(
                    "Bed could not be released.");
        }
    }

    private static void sortPatients() {

        System.out.println();
        System.out.println("========== SORT PATIENTS ==========");
        System.out.println("1. Sort by Surname");
        System.out.println("2. Sort by Patient ID");

        int choice = getInt("Enter choice: ");

        switch (choice) {
            case 1 -> {
                hospital.getPatientManager().sortBySurname();
                System.out.println("Patients sorted by surname.");
                hospital.getPatientManager().displayAllPatients();
            }
            case 2 -> {
                hospital.getPatientManager().sortByPatientID();
                System.out.println("Patients sorted by Patient ID.");
                hospital.getPatientManager().displayAllPatients();
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static String getString(String message) {

        System.out.print(message);
        return input.nextLine();
    }

    private static int getInt(String message) {

        while (true) {

            try {
                System.out.print(message);
                String value = input.nextLine();

                return Integer.parseInt(value);

            } catch (NumberFormatException e) {

                System.out.println("Please enter a number.");
            }
        }
    }
}