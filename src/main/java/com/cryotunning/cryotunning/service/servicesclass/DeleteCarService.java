package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.UserHaveNotCarsException;
import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.repository.carpackage.CarRepository;
import com.cryotunning.cryotunning.service.servicebase.BaseControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteCarService implements BaseControllerService<DeleteDto,EmptyFastFailObject,EmptyFastFailObject> {

    private final CarRepository carRepository;
    private CarEntity carEntity;

    @Override
    public ResponseEntity<EmptyFastFailObject> execute(DeleteDto deleteDto, User user) {
        validate(deleteDto,user);
        operate(deleteDto,user);
        return ResponseEntity.status(204).build();
    }


    //validate
    @Override
    public void validate(DeleteDto deleteDto, User user) {
        checkCorrectIdCar(deleteDto);
        checkUserOwnerShip(user);
    }

    private void checkCorrectIdCar(DeleteDto deleteDto){
        Optional<CarEntity> car =carRepository.findById(deleteDto.getIdCarToDelete());
        if(car.isEmpty()){
            throw new CarNotFountException("id car "+deleteDto.getIdCarToDelete()+"incorrect");
        }else {
            carEntity = car.get();
        }
    }

    private void checkUserOwnerShip(User user){
        if(!carEntity.getIdOwner().equals(user.getId())){
            throw new UserHaveNotCarsException("user is not owner car without id "+carEntity.getId());
        }
    }


    //operate block
    @Override
    public EmptyFastFailObject operate(DeleteDto deleteDto, User user) {
        // при удалении не нужен ответ из метода операции, для формирвания ответа
        operateDeleteCar();
        return new EmptyFastFailObject();
    }
    private void operateDeleteCar(){
         carRepository.delete(carEntity);
    }

    //build response
    @Override
    public EmptyFastFailObject buildResponse(EmptyFastFailObject entity, User user, DeleteDto deleteDto) {
        return new EmptyFastFailObject();
    }
}
