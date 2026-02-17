package Mattiazerbini.U5_W3_D1.controllers;

import Mattiazerbini.U5_W3_D1.entities.Dipendente;
import Mattiazerbini.U5_W3_D1.payloads.DipendentePayload;
import Mattiazerbini.U5_W3_D1.payloads.LoginDTO;
import Mattiazerbini.U5_W3_D1.payloads.LoginResponseDTO;
import Mattiazerbini.U5_W3_D1.services.AuthService;
import Mattiazerbini.U5_W3_D1.services.DipendenteService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final DipendenteService dipendenteService;

    public AuthController(AuthService authService, DipendenteService dipendenteService) {
        this.authService = authService;
        this.dipendenteService = dipendenteService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {

        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createUser(@RequestBody @Validated DipendentePayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {

            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException("Errore");
        } else {
            return this.dipendenteService.salvaDipendente(payload);
        }

    }
}
