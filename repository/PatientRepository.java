package rw.auca.muyoboke.smarthealthclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;
import rw.auca.muyoboke.smarthealthclinic.entity.User;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
}
