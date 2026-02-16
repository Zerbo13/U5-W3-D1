package Mattiazerbini.U5_W3_D1.payloads;

import Mattiazerbini.U5_W3_D1.entities.Stato;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@NoArgsConstructor
public class ViaggioPayload {
    @NotBlank(message = "La destinazione è obbligatorio")
    private String destinazione;
    @NotBlank(message = "La data è obbligatorio")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Stato stato;

    public ViaggioPayload(String destinazione, LocalDate data, Stato stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }
}
