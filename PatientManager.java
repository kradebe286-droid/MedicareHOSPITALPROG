package com.mycompany.medicarehospital;

import java.util.ArrayList;
import java.util.Comparator;

public class PatientManager {

    private final ArrayList<Patient> patients;

    public PatientManager() {
        patients = new ArrayList<>();
    }

    // Register a new patient
    public boolean registerPatient(Patient patient) {

        if (searchPatient(patient.getPatientID()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    // Search for a patient using Patient ID
    public Patient searchPatient(String patientID) {

        for (Patient patient : patients) {

            if (patient.getPatientID().equalsIgnoreCase(patientID)) {
                return patient;
            }
        }

        return null;
    }

    // Update patient details
    public boolean updatePatient(String patientID, String firstName,
            String lastName, int age, String gender,
            String medicalCondition, PatientCategory category) {

        Patient patient = searchPatient(patientID);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        patient.setCategory(category);

        return true;
    }

    // Delete a patient
    public boolean deletePatient(String patientID) {

        Patient patient = searchPatient(patientID);

        if (patient == null) {
            return false;
        }

        patients.remove(patient);
        return true;
    }

    // Display all patients
    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        for (Patient patient : patients) {
            patient.displayDetails();
        }
    }

    // Return number of patients
    public int getPatientCount() {
        return patients.size();
    }

    // Sort patients by surname
    public void sortBySurname() {

        patients.sort(Comparator.comparing(
                Patient::getLastName,
                String.CASE_INSENSITIVE_ORDER));
    }

    // Sort patients by Patient ID
    public void sortByPatientID() {

        patients.sort(Comparator.comparing(
                Patient::getPatientID,
                String.CASE_INSENSITIVE_ORDER));
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
}
