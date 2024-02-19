package tn.esprit.gestiondesmanagers.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @PostMapping("/register/{dept_id}")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request,@PathVariable Integer dept_id){
        return ResponseEntity.ok(authenticationService.register(request,dept_id));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("userconnected/{email}")
    public Optional<User> getuserbyemail(@PathVariable String email){
        return userRepository.findByEmail(email);
    }

    @GetMapping("/emailvalid/{email}")
    public Boolean validEmail(@PathVariable String email){
        return authenticationService.validEmail(email);
    }


}
