package rw.auca.muyoboke.smarthealthclinic.service;

import rw.auca.muyoboke.smarthealthclinic.entity.QueueEntry;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;
import rw.auca.muyoboke.smarthealthclinic.entity.Doctor;

import java.util.List;

public interface QueueService {
    QueueEntry addToQueue(Patient patient, Doctor doctor, boolean emergency);
    List<QueueEntry> getLiveQueue();
    QueueEntry callNextPatient(Doctor doctor);
}
