package com.cryotunning.cryotunning.service.servicesclass.modification;

import com.cryotunning.cryotunning.customexception.vehicleexception.CarIsOwningModification;
import com.cryotunning.cryotunning.customexception.vehicleexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserHasNotCarException;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.repository.carpackage.CarRepository;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerServiceWithoutResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateModificationService implements BaseControllerServiceWithoutResponseBody<Integer> {

    private final CarRepository carRepository;


    @Override
    public ResponseEntity<?> execute(Integer id, User user) {
        validate(id,user);
        operate(id,user);
        return ResponseEntity.status(204).build();
    }

    @Override
    public void validate(Integer id, User user) {
        validateCarIsBe(id);
        validateUserOwnerShipCar(id,user);
        validateCarHaveModification(id);
    }

    private void validateCarIsBe(Integer id){
        Optional<CarEntity> carEntity = carRepository.findById(id);
        if(carEntity.isEmpty()){
            throw new CarNotFountException("Car without id = "+id+" not found");
        }
    }

    private void validateUserOwnerShipCar(Integer id,User user){
        CarEntity carEntity = carRepository.findById(id).get();
        if(!carEntity.getIdOwner().equals(user.getId())){
            throw new UserHasNotCarException(
                    "User without id = "+user.getId()+"not have car without id = "+carEntity.getId());
        }
    }

    private void validateCarHaveModification(Integer id){
        CarEntity carEntity = carRepository.findById(id).get();
        if(carEntity.getIdModification() != null){
            throw new CarIsOwningModification(
                    "Car without id = "+id+"is owning modification ID = "+carEntity.getIdModification());
        }
    }


    @Override
    public void operate(Integer id, User user) {
        
    }
}
