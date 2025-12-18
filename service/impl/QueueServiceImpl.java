package rw.auca.muyoboke.smarthealthclinic.service.impl;

import org.springframework.stereotype.Service;
import rw.auca.muyoboke.smarthealthclinic.entity.*;
import rw.auca.muyoboke.smarthealthclinic.repository.QueueRepository;
import rw.auca.muyoboke.smarthealthclinic.service.QueueService;

import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;

    public QueueServiceImpl(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @Override
    public QueueEntry addToQueue(Patient patient, Doctor doctor, boolean emergency) {
        int nextNumber = (int) (queueRepository.count() + 1);

        QueueEntry entry = new QueueEntry();
        entry.setPatient(patient);
        entry.setDoctor(doctor);
        entry.setQueueNumber(nextNumber);
        entry.setEmergency(emergency);

        return queueRepository.save(entry);
    }

    @Override
    public List<QueueEntry> getLiveQueue() {
        return queueRepository.findByStatusOrderByQueueNumberAsc("WAITING");
    }

    @Override
    public QueueEntry callNextPatient(Doctor doctor) {
        List<QueueEntry> waiting = queueRepository.findByDoctorAndStatusOrderByQueueNumberAsc(doctor, "WAITING");

        if (waiting.isEmpty()) return null;

        QueueEntry next = waiting.get(0);
        next.setStatus("CALLED");
        return queueRepository.save(next);
    }
}
