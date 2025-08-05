package com.cryotunning.cryotunning.service.servicesclass.modification;

import com.cryotunning.cryotunning.customexception.vehicleexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserHasNotCarException;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.modifications.Modification;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.responsesto.ModificationDTO;
import com.cryotunning.cryotunning.repository.carpackage.CarRepository;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetModificationService implements BaseControllerService<Integer, ModificationDTO, Modification> {

    private final CarRepository carRepository;

    @Override
    public ResponseEntity<ModificationDTO> execute(Integer id, User user) {
        validate(id,user);
        Modification modification = operate(id,user);
        return ResponseEntity.status(200).body(buildResponse(modification,user,id));
    }

    @Override
    public void validate(Integer id, User user) {
        checkCarIsBe(id);
        checkCarIsOwnUser(user,id);
        checkCarIsOwnModification(id);

    }

    private void checkCarIsBe(Integer id){
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        if(carEntityOptional.isEmpty()){
            throw new CarNotFountException("Car without ID = "+id+" not found");
        }
    }

    private void checkCarIsOwnUser(User user,Integer id){
        CarEntity carEntity = carRepository.findById(id).get();
        if(!carEntity.getIdOwner().equals(user.getId())){
            throw new UserHasNotCarException("User with id = "+user.getId()+" not have car with id = "+carEntity.getId());
        }
    }

    private void checkCarIsOwnModification(Integer id){
        
    }
    

    @Override
    public Modification operate(Integer id, User user) {
        return null;
    }

    @Override
    public ModificationDTO buildResponse(Modification modification, User user, Integer id) {
        return null;
    }
}
