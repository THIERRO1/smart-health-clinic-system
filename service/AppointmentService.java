package rw.auca.muyoboke.smarthealthclinic.service;

import rw.auca.muyoboke.smarthealthclinic.dto.AppointmentRequest;
import rw.auca.muyoboke.smarthealthclinic.entity.Appointment;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;

import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(Patient patient, AppointmentRequest request);

    List<Appointment> getPatientAppointments(Patient patient);
}
