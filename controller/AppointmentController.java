package rw.auca.muyoboke.smarthealthclinic.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.auca.muyoboke.smarthealthclinic.dto.AppointmentRequest;
import rw.auca.muyoboke.smarthealthclinic.entity.Appointment;
import rw.auca.muyoboke.smarthealthclinic.entity.Patient;
import rw.auca.muyoboke.smarthealthclinic.entity.User;
import rw.auca.muyoboke.smarthealthclinic.repository.PatientRepository;
import rw.auca.muyoboke.smarthealthclinic.repository.UserRepository;
import rw.auca.muyoboke.smarthealthclinic.repository.DoctorRepository;
import rw.auca.muyoboke.smarthealthclinic.service.AppointmentService;

import java.util.List;

@Controller
@RequestMapping("/patient/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentController(AppointmentService appointmentService,
                                 UserRepository userRepository,
                                 PatientRepository patientRepository,
                                 DoctorRepository doctorRepository) {
        this.appointmentService = appointmentService;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/book")
    public String showBookingForm(Model model, Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        model.addAttribute("appointmentRequest", new AppointmentRequest());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patientName", user.getFullName());

        return "appointment/book";
    }

    @PostMapping("/book")
    public String bookAppointment(@Valid @ModelAttribute("appointmentRequest") AppointmentRequest appointmentRequest,
                                  BindingResult bindingResult,
                                  Model model,
                                  Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("patientName", user.getFullName());
            return "appointment/book";
        }

        try {
            appointmentService.bookAppointment(patient, appointmentRequest);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("patientName", user.getFullName());
            model.addAttribute("errorMessage", ex.getMessage());
            return "appointment/book";
        }

        return "redirect:/patient/appointments/list";
    }

    @GetMapping("/list")
    public String listAppointments(Model model, Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Appointment> appointments = appointmentService.getPatientAppointments(patient);

        model.addAttribute("appointments", appointments);
        model.addAttribute("patientName", user.getFullName());

        return "appointment/list";
    }
}
