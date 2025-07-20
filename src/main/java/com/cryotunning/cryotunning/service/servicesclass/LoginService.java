package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.userexception.IncorrectPasswordException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.entities.responsesto.CompleteAuthDTO;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements BaseControllerService<AuthDTO, CompleteAuthDTO,String> {

    private final UserRepository userRepository;
    private HashMap<String,Object> cache = new HashMap<>();
    private final PasswordEncoder passwordEncoder;

    //@Value("${jwt.secretService}")
    private static String secret = "bQvYcVQ6GrMGsvMeLZ1Fas5HO+9Vq8coJx+0P4AiyjY=";


    @Override
    public ResponseEntity<CompleteAuthDTO> execute(AuthDTO authDTO, User user) {
        validate(authDTO,user);
        String jwtToken = operate(authDTO,user);
        return ResponseEntity.status(200).body(buildResponse(jwtToken,user,authDTO));
    }

    @Override
    public void validate(AuthDTO authDTO, User user) {
        validateCorrectUsername(authDTO);
        validateCorrectPassword(authDTO);
    }

    private void validateCorrectUsername(AuthDTO authDTO){
        Optional<User> optionalUser = userRepository.getByUsernameOptional(authDTO.getUsername());
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Username "+authDTO.getUsername()+" not found");
        }else {
            cache.put("user",optionalUser.get());
        }
    }

    private void validateCorrectPassword(AuthDTO authDTO){
        User user = (User) cache.get("user");
        if(!checkCorrectPasswords(user.getPassword(),authDTO.getPassword())){
            throw new IncorrectPasswordException("Password "+authDTO.getPassword()+" is incorrect");
        }
    }

    private boolean checkCorrectPasswords(String encodePassword,String dtoPassword){
        return passwordEncoder.matches(dtoPassword,encodePassword);
    }



    @Override
    public String operate(AuthDTO authDTO, User user) {
        writeCountLoginUser();
        String jwt = generateJWTToken(authDTO);
        return jwt;
    }

    private void writeCountLoginUser(){
        //todo создать репозиторий и entity для работы c CountUserEntity
    }

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

    @Override
    public CompleteAuthDTO buildResponse(String s, User user, AuthDTO authDTO) {
        return new CompleteAuthDTO(
                s,authDTO.getUsername()
        );
    }
}
