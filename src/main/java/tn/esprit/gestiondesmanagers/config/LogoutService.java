package tn.esprit.gestiondesmanagers.config;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.esprit.gestiondesmanagers.Token.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if(authHeader == null||!authHeader.startsWith("Bearer ") ){
            return;
        }
        jwt=authHeader.substring(7);
        var storedToken=tokenRepository.findByToken(jwt)
                .orElse(null);
        if(storedToken!=null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }

    }
}
