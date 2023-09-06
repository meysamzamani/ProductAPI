package com.meysamzamani.ProductAPI.application.impl;

import com.meysamzamani.ProductAPI.application.AuthenticationService;
import com.meysamzamani.ProductAPI.application.JwtService;
import com.meysamzamani.ProductAPI.domain.Role;
import com.meysamzamani.ProductAPI.domain.User;
import com.meysamzamani.ProductAPI.infrastructure.persistence.UserRepository;
import com.meysamzamani.ProductAPI.presentation.dto.AuthResponseDTO;
import com.meysamzamani.ProductAPI.presentation.dto.SignInRequestDTO;
import com.meysamzamani.ProductAPI.presentation.dto.SignUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO signup(SignUpRequestDTO request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return new AuthResponseDTO(jwt);
    }

    @Override
    public AuthResponseDTO signin(SignInRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return new AuthResponseDTO(jwt);
    }
}
