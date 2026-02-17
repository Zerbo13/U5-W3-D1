package Mattiazerbini.U5_W3_D1.services;

import Mattiazerbini.U5_W3_D1.entities.Dipendente;
import Mattiazerbini.U5_W3_D1.entities.Prenotazione;
import Mattiazerbini.U5_W3_D1.entities.Viaggio;
import Mattiazerbini.U5_W3_D1.exceptions.NotFoundException;
import Mattiazerbini.U5_W3_D1.payloads.DipendentePayload;
import Mattiazerbini.U5_W3_D1.repositories.DipendenteRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;
    private final PasswordEncoder bcrypt;


    @Autowired
    public DipendenteService(DipendenteRepository dipendenteRepository, PasswordEncoder bcrypt) {
        this.dipendenteRepository = dipendenteRepository;
        this.bcrypt = bcrypt;
    }

    public Dipendente salvaDipendente(DipendentePayload payload){
        if(this.dipendenteRepository.existsByEmail(payload.getEmail())){
            throw new ValidationException("Questa mail" + payload.getEmail() + "è gia registrata");
        }
        Dipendente newDipendente = new Dipendente(payload.getUsername(), payload.getNome(), payload.getCognome(),
                payload.getEmail(), bcrypt.encode(payload.getPassword()));
        Dipendente dipendenteSalvato = this.dipendenteRepository.save(newDipendente);
        log.info("Il dipendente "+newDipendente.getNome()+" " +newDipendente.getCognome()+ " è stato inserito con successo!");
        return dipendenteSalvato;
    }

    public Dipendente findByIdAndUpdate(UUID userId, DipendentePayload payload) {
        // 1. Cerchiamo l'utente nel db
        Dipendente found = this.findById(userId);

        // 2. Validazione dati (esempio controllo se email è già in uso
        if (!found.getEmail().equals(payload.getEmail())) this.dipendenteRepository.findByEmail(payload.getEmail()).ifPresent(user -> {
            try {
                throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        });
        // Il controllo su email già in uso lo faccio solo se l'utente sta modificando effettivamente la sua email

        // 3. Modifico l'utente trovato
        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setEmail(payload.getEmail());
        found.setPassword(payload.getPassword());

        // 4. Salvo
        Dipendente modifiedUser = this.dipendenteRepository.save(found);

        // 5. Log
        log.info("L'utente con id " + modifiedUser.getId() + " è stato modificato correttamente");

        // 6. Ritorno l'utente modificato
        return modifiedUser;
    }

    public void findByIdAndDelete(UUID userId) {
        Dipendente found = this.findById(userId);
        this.dipendenteRepository.delete(found);
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("L'utente con email " + email + " non è stato trovato!"));
    }

    public Dipendente findById(UUID idDipendente) {
        return this.dipendenteRepository.findById(idDipendente)
                .orElseThrow(() -> new NotFoundException(idDipendente));
    }

}
