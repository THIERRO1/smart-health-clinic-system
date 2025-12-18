package rw.auca.muyoboke.smarthealthclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.muyoboke.smarthealthclinic.entity.Appointment;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;
import rw.auca.muyoboke.smarthealthclinic.entity.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientOrderByAppointmentDateTimeDesc(Patient patient);

    List<Appointment> findByDoctorOrderByAppointmentDateTimeAsc(Doctor doctor);

    boolean existsByDoctorAndAppointmentDateTime(Doctor doctor, LocalDateTime dateTime);
}
