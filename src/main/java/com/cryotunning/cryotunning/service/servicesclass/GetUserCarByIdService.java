package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import org.springframework.http.ResponseEntity;

public class GetUserCarByIdService implements BaseControllerService<GetCarRequestDTO, CarResponseDTO, CarEntity> {

    @Override
    public ResponseEntity<CarResponseDTO> execute(GetCarRequestDTO getCarRequestDTO, User user) {
        validate(getCarRequestDTO,user);
        CarEntity operateResult = operate(getCarRequestDTO,user);
        CarResponseDTO responseToController = buildResponse(operateResult,user,getCarRequestDTO);
        return ResponseEntity.status(200).body(responseToController);
    }

    @Override
    public void validate(GetCarRequestDTO getCarRequestDTO, User user) {

    }

    @Override
    public CarEntity operate(GetCarRequestDTO getCarRequestDTO, User user) {
        return null;
    }

    @Override
    public CarResponseDTO buildResponse(CarEntity carEntity, User user, GetCarRequestDTO getCarRequestDTO) {
        return new CarResponseDTO(

        );
    }
}
