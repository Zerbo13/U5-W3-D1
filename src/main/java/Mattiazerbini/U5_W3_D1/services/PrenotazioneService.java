package Mattiazerbini.U5_W3_D1.services;

import Mattiazerbini.U5_W3_D1.entities.Dipendente;
import Mattiazerbini.U5_W3_D1.entities.Prenotazione;
import Mattiazerbini.U5_W3_D1.entities.Viaggio;
import Mattiazerbini.U5_W3_D1.exceptions.ExceptionPrenotation;
import Mattiazerbini.U5_W3_D1.exceptions.NotFoundException;
import Mattiazerbini.U5_W3_D1.exceptions.ValidationException;
import Mattiazerbini.U5_W3_D1.payloads.PrenotazionePayload;
import Mattiazerbini.U5_W3_D1.repositories.DipendenteRepository;
import Mattiazerbini.U5_W3_D1.repositories.PrenotazioneRepository;
import Mattiazerbini.U5_W3_D1.repositories.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final DipendenteRepository dipendenteRepository;
    private final ViaggioRepository viaggioRepository;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, DipendenteRepository dipendenteRepository, ViaggioRepository viaggioRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.dipendenteRepository = dipendenteRepository;
        this.viaggioRepository = viaggioRepository;
    }

    public Prenotazione salvaPrenotazione(PrenotazionePayload payload){
        Dipendente newDipendente = dipendenteRepository.findById(payload.getIdDipendente())
        .orElseThrow(()->new ValidationException("Dipendente con id "+payload.getIdDipendente()+ " non trovato"));

        Viaggio newViaggio = viaggioRepository.findById(payload.getIdViaggio())
                .orElseThrow(() -> new ValidationException("Viaggio con id "+payload.getIdViaggio()+ " non trovato"));

        boolean prenotazioneEsistente = prenotazioneRepository.existsByDipendenteIdAndDataRichiesta
                (payload.getIdDipendente(), payload.getDataRichiesta());
        if(prenotazioneEsistente){
            throw new ExceptionPrenotation("Il dipendente ha gia effettuato una prenotazione per il" +payload.getDataRichiesta());
        }
        Prenotazione prenotazione = new Prenotazione(payload.getDataRichiesta(), payload.getPreferenze(),
                newDipendente, newViaggio);
        Prenotazione prenotazioneSalvata = this.prenotazioneRepository.save(prenotazione);
        log.info("Prenotazione con id " +prenotazioneSalvata.getId()+ " salvata con successo!" );
        return prenotazioneSalvata;
    }

}
