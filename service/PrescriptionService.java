package rw.auca.muyoboke.smarthealthclinic.service;

public interface PrescriptionService {
    void createPrescription(String doctorUsername,
                            String patientUsername,
                            String diagnosis,
                            String medicine);
}
