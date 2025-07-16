package com.cryotunning.cryotunning.service.servicebase;

import com.cryotunning.cryotunning.entities.dbentities.User;
import org.springframework.http.ResponseEntity;

public interface BaseControllerService<DTO,RETURN_DTO,OPERATE_RESULT> {
    ResponseEntity<RETURN_DTO> execute(DTO dto, User user);

    void validate(DTO dto, User user);

    OPERATE_RESULT operate(DTO dto, User user);

    RETURN_DTO buildResponse(OPERATE_RESULT operateResult, User user, DTO dto);
}
