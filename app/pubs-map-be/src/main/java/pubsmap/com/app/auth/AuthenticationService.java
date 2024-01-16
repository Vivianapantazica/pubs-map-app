package pubsmap.com.app.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pubsmap.com.app.clients.Client;
import pubsmap.com.app.clients.ClientRepository;
import pubsmap.com.app.clients.Role;
import pubsmap.com.app.config.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Client client = Client.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAdmin(false)
                .role(Role.USER)
                .deleted(false)
                .build();
        clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Client client = clientRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
