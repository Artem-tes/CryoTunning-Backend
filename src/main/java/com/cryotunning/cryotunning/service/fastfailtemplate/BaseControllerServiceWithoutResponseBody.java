package com.cryotunning.cryotunning.service.fastfailtemplate;

import com.cryotunning.cryotunning.entities.dbentities.User;
import org.springframework.http.ResponseEntity;

public interface BaseControllerServiceWithoutResponseBody<REQUEST_DTO>{
    ResponseEntity<?> execute(REQUEST_DTO requestDto, User user);
    void validate(REQUEST_DTO requestDto,User user);
    void operate(REQUEST_DTO requestDto,User user);
}
