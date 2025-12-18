package rw.auca.muyoboke.smarthealthclinic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;
import rw.auca.muyoboke.smarthealthclinic.entity.User;
import rw.auca.muyoboke.smarthealthclinic.repository.PatientRepository;
import rw.auca.muyoboke.smarthealthclinic.repository.UserRepository;

@Controller
public class PatientController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public PatientController(UserRepository userRepository,
                             PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/patient/profile")
    public String patientProfile(Authentication authentication, Model model) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("patient", patient);

        return "patient/profile";
    }
}
