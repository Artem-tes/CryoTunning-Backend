package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.vehicleexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.vehicleexception.NullFindCarInfoException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserHasNotCarException;
import com.cryotunning.cryotunning.entities.dbentities.*;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserCarByIdService implements BaseControllerService<GetCarRequestDTO, CarResponseDTO, CarEntity> {

    private final CarRepository carRepository;
    private HashMap<String,Object> cache = new HashMap<>();
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final GenerationRepository generationRepository;
    private final ColorRepository colorRepository;

    @Override
    public ResponseEntity<CarResponseDTO> execute(GetCarRequestDTO getCarRequestDTO, User user) {
        validate(getCarRequestDTO,user);
        CarEntity operateResult = operate(getCarRequestDTO,user);
        CarResponseDTO responseToController = buildResponse(operateResult,user,getCarRequestDTO);
        return ResponseEntity.status(200).body(responseToController);
    }

    @Override
    public void validate(GetCarRequestDTO getCarRequestDTO, User user) {
        validatePathIdCarIsCorrect(getCarRequestDTO);
        validateUserOwnerShip(user);
        validateCorrectCarEntityIdCharacter();
    }

    private void validatePathIdCarIsCorrect(GetCarRequestDTO getCarRequestDTO){
        Optional<CarEntity> carEntityOptional = carRepository.findById(getCarRequestDTO.getCarId());
        if(carEntityOptional.isEmpty()){
            throw new CarNotFountException("Car without ID = "+getCarRequestDTO.getCarId()+" not found");
        }
        cache.put("car",carEntityOptional.get());
    }

    private void validateUserOwnerShip(User user){
        CarEntity carEntity = (CarEntity) cache.get("car");
        if(!carEntity.getIdOwner().equals(user.getId())){
            throw new UserHasNotCarException("User without id = "+user.getId()+
                    " not ownership car with id = "+carEntity.getId());
        }
    }

    private void validateCorrectCarEntityIdCharacter(){
        CarEntity carEntity = (CarEntity) cache.get("car");
        Optional<Brand> brand = brandRepository.findById(carEntity.getBrandId());
        Optional<Model> model = modelRepository.findById(carEntity.getModelId());
        Optional<Generation> generation = generationRepository.findById(carEntity.getGenerationId());
        Optional<Color> color = colorRepository.findById(carEntity.getColorId());
        if(brand.isEmpty() || model.isEmpty() || generation.isEmpty() || color.isEmpty()){
            throw new NullFindCarInfoException("Car with ID = "+carEntity.getId()+
                    "one or many filed is null");
        }
     }


    @Override
    public CarEntity operate(GetCarRequestDTO getCarRequestDTO, User user) {
        return getCarEntityById(getCarRequestDTO);
    }

    private CarEntity getCarEntityById(GetCarRequestDTO getCarRequestDTO){
        return carRepository.findById(getCarRequestDTO.getCarId()).get();
    }

    @Override
    public CarResponseDTO buildResponse(CarEntity carEntity, User user, GetCarRequestDTO getCarRequestDTO) {
        return new CarResponseDTO(
                carEntity.getId(),
                brandRepository.findById(carEntity.getBrandId()).get().getBrandName(),
                modelRepository.findById(carEntity.getModelId()).get().getModelName(),
                generationRepository.findById(carEntity.getGenerationId()).get().getGenerationName(),
                colorRepository.findById(carEntity.getColorId()).get().getColorName()
        );
    }
}
