package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.UsernameIsOwningException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.enums.ROLES;
import com.cryotunning.cryotunning.repository.UserRepository;
import com.cryotunning.cryotunning.service.servicebase.BaseControllerServiceWithoutResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService implements BaseControllerServiceWithoutResponseBody<AuthDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> execute(AuthDTO authDTO, User user) {
        validate(authDTO,user);
        operate(authDTO,user);
        return ResponseEntity.status(204).build();
    }

    @Override
    public void validate(AuthDTO authDTO, User user) {
        validateUsernameOwning(authDTO);
    }

    private void validateUsernameOwning(AuthDTO authDTO){
        String username = authDTO.getUsername();
        Optional<User> userOptional = userRepository.findByUsernameOptional(username);
        if(userOptional.isPresent()){
            throw new UsernameIsOwningException("username = "+username+" is owning");
        }
    }

    @Override
    public void operate(AuthDTO authDTO, User user) {
        String encodePassword = encodePasswordByAuthDTO(authDTO);
        saveUserToDB(authDTO,encodePassword);
    }

    private void saveUserToDB(AuthDTO authDTO,String encodePassword){
        userRepository.save(new User(authDTO.getUsername(),encodePassword, "USER"));
    }

    private String encodePasswordByAuthDTO(AuthDTO authDTO){
        return passwordEncoder.encode(authDTO.getPassword());
    }
}
