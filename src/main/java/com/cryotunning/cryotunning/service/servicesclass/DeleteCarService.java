package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.vehicleexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserHaveNotCarsException;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.repository.carpackage.CarRepository;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerServiceWithoutResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteCarService implements BaseControllerServiceWithoutResponseBody<DeleteDto> {

    private final CarRepository carRepository;
    HashMap<String, Object> cache = new HashMap<>();

    @Override
    public ResponseEntity<?> execute(DeleteDto deleteDto, User user) {
        validate(deleteDto,user);
        operate(deleteDto,user);
        return ResponseEntity.status(204).build();
    }

    @Override
    public void validate(DeleteDto deleteDto, User user) {
        validateCarIsBe(deleteDto);
        validateUserOwnerShip(user);
    }

    private void validateCarIsBe(DeleteDto deleteDto){
        Optional<CarEntity> carEntityOptional = carRepository.findById(deleteDto.getIdCarToDelete());
        if(carEntityOptional.isEmpty()){
            throw new CarNotFountException("car with out id = "+deleteDto.getIdCarToDelete()+" not found");
        }else {
            cache.put("car",carEntityOptional.get());
        }
    }

    private void validateUserOwnerShip(User user){
        CarEntity carEntity = (CarEntity) cache.get("car");
        if(!user.getId().equals(carEntity.getIdOwner())){
            throw new UserHaveNotCarsException(
                    "user without id = "+user.getId()+" not have car without id = "+carEntity.getId());
        }
    }

    @Override
    public void operate(DeleteDto deleteDto,User user) {
        deleteCarById();
    }

    @Transactional
    private void deleteCarById(){
        carRepository.delete((CarEntity) cache.get("car"));
    }
}
