package rw.auca.muyoboke.smarthealthclinic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rw.auca.muyoboke.smarthealthclinic.entity.Doctor;
import rw.auca.muyoboke.smarthealthclinic.entity.User;
import rw.auca.muyoboke.smarthealthclinic.repository.DoctorRepository;
import rw.auca.muyoboke.smarthealthclinic.repository.UserRepository;
import rw.auca.muyoboke.smarthealthclinic.service.PrescriptionService;

@Controller
public class DoctorController {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PrescriptionService prescriptionService;

    public DoctorController(UserRepository userRepository,
                            DoctorRepository doctorRepository,
                            PrescriptionService prescriptionService) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.prescriptionService = prescriptionService;
    }

    // ✅ Doctor Dashboard
    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Authentication authentication, Model model) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        model.addAttribute("doctorName", user.getFullName());

        return "doctor/dashboard";
    }

    // ✅ Doctor Queue Page
    @GetMapping("/doctor/queue")
    public String doctorQueue(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        model.addAttribute("queue", doctor.getQueueEntries());

        return "doctor/queue";
    }

    // ✅ Step 6 — Call Next Patient
    @GetMapping("/doctor/call-next")
    public String callNext(Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // TODO: integrate with QueueService
        // queueService.callNextPatient(doctor);

        return "redirect:/doctor/queue";
    }

    // ✅ Show prescription form
    @GetMapping("/doctor/prescribe")
    public String showPrescriptionForm() {
        return "doctor/prescribe";
    }

    // ✅ Handle prescription submission
    @PostMapping("/doctor/prescribe")
    public String submitPrescription(
            @RequestParam String patientUsername,
            @RequestParam String diagnosis,
            @RequestParam String medicine,
            Authentication authentication) {

        String doctorUsername = authentication.getName();

        prescriptionService.createPrescription(
                doctorUsername,
                patientUsername,
                diagnosis,
                medicine
        );

        return "redirect:/doctor/dashboard";
    }
}
