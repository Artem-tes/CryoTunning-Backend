package com.cryotunning.cryotunning.service.servicesclass;

import com.cryotunning.cryotunning.customexception.vehicleexception.NullFindCarInfoException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserCannotCreateCarException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.fastfailtemplate.BaseControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CreateCarService implements BaseControllerService<CreateCarDTO, CarResponseDTO,CarEntity> {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final GenerationRepository generationRepository;
    private final ColorRepository colorRepository;
    private final CarRepository carRepository;

    // CONTROLLER METHOD
    @Override
    public ResponseEntity<CarResponseDTO> execute(CreateCarDTO createCarDTO, User user) {
        validate(createCarDTO,user);
        CarEntity saveEntity = operate(createCarDTO,user);
        return ResponseEntity.status(201).body(buildResponse(saveEntity,user,createCarDTO));
    }

    //VALIDATE
    @Override
    public void validate(CreateCarDTO createCarDTO, User user) {
        checkUserCanCreateCar(user);
        checkCarInfo(createCarDTO);
    }

    private void checkCarInfo(CreateCarDTO carDTO){
        Optional<Integer> brand = brandRepository.getIdByBrandNameOptional(carDTO.getBrand());
        Optional<Integer> model = modelRepository.getIdByModelNameOptional(carDTO.getModel());
        Optional<Integer> color = colorRepository.getIdByNameColorOptional(carDTO.getColor());
        Optional<Integer> generation = generationRepository.getIdByGenerationNameOptional(carDTO.getGeneration());
        if(brand.isEmpty() || model.isEmpty() || color.isEmpty() || generation.isEmpty()) {
            throw new NullFindCarInfoException("Data from Client incorrect");
        }
    }


    private void checkUserCanCreateCar(User user){
        Optional<LinkedList<CarEntity>> userCars = carRepository.getAllCarById(user.getId());
        if (!userCars.isEmpty()){
            if(userCars.get().size() > 6){
                throw new UserCannotCreateCarException("User cannot create car becouse exit limit for 6 car," +
                        "user was be premium to create more 6 cars");
            }
        }
    }


    //OPERATE
    @Override
    public CarEntity operate(CreateCarDTO createCarDTO, User user) {
        CarEntity carEntity = saveCar(createCarDTO,user);
        return carEntity;
    }

    @Transactional
    private CarEntity saveCar(CreateCarDTO createCarDTO,User user){
        return carRepository.save(
                new CarEntity(
                        null,
                        user.getId(),
                        null,
                        brandRepository.getIdByBrandNameOptional(createCarDTO.getBrand()).get(),
                        modelRepository.getIdByModelNameOptional(createCarDTO.getModel()).get(),
                        generationRepository.getIdByGenerationNameOptional(createCarDTO.getGeneration()).get(),
                        colorRepository.getIdByNameColorOptional(createCarDTO.getColor()).get()
                )
        );
    }

    // FORM RESPONSE
    @Override
    public CarResponseDTO buildResponse(CarEntity entity, User user, CreateCarDTO createCarDTO) {
        return new CarResponseDTO(
                entity.getId(),
                createCarDTO.getBrand(),
                createCarDTO.getModel(),
                createCarDTO.getGeneration(),
                createCarDTO.getColor()
        );
    }
}
