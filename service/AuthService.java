package rw.auca.muyoboke.smarthealthclinic.service;

import rw.auca.muyoboke.smarthealthclinic.dto.RegisterRequest;

public interface AuthService {
    void registerPatient(RegisterRequest request);
}
