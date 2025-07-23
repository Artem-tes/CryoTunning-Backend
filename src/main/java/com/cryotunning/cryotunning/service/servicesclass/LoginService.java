package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.userexception.IncorrectPasswordException;
import com.cryotunning.cryotunning.entities.dbentities.LoginUser;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.entities.responsesto.CompleteAuthDTO;
import com.cryotunning.cryotunning.repository.userpackage.CountUserRepository;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечает за авторизацию пользователя
 * @author ArtemPotapov
 */
@Service
@RequiredArgsConstructor
public class LoginService implements BaseControllerService<AuthDTO, CompleteAuthDTO,String> {


    private final UserRepository userRepository;
    private HashMap<String,Object> cache = new HashMap<>();
    private final PasswordEncoder passwordEncoder;
    private final CountUserRepository countUserRepository;

    private static String secret = "bQvYcVQ6GrMGsvMeLZ1Fas5HO+9Vq8coJx+0P4AiyjY=";


    /**
     * Главный execute метод, который вызывается в контроллере
     * @param authDTO {"username":"@username","password":"secret"}
     * @param user заглушка, не используется
     * @return Статус - 200, completeAuthDTO {"jwtToken":"token","username":"@username"}
     */
    @Override
    public ResponseEntity<CompleteAuthDTO> execute(AuthDTO authDTO, User user) {
        validate(authDTO,user);
        String jwtToken = operate(authDTO,user);
        return ResponseEntity.status(200).body(buildResponse(jwtToken,user,authDTO));
    }

    /**
     * Валидация -- Главный метод валидации
     * @param authDTO
     * @param user
     */
    @Override
    public void validate(AuthDTO authDTO, User user) {
        validateCorrectUsername(authDTO);
        validateCorrectPassword(authDTO);
    }

    /**
     * Валидация -- Проверка того что username находится в базе данных и то что он корректный
     * @param authDTO
     * @throws UsernameNotFoundException 400
     */
    private void validateCorrectUsername(AuthDTO authDTO){
        Optional<User> optionalUser = userRepository.getByUsernameOptional(authDTO.getUsername());
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Username "+authDTO.getUsername()+" not found");
        }else {
            cache.put("user",optionalUser.get());
        }
    }

    /**
     * Валидация -- Проверка того что пароль принадлежащий пользователю с указаным username кореектный и правильный
     * @param authDTO
     * @throws IncorrectPasswordException 400
     */
    private void validateCorrectPassword(AuthDTO authDTO){
        User user = (User) cache.get("user");
        if(!checkCorrectPasswords(user.getPassword(),authDTO.getPassword())){
            throw new IncorrectPasswordException("Password "+authDTO.getPassword()+" is incorrect");
        }
    }

    /**
     * Валидация -- Утилитный метод для проверки совпадают пароли или нет
     * @param encodePassword
     * @param dtoPassword
     * @return true - пароли совпадают, false - пароли не совпадают
     */
    private boolean checkCorrectPasswords(String encodePassword,String dtoPassword){
        return passwordEncoder.matches(dtoPassword,encodePassword);
    }


    /**
     * Главный метод операции
     * @param authDTO
     * @param user
     * @return JWT токен для ответа на клиент
     */
    @Override
    public String operate(AuthDTO authDTO, User user) {
        writeCountLoginUser();
        String jwt = generateJWTToken(authDTO);
        return jwt;
    }

    /**
     * Записывает еще одного пользователя в количество авторизованых
     */
    private void writeCountLoginUser(){
        Integer countLoginUsers = countUserRepository.getCountLoginUser();
        countUserRepository.save(
                new LoginUser((long)1,countLoginUsers+1));
    }

    /**
     * Генерирует JWT токен на основе username и времени
     * @param authDTO
     * @return JWT токен на 30 дней
     */
    private String generateJWTToken(AuthDTO authDTO){
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000); // +30 дней

        return Jwts.builder()
                .setSubject(authDTO.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Формирует ответ на запрос, запаковывает в CompleteAuthDTO
     * @param jwtToken
     * @param user
     * @param authDTO
     * @return CompleteAuthDTO
     */
    @Override
    public CompleteAuthDTO buildResponse(String jwtToken, User user, AuthDTO authDTO) {
        return new CompleteAuthDTO(
                jwtToken,authDTO.getUsername()
        );
    }
}
