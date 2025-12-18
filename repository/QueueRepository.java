package rw.auca.muyoboke.smarthealthclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.muyoboke.smarthealthclinic.entity.QueueEntry;
import rw.auca.muyoboke.smarthealthclinic.entity.Doctor;

import java.util.List;

public interface QueueRepository extends JpaRepository<QueueEntry, Long> {
    List<QueueEntry> findByDoctorAndStatusOrderByQueueNumberAsc(Doctor doctor, String status);
    List<QueueEntry> findByStatusOrderByQueueNumberAsc(String status);
}
