package rw.auca.muyoboke.smarthealthclinic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.muyoboke.smarthealthclinic.dto.AppointmentRequest;
import rw.auca.muyoboke.smarthealthclinic.entity.Appointment;
import rw.auca.muyoboke.smarthealthclinic.entity.Doctor;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;
import rw.auca.muyoboke.smarthealthclinic.repository.AppointmentRepository;
import rw.auca.muyoboke.smarthealthclinic.repository.DoctorRepository;
import rw.auca.muyoboke.smarthealthclinic.service.AppointmentService;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional
    public Appointment bookAppointment(Patient patient, AppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        if (appointmentRepository.existsByDoctorAndAppointmentDateTime(
                doctor, request.getAppointmentDateTime())) {
            throw new IllegalArgumentException("Doctor already has an appointment at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setReason(request.getReason());
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getPatientAppointments(Patient patient) {
        return appointmentRepository.findByPatientOrderByAppointmentDateTimeDesc(patient);
    }
}
