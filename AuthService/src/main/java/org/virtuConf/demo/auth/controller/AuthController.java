package org.virtuConf.demo.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.virtuConf.demo.auth.dto.*;
import org.virtuConf.demo.auth.service.AuthService;
import org.virtuConf.demo.auth.utils.errors.BadRequest;
import org.virtuConf.demo.auth.utils.errors.ConflictError;
import org.virtuConf.demo.auth.utils.errors.ResourceMissingError;
import org.virtuConf.demo.auth.utils.errors.UnauthenticatedError;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;
    @PostMapping("/register")
    public RegistrationResponseDTO registrationHandler(@RequestBody RegisterDTO requestData) throws ConflictError, IOException, BadRequest {
        return service.registerUser(requestData);
    }

    @PostMapping("/otp/validate")
    public SimpleMsgResponseDTO validateOTP(@RequestBody ValidateOTPDTO requestData) throws ResourceMissingError, UnauthenticatedError {
        return service.validateOTP(requestData);
    }

    @PostMapping("/otp/regenerate")
    public SimpleMsgResponseDTO regenerateOTP(@RequestBody RegenerateOTPDTO requestData) throws ResourceMissingError, IOException {
        return service.regenerateOTP(requestData);
    }
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO requestData) throws BadRequest, ResourceMissingError, UnauthenticatedError {
        return service.login(requestData);
    }

    @PatchMapping("/token/refresh")
    public RefreshTokenResponseDTO refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws BadRequest, ResourceMissingError, UnauthenticatedError {
        if(!authHeader.startsWith("Bearer ")) throw new BadRequest();
        return service.refreshToken(authHeader.substring(7));
    }

    @GetMapping("/token/userdata")
    public PersonDataDTO getUserData(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws BadRequest, UnauthenticatedError {
        if(!authHeader.startsWith("Bearer ")) throw new BadRequest();
        return service.getPersonData(authHeader.substring(7));
    }
}
