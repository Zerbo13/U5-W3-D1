package Mattiazerbini.U5_W3_D1.services;

import Mattiazerbini.U5_W3_D1.entities.Dipendente;
import Mattiazerbini.U5_W3_D1.entities.Prenotazione;
import Mattiazerbini.U5_W3_D1.entities.Viaggio;
import Mattiazerbini.U5_W3_D1.exceptions.NotFoundException;
import Mattiazerbini.U5_W3_D1.payloads.DipendentePayload;
import Mattiazerbini.U5_W3_D1.repositories.DipendenteRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;

    @Autowired
    public DipendenteService(DipendenteRepository dipendenteRepository) {
        this.dipendenteRepository = dipendenteRepository;
    }

    public Dipendente salvaDipendente(DipendentePayload payload){
        if(this.dipendenteRepository.existsByEmail(payload.getEmail())){
            throw new ValidationException("Questa mail" + payload.getEmail() + "è gia registrata");
        }
        Dipendente newDipendente = new Dipendente(payload.getUsername(), payload.getNome(), payload.getCognome(),
                payload.getEmail(), payload.getPassword());
        Dipendente dipendenteSalvato = this.dipendenteRepository.save(newDipendente);
        log.info("Il dipendente "+newDipendente.getNome()+" " +newDipendente.getCognome()+ " è stato inserito con successo!");
        return dipendenteSalvato;
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }

}
