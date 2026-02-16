package Mattiazerbini.U5_W3_D1.repositories;

import Mattiazerbini.U5_W3_D1.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
