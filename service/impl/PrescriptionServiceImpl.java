package rw.auca.muyoboke.smarthealthclinic.service.impl;

import org.springframework.stereotype.Service;
import rw.auca.muyoboke.smarthealthclinic.entity.*;
import rw.auca.muyoboke.smarthealthclinic.repository.*;
import rw.auca.muyoboke.smarthealthclinic.service.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(UserRepository userRepository,
                                   DoctorRepository doctorRepository,
                                   PatientRepository patientRepository,
                                   PrescriptionRepository prescriptionRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void createPrescription(String doctorUsername,
                                   String patientUsername,
                                   String diagnosis,
                                   String medicine) {

        // ✅ Find doctor user
        User doctorUser = userRepository.findByUsername(doctorUsername)
                .orElseThrow(() -> new RuntimeException("Doctor user not found"));

        Doctor doctor = doctorRepository.findByUser(doctorUser)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // ✅ Find patient user
        User patientUser = userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> new RuntimeException("Patient user not found"));

        Patient patient = patientRepository.findByUser(patientUser)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // ✅ Create prescription
        Prescription p = new Prescription();
        p.setDoctor(doctor);
        p.setPatient(patient);
        p.setDiagnosis(diagnosis);
        p.setMedicine(medicine);
        p.setStatus(PrescriptionStatus.PENDING);

        prescriptionRepository.save(p);
    }
}
