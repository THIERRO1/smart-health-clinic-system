package rw.auca.muyoboke.smarthealthclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 60)
    private String fullName;

    @NotBlank
    @Size(min = 3, max = 40)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    private String confirmPassword;

    // Getters and setters...
}
