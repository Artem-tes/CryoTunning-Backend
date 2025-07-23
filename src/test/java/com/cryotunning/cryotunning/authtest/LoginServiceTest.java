package com.cryotunning.cryotunning.authtest;

import com.cryotunning.cryotunning.customexception.userexception.IncorrectPasswordException;
import com.cryotunning.cryotunning.entities.dbentities.LoginUser;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.entities.responsesto.CompleteAuthDTO;
import com.cryotunning.cryotunning.repository.userpackage.CountUserRepository;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.servicesclass.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    UserRepository mockUserRepository;
    @Mock
    PasswordEncoder mockPasswordEncoder;
    @Mock
    CountUserRepository countUserRepository;

    @InjectMocks
    LoginService loginService;


    @Test
    public void positiveExecuteTest(){
        AuthDTO mockAuthDTO = new AuthDTO();
        User mockUser = new User();
        mockUser.setPassword("the_secret");
        mockAuthDTO.setUsername("username");
        mockAuthDTO.setPassword("the_secret");
        Mockito.when(mockUserRepository.getByUsernameOptional(mockAuthDTO.getUsername()))
                .thenReturn(Optional.of(mockUser));
        Mockito.when(mockPasswordEncoder.matches(mockAuthDTO.getPassword(), mockAuthDTO.getPassword()))
                .thenReturn(true);
        Mockito.when(countUserRepository.getCountLoginUser()).thenReturn(0);
        Mockito.when(countUserRepository.save(Mockito.any(LoginUser.class)))
                .thenReturn(new LoginUser());

        ResponseEntity<CompleteAuthDTO> response = loginService.execute(mockAuthDTO,new User());
        Assertions.assertEquals(HttpStatusCode.valueOf(200),response.getStatusCode());
    }

    @Test
    public void negativeUsernameNotFoundExceptionThrows(){
        AuthDTO mockAuthDTO = new AuthDTO();
        mockAuthDTO.setUsername("not_founded_username");
        Assertions.assertThrows(UsernameNotFoundException.class,()->loginService.execute(mockAuthDTO,new User()));

    }

    @Test
    public void negativeIncorrectPasswordExceptionThrows(){
        AuthDTO mockAuthDTO = new AuthDTO();
        mockAuthDTO.setUsername("ultimate");
        mockAuthDTO.setPassword("incorrectPassword");
        Mockito.when(mockUserRepository.getByUsernameOptional(mockAuthDTO.getUsername()))
                .thenReturn(Optional.of(new User()));
        Assertions.assertThrows(IncorrectPasswordException.class,()->loginService.execute(mockAuthDTO,new User()));
    }



}
