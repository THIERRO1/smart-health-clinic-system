package rw.auca.muyoboke.smarthealthclinic.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.auca.muyoboke.smarthealthclinic.dto.RegisterRequest;
import rw.auca.muyoboke.smarthealthclinic.service.AuthService;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {

            // ✅ Redirect DOCTOR to doctor dashboard
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"))) {
                return "redirect:/doctor/dashboard";
            }

            // ✅ Redirect all other users to normal dashboard
            return "redirect:/dashboard";
        }

        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPatient(
            @Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            authService.registerPatient(registerRequest);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "auth/register";
        }

        model.addAttribute("successMessage", "Account created successfully. Please login.");
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "dashboard";
    }
}
