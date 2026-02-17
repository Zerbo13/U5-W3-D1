package Mattiazerbini.U5_W3_D1.exceptions;

import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID message) {
        super("La risorsa con id "+id+ " non è stata trovata");
    }
}
