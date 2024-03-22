package org.virtuConf.demo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.virtuConf.demo.auth.dto.*;
import org.virtuConf.demo.auth.model.Person;
import org.virtuConf.demo.auth.model.RefreshToken;
import org.virtuConf.demo.auth.repository.PersonRepo;
import org.virtuConf.demo.auth.repository.RefreshTokenRepo;
import org.virtuConf.demo.auth.utils.cache.OTPCache;
import org.virtuConf.demo.auth.utils.communication.MailCommunication;
import org.virtuConf.demo.auth.utils.errors.BadRequest;
import org.virtuConf.demo.auth.utils.errors.ConflictError;
import org.virtuConf.demo.auth.utils.errors.ResourceMissingError;
import org.virtuConf.demo.auth.utils.errors.UnauthenticatedError;
import org.virtuConf.demo.auth.utils.jwt.JWTInterface;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    PersonRepo personRepo;
    @Autowired
    RefreshTokenRepo refreshTokenRepo;
    @Autowired
    MailCommunication mailCommunication;
    @Autowired
    OTPCache otpCache;
    @Autowired
    JWTInterface jwtHelper;
    @Value("${otp.expiry}")
    private Integer otpExpiry;
    @Override
    public RegistrationResponseDTO registerUser(RegisterDTO requestData) throws ConflictError, IOException, BadRequest {
        validateRegister(requestData);
        requestData.setPassword(this.hashPassword(requestData.getPassword() , 10));
        Person newPerson = Person.buildFromRequest(requestData);
        newPerson = personRepo.save(newPerson);
        String otp = this.generateOTPForEmailVerification(String.valueOf(newPerson.getId()));
        mailCommunication.sendMailForEmailVerification(newPerson.getEmail() , otp);
        RegistrationResponseDTO response = new RegistrationResponseDTO();
        response.setMsg("Verification otp sent to your mail. Please verify");
        response.setUserId(newPerson.getId());
        return response;
    }
    @Override
    public SimpleMsgResponseDTO validateOTP(ValidateOTPDTO requestData) throws ResourceMissingError, UnauthenticatedError {
        Optional<String> retrievedOTPOp = otpCache.getOTP(String.valueOf(requestData.getUserID()));
        if(retrievedOTPOp.isEmpty()) throw new ResourceMissingError("OTP expired or not found");
        String retrievedOTP = retrievedOTPOp.get();
        if(!retrievedOTP.equals(requestData.getOtp())) throw new UnauthenticatedError("OTP is wrong");
        Optional<Person> personOp = personRepo.findById(requestData.getUserID());
        if(personOp.isEmpty()) throw new ResourceMissingError("Person with provided id not found");
        Person person = personOp.get();
        person.setVerified(true);
        person.setActive(true);
        personRepo.save(person);
        SimpleMsgResponseDTO res = new SimpleMsgResponseDTO();
        res.setMsg("User has been verified");
        return res;
    }

    @Override
    public SimpleMsgResponseDTO regenerateOTP(RegenerateOTPDTO requestData) throws ResourceMissingError, IOException {
        String otp = this.generateOTPForEmailVerification(String.valueOf(requestData.getUserId()));
        Optional<Person> personOp = personRepo.findById(requestData.getUserId());
        if(personOp.isEmpty()) throw new ResourceMissingError("Person not found");
        Person person = personOp.get();
        mailCommunication.sendMailForEmailVerification(person.getEmail() , otp);
        SimpleMsgResponseDTO response = new SimpleMsgResponseDTO();
        response.setMsg("Verification otp sent to your mail. Please verify");
        return response;
    }

    @Override
    public LoginResponseDTO login(LoginDTO requestData) throws BadRequest, ResourceMissingError, UnauthenticatedError {
        String email = requestData.getEmail();
        String password = requestData.getPassword();
        if(email.isEmpty()) throw new BadRequest();
        if(password.isEmpty()) throw new BadRequest();
        Optional<Person> personOp = personRepo.findByEmail(email);
        if(personOp.isEmpty()) throw new ResourceMissingError("Email not found");
        Person person = personOp.get();
        if(!BCrypt.checkpw(password , person.getPassword())) throw new UnauthenticatedError("Email or password wrong");
        String accessToken = jwtHelper.generateSinglePayloadJWT("userId" , person.getId().toString() , true , true , 1200);
        String refreshToken = jwtHelper.generateSinglePayloadJWT("userId" , person.getId().toString() , false , false , 0);
        RefreshToken token = new RefreshToken();
        token.setTokenValue(refreshToken);
        refreshTokenRepo.save(token);
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMsg("Logged in");
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(String token) throws ResourceMissingError, BadRequest, UnauthenticatedError {
        Optional<RefreshToken> dbTokenOp = refreshTokenRepo.findByTokenValue(token);
        if(dbTokenOp.isEmpty()) throw new ResourceMissingError("Logged out");
        RefreshToken dbToken = dbTokenOp.get();
        String dbTokenStr = dbToken.getTokenValue();
        String userId = jwtHelper.decodeRefreshJWT(dbTokenStr , "userId");
        String newAccessToken = jwtHelper.generateSinglePayloadJWT("userId" , userId , true , true , 1200);
        RefreshTokenResponseDTO refreshTokenResponseDTO = new RefreshTokenResponseDTO();
        refreshTokenResponseDTO.setAccessToken(newAccessToken);
        return refreshTokenResponseDTO;
    }

    @Override
    public PersonDataDTO getPersonData(String token) throws BadRequest, UnauthenticatedError {
        String userId = jwtHelper.decodeAccessJWT(token , "userId");
        Optional<Person> personOp = personRepo.findById(Integer.parseInt(userId));
        if(personOp.isEmpty()) throw new RuntimeException();
        Person person = personOp.get();
        return PersonDataDTO.fromPersonEntity(person);
    }

    private void validateRegister(RegisterDTO requestData) throws ConflictError, BadRequest {
        String email = requestData.getEmail();
        String password = requestData.getPassword();
        if(email.isEmpty()) throw new BadRequest();
        if(password.isEmpty()) throw new BadRequest();
        Optional<Person> userOp = personRepo.findByEmail(requestData.getEmail());
        if(userOp.isPresent()) throw new ConflictError("Username is already taken");
    }

    private String hashPassword(String password , Integer salt){
        return BCrypt.hashpw(password , BCrypt.gensalt(salt));
    }
    private String generateOTPForEmailVerification(String id){
        String random = String.valueOf((int)(Math.random() * (967589 - 103425) + 103425));
        otpCache.setOTP(random , id ,otpExpiry);
        return random;
    }

}
