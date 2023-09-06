package com.meysamzamani.ProductAPI.presentation.controller;

import com.meysamzamani.ProductAPI.application.AuthenticationService;
import com.meysamzamani.ProductAPI.presentation.dto.AuthResponseDTO;
import com.meysamzamani.ProductAPI.presentation.dto.SignInRequestDTO;
import com.meysamzamani.ProductAPI.presentation.dto.SignUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AuthResponseDTO signUp(@RequestBody SignUpRequestDTO request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AuthResponseDTO signIn(@RequestBody SignInRequestDTO request) {
        return authenticationService.signin(request);
    }
}
