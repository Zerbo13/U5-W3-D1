package Mattiazerbini.U5_W3_D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DipendentePayload {

    @NotBlank(message = "Username è obbligatorio")
    @Size(min = 1, max = 20, message = "L'username deve essere tra 1 e i 20 caratteri")
    private String username;
    @NotBlank(message = "Nome è obbligatorio")
    @Size(min = 1, max = 20, message = "Il nome deve essere tra 1 e i 20 caratteri")
    private String nome;
    @NotBlank(message = "Cognome è obbligatorio")
    @Size(min = 1, max = 20, message = "Il cognome deve essere tra 1 e i 20 caratteri")
    private String cognome;
    @NotBlank(message = "Email è obbligatorio")
    @Email(message = "Il formato dell'indirizzo email inserito non è corretto!")
    @Size(min = 5, message = "La email deve essere almeno di 5 caratteri")
    private String email;
    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola ecc ecc ...")
    String password;

    public DipendentePayload(String username, String nome, String cognome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }
}
