package com.cryotunning.cryotunning.authtest;

import com.cryotunning.cryotunning.customexception.userexception.UsernameIsOwningException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.servicesclass.RegistrationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    RegistrationService registrationService;

    @Test
    public void positiveExecuteTest(){
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("username");
        authDTO.setPassword("password");
        Mockito.when(userRepository.getByUsernameOptional(authDTO.getUsername()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(new User());

        ResponseEntity<?> response = registrationService.execute(authDTO,new User());
        Assertions.assertEquals(HttpStatusCode.valueOf(204),response.getStatusCode());
    }

    @Test
    public void negativeThrowUsernameIsOwningExceptionTest(){
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("username");
        Mockito.when(userRepository.getByUsernameOptional(authDTO.getUsername()))
                .thenReturn(Optional.of(new User()));
        Assertions.assertThrows(UsernameIsOwningException.class,()-> registrationService.execute(authDTO,new User()));
    }
}
