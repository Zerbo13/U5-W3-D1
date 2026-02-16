package Mattiazerbini.U5_W3_D1.services;

import Mattiazerbini.U5_W3_D1.entities.Dipendente;
import Mattiazerbini.U5_W3_D1.entities.Viaggio;
import Mattiazerbini.U5_W3_D1.exceptions.NotFoundException;
import Mattiazerbini.U5_W3_D1.payloads.DipendentePayload;
import Mattiazerbini.U5_W3_D1.payloads.ViaggioPayload;
import Mattiazerbini.U5_W3_D1.repositories.DipendenteRepository;
import Mattiazerbini.U5_W3_D1.repositories.ViaggioRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ViaggioService {

    private final ViaggioRepository viaggioRepository;

    @Autowired
    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }

    public Viaggio salvaViaggio(ViaggioPayload payload){
        Viaggio newViaggio = new Viaggio(payload.getDestinazione(), payload.getData(), payload.getStato());
        Viaggio viaggioSalvato = this.viaggioRepository.save(newViaggio);
        log.info("Il viaggio "+payload.getDestinazione()+" " +newViaggio.getData()+ " è stato inserito con successo!");
        return viaggioSalvato;
    }

}
