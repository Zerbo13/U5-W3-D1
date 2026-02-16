package Mattiazerbini.U5_W3_D1.controllers;

import Mattiazerbini.U5_W3_D1.entities.Prenotazione;
import Mattiazerbini.U5_W3_D1.entities.Viaggio;
import Mattiazerbini.U5_W3_D1.payloads.PrenotazionePayload;
import Mattiazerbini.U5_W3_D1.payloads.ViaggioPayload;
import Mattiazerbini.U5_W3_D1.services.PrenotazioneService;
import Mattiazerbini.U5_W3_D1.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggio")
public class ViaggioController {


    private final ViaggioService viaggioService;

    @Autowired
    public ViaggioController(ViaggioService viaggioService){
        this.viaggioService = viaggioService;
    }

    //CREAZIONE DI UN NUOVO VIAGGIO (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Viaggio createViaggio(@RequestBody ViaggioPayload payload) {
        return this.viaggioService.salvaViaggio(payload);
    }

}
