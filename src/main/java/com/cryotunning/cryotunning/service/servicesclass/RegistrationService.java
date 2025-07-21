package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.userexception.UsernameIsOwningException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.enums.ROLES;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerServiceWithoutResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Сервис занимается валидацией и регистрацией нового пользователя
 * @author ArtemPotapov
 */
@Service
@RequiredArgsConstructor
public class RegistrationService implements BaseControllerServiceWithoutResponseBody<AuthDTO> {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Главный метод в сервисе, вызывается в контроллере
     * @param authDTO  (username,password)
     * @param user  здесь используется как заглушка, чтобы не делать новую реализацию
     * @return 204
     * @exception UsernameIsOwningException если имя пользователя уже занято 400 badRequest
     */
    @Override
    public ResponseEntity<?> execute(AuthDTO authDTO, User user) {
        validate(authDTO,user);
        operate(authDTO,user);
        return ResponseEntity.status(204).build();
    }

    /**
     * Главный метод валидации
     * @param authDTO  (username,password)
     * @param user  здесь используется как заглушка, чтобы не делать новую реализацию
     */
    @Override
    public void validate(AuthDTO authDTO, User user) {
        validateUsernameOwning(authDTO);
    }

    /**
     * Проверяет не занят ли username указаный в authDTO
     * @param authDTO  (username,password)
     * @throws UsernameIsOwningException  username указанный в authDTO уже занят
     */
    private void validateUsernameOwning(AuthDTO authDTO){
        String username = authDTO.getUsername();
        Optional<User> userOptional = userRepository.getByUsernameOptional(username);
        if(userOptional.isPresent()){
            throw new UsernameIsOwningException("username = "+username+" is owning");
        }
    }

    /**
     * Главный метод операции, кэширует пароль, и сохраняет нового пользователя
     * @param authDTO  (username,password)
     * @param user  здесь используется как заглушка, чтобы не делать новую реализацию
     */
    @Override
    public void operate(AuthDTO authDTO, User user) {
        String encodePassword = encodePasswordByAuthDTO(authDTO);
        saveUserToDB(authDTO,encodePassword);
    }

    /**
     * Сохраняет пользователя в базу данных
     * @param authDTO  (username,password)
     * @param encodePassword  здесь используется как заглушка, чтобы не делать новую реализацию
     */
    private void saveUserToDB(AuthDTO authDTO,String encodePassword){
        userRepository.save(new User(authDTO.getUsername(),encodePassword, ROLES.USER.name()));
    }

    /**
     * Шифрует пароль методом BCRYPT
     * @param authDTO  (username,password)
     * @return  зашифрованный пароль методом BCRYPT
     */
    private String encodePasswordByAuthDTO(AuthDTO authDTO){
        return passwordEncoder.encode(authDTO.getPassword());
    }
}
