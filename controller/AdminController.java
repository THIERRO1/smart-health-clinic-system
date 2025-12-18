package rw.auca.muyoboke.smarthealthclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rw.auca.muyoboke.smarthealthclinic.repository.*;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminController(UserRepository userRepository,
                           DoctorRepository doctorRepository,
                           PatientRepository patientRepository,
                           PrescriptionRepository prescriptionRepository,
                           AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // ✅ Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    // ✅ View all users
    @GetMapping("/admin/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    // ✅ View all doctors
    @GetMapping("/admin/doctors")
    public String viewDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "admin/doctors";
    }

    // ✅ View all patients
    @GetMapping("/admin/patients")
    public String viewPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "admin/patients";
    }

    // ✅ View all prescriptions
    @GetMapping("/admin/prescriptions")
    public String viewPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionRepository.findAll());
        return "admin/prescriptions";
    }

    // ✅ View all appointments
    @GetMapping("/admin/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "admin/appointments";
    }
}
