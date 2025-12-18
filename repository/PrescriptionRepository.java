package rw.auca.muyoboke.smarthealthclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.muyoboke.smarthealthclinic.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
