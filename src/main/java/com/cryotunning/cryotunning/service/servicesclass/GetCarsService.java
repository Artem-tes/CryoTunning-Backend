package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.vehicleexception.UserHaveNotCarsException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerServiceNotHaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class GetCarsService implements BaseControllerServiceNotHaveRequestDto<LinkedList<CarResponseDTO>,LinkedList<CarEntity>> {


    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final GenerationRepository generationRepository;
    private final ColorRepository colorRepository;

    @Override
    public ResponseEntity<LinkedList<CarResponseDTO>> execute(User user) {
        validate(user);
        LinkedList<CarEntity> carEntities = operate(user);
        return ResponseEntity.status(200).body(buildResponse(user,carEntities));
    }

    @Override
    public void validate(User user) {
        checkUserHaveCar(user);
    }
    //validate block
    private void checkUserHaveCar(User user){
        if(carRepository.getAllCarById(user.getId()).isEmpty()){
            throw new UserHaveNotCarsException("user hav not car " +
                    "|| checkUserHaveCar|validate|GetCarsService");
        }
    }

    @Override
    public LinkedList<CarEntity> operate(User user) {
        LinkedList<CarEntity> carEntities = getAllCarById(user.getId());
        return carEntities;
    }
    // operate block
    private LinkedList<CarEntity> getAllCarById(Integer idUser){
        return carRepository.getAllCarById(idUser).get();
    }

    @Override
    public LinkedList<CarResponseDTO> buildResponse(User user,LinkedList<CarEntity> carEntities) {
        LinkedList<CarResponseDTO> carResponseDTOS = new LinkedList<>();
        for (CarEntity carEntity : carEntities) {
            carResponseDTOS.add(new CarResponseDTO(
                    carEntity.getId(),
                    brandRepository.findById(carEntity.getBrandId()).get().getBrandName(),
                    modelRepository.findById(carEntity.getModelId()).get().getModelName(),
                    generationRepository.findById(carEntity.getGenerationId()).get().getGenerationName(),
                    colorRepository.findById(carEntity.getColorId()).get().getColorName()
            ));
        }
        return carResponseDTOS;
    }
}
