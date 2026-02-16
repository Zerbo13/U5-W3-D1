package Mattiazerbini.U5_W3_D1.repositories;

import Mattiazerbini.U5_W3_D1.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    boolean existsByDipendenteIdAndDataRichiesta(UUID dipendenteId, LocalDate dataRichiesta);
}
