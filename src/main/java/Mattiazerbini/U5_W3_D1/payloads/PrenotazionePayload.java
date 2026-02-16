package Mattiazerbini.U5_W3_D1.payloads;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class PrenotazionePayload {
    @NotBlank(message = "La data è obbligatorio")
    private LocalDate dataRichiesta;
    private String preferenze;
    private UUID idDipendente;
    private UUID idViaggio;

    public PrenotazionePayload(LocalDate dataRichiesta, String preferenze, UUID idDipendente, UUID idViaggio) {
        this.dataRichiesta = dataRichiesta;
        this.preferenze = preferenze;
        this.idDipendente = idDipendente;
        this.idViaggio = idViaggio;
    }

}
