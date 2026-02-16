package Mattiazerbini.U5_W3_D1.exceptions;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("La risorsa con id "+id+ " non è stata trovata");
    }
}
