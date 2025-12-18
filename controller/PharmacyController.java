package rw.auca.muyoboke.smarthealthclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rw.auca.muyoboke.smarthealthclinic.entity.Prescription;
import rw.auca.muyoboke.smarthealthclinic.entity.PrescriptionStatus;
import rw.auca.muyoboke.smarthealthclinic.repository.PrescriptionRepository;

@Controller
public class PharmacyController {

    private final PrescriptionRepository prescriptionRepository;

    public PharmacyController(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    // ✅ View all prescriptions
    @GetMapping("/pharmacy/view")
    public String viewPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionRepository.findAll());
        return "pharmacy/view";
    }

    // ✅ Mark as READY
    @GetMapping("/pharmacy/ready/{id}")
    public String markReady(@PathVariable Long id) {
        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        p.setStatus(PrescriptionStatus.READY);
        prescriptionRepository.save(p);
        return "redirect:/pharmacy/view";
    }

    // ✅ Mark as COLLECTED
    @GetMapping("/pharmacy/collected/{id}")
    public String markCollected(@PathVariable Long id) {
        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        p.setStatus(PrescriptionStatus.COLLECTED);
        prescriptionRepository.save(p);
        return "redirect:/pharmacy/view";
    }
}
