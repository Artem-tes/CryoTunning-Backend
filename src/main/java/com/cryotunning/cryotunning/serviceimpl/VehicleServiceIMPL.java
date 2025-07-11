package com.cryotunning.cryotunning.serviceimpl;
import com.cryotunning.cryotunning.customexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.NullFindCarInfoException;
import com.cryotunning.cryotunning.customexception.UserHasNotCarException;
import com.cryotunning.cryotunning.entities.dbentities.*;
import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.requestdto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.repository.*;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceIMPL implements VehicleService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final GenerationRepository generationRepository;

    /// /// MAIN METHOD
    @Override
    public ResponseEntity<CarResponseDTO> getCarById(User user, Integer idCar) {
        //VALIDATE
        mainValidateGetCarById(user, idCar);
        //OPERATE
        CarEntity car = getCarByIdFromDb(idCar);
        //RESPONSE
        CarResponseDTO carResponseDTO = formResponseToGetCarByIdUSer(car);
        return ResponseEntity.status(200).body(carResponseDTO);
    }

    /// /// FORM RESPONSE BLOCK
    private CarResponseDTO formResponseToGetCarByIdUSer(CarEntity car) {
        Integer idCar = car.getId();
        HashMap<String, String> carData = getNameAutoPartByIds(car);
        return new CarResponseDTO(idCar, carData.get("brand"), carData.get("model"), carData.get("generation"), carData.get("color"));
    }


    /// /// UTIL FORM RESPONSE METHOD
    private HashMap<String, String> getNameAutoPartByIds(CarEntity car) {
        Brand brand = brandRepository.findById(car.getBrandId()).get();
        Model model = modelRepository.findById(car.getModelId()).get();
        Generation generation = generationRepository.findById(car.getGenerationId()).get();
        Color color = colorRepository.findById(car.getColorId()).get();
        HashMap<String, String> carInfoString = new HashMap<>();
        carInfoString.put("model", model.getModelName());
        carInfoString.put("brand", brand.getBrandName());
        carInfoString.put("generation", generation.getGenerationName());
        carInfoString.put("color", color.getColorName());
        return carInfoString;


    }

    /// /// MAIN OPERATE METHOD
    private CarEntity getCarByIdFromDb(Integer idCar) {
        return carRepository.findById(idCar).get();
    }

    /// ///VALIDATE BLOCK
    private void mainValidateGetCarById(User user, Integer idCar) {
        checkCarIsBe(idCar);
        isUserCarOwnerShip(user, idCar);
        isIdCarInfoIsCorrect(idCar);

    }

    private void isIdCarInfoIsCorrect(Integer idCar) {
        CarEntity car = carRepository.findById(idCar).get();
        Optional<Brand> brand = brandRepository.findById(car.getBrandId());
        Optional<Model> model = modelRepository.findById(car.getModelId());
        Optional<Generation> generation = generationRepository.findById(car.getGenerationId());
        Optional<Color> color = colorRepository.findById(car.getColorId());
        if (brand.isEmpty() || model.isEmpty() || generation.isEmpty() | color.isEmpty()) {
            throw new NullFindCarInfoException("Cannot find info car by id = " + idCar);
        }
    }

    private void isUserCarOwnerShip(User user, Integer idCar) {
        CarEntity car = carRepository.findById(idCar).get();
        if (user.getId() != car.getIdOwner()) {
            throw new UserHasNotCarException("User has not got car with id = " + idCar);
        }
    }

    private void checkCarIsBe(Integer idCar) {
        Optional<CarEntity> car = carRepository.findById(idCar);
        if (!car.isPresent()) {
            throw new CarNotFountException("NOT FOUND CAR WITH ID = " + idCar);
        }
    }

    /// /// VALIDATE BLOCK



    /// /// POST /api/user/cars
    /// RESPONSE - CarResponseDTO
    public ResponseEntity<?> createCar(User user, CreateCarDTO createCarDTO) {
        //VALIDATE
        validateCreateCar(createCarDTO);
        //OPERATE
        CarEntity car = operateCreateCar(createCarDTO, user);
        //FORM RESPONSE
        return ResponseEntity.status(200).body(formResponseCreateCar(createCarDTO,car));

    }

    // FORM RESPONSE
    private CarResponseDTO formResponseCreateCar(CreateCarDTO createCarDTO,
                                                 CarEntity carEntity){
        return new CarResponseDTO(
                carEntity.getId(),
                createCarDTO.getBrand(),
                createCarDTO.getModel(),
                createCarDTO.getGeneration(),
                createCarDTO.getColor()
        );
    }

    // OPERATE METHOD
    private CarEntity operateCreateCar(CreateCarDTO createCarDTO, User user){
        return carRepository.save(new CarEntity(
                null,
                user.getId(),
                null,
                brandRepository.getIdByBrandNameOptional(createCarDTO.getBrand()).get(),
                modelRepository.getIdByModelNameOptional(createCarDTO.getModel()).get(),
                generationRepository.getIdByGenerationNameOptional
                        (createCarDTO.getGeneration()).get(),
                colorRepository.getIdByNameColorOptional(createCarDTO.getColor()).get()
        ));
    }

    // MAIN VALIDATE METHOD
    private void validateCreateCar(CreateCarDTO createCarDTO){
        validateCarData(createCarDTO);
    }

    // VALIDATE INCORRECT CAR DATA

    private void validateCarData(CreateCarDTO createCarDTO) {
        String brand = createCarDTO.getBrand();
        String model = createCarDTO.getModel();
        String generation = createCarDTO.getGeneration();
        String color = createCarDTO.getColor();
        if (!brandRepository.getIdByBrandNameOptional(brand).isPresent() ||
                !modelRepository.getIdByModelNameOptional(model).isPresent() ||
                !generationRepository.getIdByGenerationNameOptional(generation)
                        .isPresent() || !colorRepository.getIdByNameColorOptional(color)
                .isPresent()) {
            throw new NullFindCarInfoException("cannot found car info || bad request");

        }
    }




    @Override
    public ResponseEntity<?> deleteCar(User user, Integer idCar) {
        return null;
    }


    //UTIL BLOCK



    // UTIL BLOCK

    @Override
    public ResponseEntity<?> getUserCars(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Integer> carsId = getUserCarId(user);
        List<CarEntity> cars = getAllUserCarById(carsId);
        List<CarResponseDTO> responseDTOSList = formResponseToClient(cars);
        return ResponseEntity.status(200).body(responseDTOSList);
    }

    private List<CarResponseDTO> formResponseToClient(List<CarEntity> carEntities) {
        List<CarResponseDTO> carResponseDTOS = new LinkedList<>();
        for (CarEntity car : carEntities) {
            CarResponseDTO carResponseDTO = new CarResponseDTO();
            carResponseDTO.setId(car.getId());
            String brandCar = brandRepository.findById(car.getBrandId()).get().getBrandName();
            String modelCar = modelRepository.findById(car.getModelId()).get().getModelName();
            String generationCar = generationRepository.findById(car.getGenerationId()).get().getGenerationName();
            String colorCar = colorRepository.findById(car.getColorId()).get().getColorName();
            if (brandCar.isEmpty() || modelCar.isEmpty() || generationCar.isEmpty() || colorCar.isEmpty()) {
                throw new NullFindCarInfoException("");
            } else {
                carResponseDTO.setBrand(brandCar);
                carResponseDTO.setModel(modelCar);

                carResponseDTO.setGeneration(generationCar);
                carResponseDTO.setColor(colorCar);
            }
            carResponseDTOS.add(carResponseDTO);
        }
        return carResponseDTOS;
    }


    private List<CarEntity> getAllUserCarById(List<Integer> carsId) {
        List<CarEntity> cars = new LinkedList<>();
        for (Integer id : carsId) {
            if (carRepository.findById(id).isPresent()) {
                cars.add(carRepository.findById(id).get());
            }
        }
        return cars;
    }


    private List<Integer> getUserCarId(User user) {
        List<Integer> carsId = carRepository.getAllIdCarsForUser(user.getId());
        return carsId;
    }


}