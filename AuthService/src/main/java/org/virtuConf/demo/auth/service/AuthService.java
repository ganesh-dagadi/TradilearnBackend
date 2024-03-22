package org.virtuConf.demo.auth.service;
import org.virtuConf.demo.auth.dto.*;
import org.virtuConf.demo.auth.utils.errors.BadRequest;
import org.virtuConf.demo.auth.utils.errors.ConflictError;
import org.virtuConf.demo.auth.utils.errors.ResourceMissingError;
import org.virtuConf.demo.auth.utils.errors.UnauthenticatedError;

import java.io.IOException;

public interface AuthService {
    public RegistrationResponseDTO registerUser(RegisterDTO requestData) throws ConflictError, IOException, BadRequest;
    public SimpleMsgResponseDTO validateOTP(ValidateOTPDTO requestData) throws ResourceMissingError, UnauthenticatedError;
    public SimpleMsgResponseDTO regenerateOTP(RegenerateOTPDTO requestData) throws ResourceMissingError, IOException;
    public LoginResponseDTO login(LoginDTO requestData) throws BadRequest, ResourceMissingError, UnauthenticatedError;
    RefreshTokenResponseDTO refreshToken(String token) throws ResourceMissingError, BadRequest, UnauthenticatedError;
    PersonDataDTO getPersonData(String token) throws BadRequest, UnauthenticatedError;
}
