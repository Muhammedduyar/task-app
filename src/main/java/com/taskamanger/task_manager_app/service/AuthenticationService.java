package com.taskamanger.task_manager_app.service;
import com.taskamanger.task_manager_app.dto.AuthenticationRequest;
import com.taskamanger.task_manager_app.dto.AuthenticationResponse;
import com.taskamanger.task_manager_app.dto.RegisterRequest;
import com.taskamanger.task_manager_app.model.Role;
import com.taskamanger.task_manager_app.model.User;
import com.taskamanger.task_manager_app.repository.UserRepository;
import com.taskamanger.task_manager_app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        // Rolü belirle, varsayılan olarak USER olabilir veya gelen isteğe göre ayarlanabilir
        Role userRole = request.getRole() != null ? Role.valueOf(request.getRole().toUpperCase()) : Role.USER;

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // Kimlik doğrulama başarılı ise kullanıcıyı bul ve token oluştur
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(); // Kullanıcı bulunamazsa hata fırlat
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}