package com.meysamzamani.ProductAPI.application;

import com.meysamzamani.ProductAPI.presentation.dto.AuthResponseDTO;
import com.meysamzamani.ProductAPI.presentation.dto.SignInRequestDTO;
import com.meysamzamani.ProductAPI.presentation.dto.SignUpRequestDTO;

public interface AuthenticationService {
    AuthResponseDTO signup(SignUpRequestDTO request);

    AuthResponseDTO signin(SignInRequestDTO request);
}
