package com.suhail.notes.service;


import com.suhail.notes.requests.AuthenticationRequest;
import com.suhail.notes.responses.AuthenticationResponse;
import com.suhail.notes.requests.RegisterRequest;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}