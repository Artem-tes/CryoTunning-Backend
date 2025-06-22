package com.cryotunning.cryotunning.serviceimpl;

import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.entities.requestdto.ResponseLoginDTO;
import com.cryotunning.cryotunning.enums.ROLES;
import com.cryotunning.cryotunning.repository.UserRepository;
import com.cryotunning.cryotunning.service.AuthLoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthLoginServiceIMPL implements AuthLoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private static final String secret = "bQvYcVQ6GrMGsvMeLZ1Fas5HO+9Vq8coJx+0P4AiyjY=";

    @Override
    public ResponseEntity<String> regNewUser(AuthDTO authDTO){
        if(userRepository.findByUsername(authDTO.getUsername())!=null){
            return ResponseEntity.badRequest().body("username is own");
        }else {
            saveUser(authDTO);
            return ResponseEntity.status(200).body("success");
        }
    }

    private void saveUser(AuthDTO authDTO){
        userRepository.save(new User(
                null,
                authDTO.getUsername(),
                passwordEncoder.encode(authDTO.getPassword()),
                String.valueOf(ROLES.USER)
        ));
    }



    @Override
    public ResponseEntity<?> authAction(AuthDTO authDTO) {
        User user = userRepository.findByUsername(authDTO.getUsername());
        if(passwordEncoder.matches(authDTO.getPassword(),user.getPassword())){
            String token = generateJwtToken(authDTO.getUsername());
            return ResponseEntity.status(200).body(new ResponseLoginDTO(
                    token,
                    authDTO.getUsername()
            ));
        }else {
            return ResponseEntity.status(401).body("Bad Password");
        }
    }

    private String generateJwtToken(String username) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000); // +30 дней

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
