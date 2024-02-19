package tn.esprit.gestiondesmanagers.auth;

import com.sun.xml.internal.ws.handler.HandlerException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.Token.Token;
import tn.esprit.gestiondesmanagers.Token.TokenRepository;
import tn.esprit.gestiondesmanagers.Token.Tokentype;
import tn.esprit.gestiondesmanagers.config.JwtService;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.repositories.DepartmentRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

import javax.management.relation.Role;
import javax.management.relation.RoleInfo;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

   private final UserRepository repository;

   private final TokenRepository tokenRepository;

   private final PasswordEncoder passwordEncoder;

   private final JwtService jwtService;

   private final DepartmentRepository departmentRepository;

   private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request,Integer dept_id) {
        Department department=departmentRepository.findById(dept_id).orElse(null);
        var user=User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .birth_date(request.getBirth_date())
                .cin(request.getCin())
                .confirmpassword(passwordEncoder.encode(request.getConfirmpassword()))
                .nationality(request.getNationality())
                .gender(request.getGender())
                .phone_number(request.getPhone_number())
                .login(request.getLogin())
                .department(department)
                .image(request.getImage())
                .work_post(request.getWork_post())
                .build();
        var savedUser=repository.save(user);
        var jwtToken= jwtService.generatetoken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    private void revokeAllUserTokens(User user){
        var validusertokens=tokenRepository.findAllValidTokensByUser(user.getId());
        if(validusertokens.isEmpty())
            return;
            validusertokens.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validusertokens);
        }


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws HandlerException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var  user=repository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé"));
        var jwtToken= jwtService.generatetoken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }
    private void saveUserToken(User user, String jwtToken) {
        var token= Token.builder()
                .user(user)
                .token(jwtToken)
                .tokentype(Tokentype.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public Boolean validEmail(String email){
        if(repository.validEmail(email)!=null){
            return  false;
        }
        return true;
    }
}
