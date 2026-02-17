package Mattiazerbini.U5_W3_D1.security;

import Mattiazerbini.U5_W3_D1.entities.Dipendente;
import Mattiazerbini.U5_W3_D1.exceptions.UnauthorizedException;
import Mattiazerbini.U5_W3_D1.services.DipendenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final DipendenteService dipendenteService;

    @Autowired
    public JWTCheckerFilter(JWTTools jwtTools, DipendenteService dipendenteService) {
        this.jwtTools = jwtTools;
        this.dipendenteService = dipendenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 1. Verifichiamo se la richiesta contiene l'header Authorization e che in caso sia nel formato "Bearer oi1j3oj21o3j213jo12j3"
        // Se l'header non c'è oppure se è malformato --> lanciamo eccezione
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer"))
            throw new UnauthorizedException("Inserire il token nell'Authorization header nel formato corretto");
        // 2. Estraiamo il token dall'header
        //  authHeader -> "Bearer oi1j3oj21o3j213jo12j3"
        String accessToken = authHeader.replace("Bearer ", "");
        // 3. Verifichiamo se il token è valido (controllare la firma e verificare data di scadenza)
        jwtTools.verifyToken(accessToken);
        // 4. Se tutto è OK --> andiamo avanti, trasmettiamo la richiesta al prossimo (può essere o un altro elemento della catena oppure il controller)
        filterChain.doFilter(request, response);

        // 1. Cerchiamo l'utente nel DB tramite id (l'id sta nel token!)
        // 1.1 Leggiamo l'id dal token
        UUID userId = jwtTools.extractIdFromToken(accessToken);
        // 1.2 Find by Id
        Dipendente authenticatedUser = this.dipendenteService.findById(userId);
        // 2. Associamo l'utente al Security Context
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 3. Se tutto è OK --> andiamo avanti, trasmettiamo la richiesta al prossimo (può essere o un altro elemento della catena oppure il controller)
        filterChain.doFilter(request, response);

        // 5. Se c'è qualche problema con il token -> eccezione
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
       }
}
