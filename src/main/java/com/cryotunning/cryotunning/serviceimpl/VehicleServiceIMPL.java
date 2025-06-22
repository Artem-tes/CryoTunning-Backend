package com.cryotunning.cryotunning.serviceimpl;

import com.cryotunning.cryotunning.customexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.NullFieldException;
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
        mainValidateGetCarById(user,idCar);
        //OPERATE
        CarEntity car = getCarByIdFromDb(idCar);
        //RESPONSE
        CarResponseDTO carResponseDTO = formResponseToGetCarByIdUSer(car);
        return ResponseEntity.status(200).body(carResponseDTO);
    }

    /// /// FORM RESPONSE BLOCK
    private CarResponseDTO formResponseToGetCarByIdUSer(CarEntity car){
        Integer idCar = car.getId();
        HashMap<String,String> carData = getNameAutoPartByIds(car);
        return new CarResponseDTO(idCar,carData.get("brand"),carData.get("model"),carData.get("generation"),carData.get("color"));
    }


    /// /// UTIL FORM RESPONSE METHOD
    private HashMap<String,String> getNameAutoPartByIds(CarEntity car){
        Brand brand = brandRepository.findById(car.getBrandId()).get();
        Model model = modelRepository.findById(car.getModelId()).get();
        Generation generation = generationRepository.findById(car.getGenerationId()).get();
        Color color = colorRepository.findById(car.getColorId()).get();
        HashMap<String,String> carInfoString = new HashMap<>();
        carInfoString.put("model",model.getModelName());
        carInfoString.put("brand",brand.getBrandName());
        carInfoString.put("generation",generation.getGenerationName());
        carInfoString.put("color",color.getColorName());
        return carInfoString;


    }

    /// /// MAIN OPERATE METHOD
    private CarEntity getCarByIdFromDb(Integer idCar){
        return carRepository.findById(idCar).get();
    }

    /// ///VALIDATE BLOCK
    private void mainValidateGetCarById(User user,Integer idCar){
        checkCarIsBe(idCar);
        isUserCarOwnerShip(user,idCar);
        isIdCarInfoIsCorrect(idCar);

    }

    private void isIdCarInfoIsCorrect(Integer idCar){
        CarEntity car = carRepository.findById(idCar).get();
        Brand brand = brandRepository.findById(car.getBrandId()).get();
        Model model = modelRepository.findById(car.getModelId()).get();
        Generation generation = generationRepository.findById(car.getGenerationId()).get();
        Color color = colorRepository.findById(car.getColorId()).get();
        if(brand == null||model==null||generation==null|color==null){
            throw new NullFindCarInfoException("Cannot find info car by id = "+idCar);
        }
    }

    private void isUserCarOwnerShip(User user,Integer idCar){
        CarEntity car = carRepository.findById(idCar).get();
        if(user.getId() != car.getIdOwner()){
            throw new UserHasNotCarException("User has not got car with id = "+idCar);
        }
    }

    private void checkCarIsBe(Integer idCar){
        Optional<CarEntity> car = carRepository.findById(idCar);
        if(!car.isPresent()){
            throw new CarNotFountException("NOT FOUND CAR WITH ID = "+idCar);
        }
    }
    /// /// VALIDATE BLOCK

    @Override
    public ResponseEntity<?> deleteCar(UserDetails userDetails, Integer idCar) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        validateUserOwnership(user,idCar);
        removeCar(idCar);
        return ResponseEntity.noContent().build();
    }


    //UTIL BLOCK

    private void removeCar(Integer idCar){
        carRepository.deleteById(idCar);
        /// /// todo когда добавятся модификации, также удлить и их!!

    }

    private void validateUserOwnership(User user,Integer carId){
        CarEntity car = getCarById(carId);
        checkUserCarUtil(car,user.getId());
    }


    // checkUserCar util block
    private CarEntity getCarById(Integer idCar){
        Optional<CarEntity> optionalCarEntity = carRepository.findById(idCar);
        if(optionalCarEntity.isPresent()){
            return optionalCarEntity.get();
        }else {
            throw new CarNotFountException("Car without id = "+idCar+" not found");
        }
    }

    void checkUserCarUtil(CarEntity car,Integer idUser){
        if(!car.getIdOwner().equals(idUser)){
            throw new UserHasNotCarException("user has not got car with id = "+car.getId());
        }
    }

    // checkUserCar util block


    // UTIL BLOCK

    @Override
    public ResponseEntity<?> getUserCars(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Integer> carsId = getUserCarId(user);
        List<CarEntity> cars = getAllUserCarById(carsId);
        List<CarResponseDTO> responseDTOSList = formResponseToClient(cars);
        return ResponseEntity.status(200).body(responseDTOSList);
    }

    private List<CarResponseDTO> formResponseToClient(List<CarEntity> carEntities){
        List<CarResponseDTO> carResponseDTOS = new LinkedList<>();
        for (CarEntity car : carEntities) {
            CarResponseDTO carResponseDTO = new CarResponseDTO();
            carResponseDTO.setId(car.getId());
            String brandCar = brandRepository.findById(car.getBrandId()).get().getBrandName();
            String modelCar = modelRepository.findById(car.getModelId()).get().getModelName();
            String generationCar = generationRepository.findById(car.getGenerationId()).get().getGenerationName();
            String colorCar = colorRepository.findById(car.getColorId()).get().getColorName();
            if(brandCar.isEmpty() || modelCar.isEmpty() || generationCar.isEmpty() || colorCar.isEmpty()){
                throw new NullFindCarInfoException("");
            }else {
                carResponseDTO.setBrand(brandCar);
                carResponseDTO.setModel(modelCar);

                carResponseDTO.setGeneration(generationCar);
                carResponseDTO.setColor(colorCar);
            }
            carResponseDTOS.add(carResponseDTO);
        }
        return carResponseDTOS;
    }


    private List<CarEntity> getAllUserCarById(List<Integer> carsId){
        List<CarEntity> cars = new LinkedList<>();
        for (Integer id : carsId) {
            if(carRepository.findById(id).isPresent()){
                cars.add(carRepository.findById(id).get());
            }
        }
        return cars;
    }


    private List<Integer> getUserCarId(User user){
        List<Integer> carsId = carRepository.getAllIdCarsForUser(user.getId());
        return carsId;
    }





    @Override
    public ResponseEntity<?> createCar(UserDetails userDetails, CreateCarDTO createCarDTO) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        //получаем массив из элементов полученых по ID
        HashMap<String,Integer> carDataId = parseCreateCarDTO(createCarDTO);
        try {
            CarEntity car = saveCarToDB(carDataId,user);
            return ResponseEntity.status(200).body(formResponseCarDTO(car));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("error from save car modul");
        }
    }

    private CarEntity saveCarToDB(HashMap<String,Integer> carData, User user){
         CarEntity newCar = carRepository.save(new CarEntity(
                null,
                 user.getId(),
                null,
                carData.get("brand"),
                carData.get("model"),
                carData.get("generation"),
                carData.get("color")
        ));
         return newCar;
    }

    /// /// UTIL BLOCK
    private CarResponseDTO formResponseCarDTO(CarEntity car){
        return new CarResponseDTO(
                car.getId(),
                String.valueOf(brandRepository.findById(car.getBrandId())),
                String.valueOf(modelRepository.findById(car.getModelId())),
                String.valueOf(generationRepository.findById(car.getGenerationId())),
                String.valueOf(colorRepository.findById(car.getColorId()))
        );
    }

    private HashMap<String, Integer> parseCreateCarDTO(CreateCarDTO createCarDTO) {
        HashMap<String, Integer> itemsIdCar = new HashMap<>();

        Integer colorId = colorRepository.getIdByNameColor(createCarDTO.getColor());
        if (colorId == null) throw new NullFieldException("Color not found: " + createCarDTO.getColor());
        itemsIdCar.put("color", colorId);

        Integer brandId = brandRepository.getIdByBrandName(createCarDTO.getBrand());
        if (brandId == null) throw new NullFieldException("Brand not found: " + createCarDTO.getBrand());
        itemsIdCar.put("brand", brandId);

        Integer modelId = modelRepository.getIdByModelName(createCarDTO.getModel());
        if (modelId == null) throw new NullFieldException("Model not found: " + createCarDTO.getModel());
        itemsIdCar.put("model", modelId);

        Integer generationId = generationRepository.getIdByGenerationName(createCarDTO.getGeneration());
        if (generationId == null) throw new NullFieldException("Generation not found: " + createCarDTO.getGeneration());
        itemsIdCar.put("generation", generationId);

        return itemsIdCar;
    }

    /// ///UTIL BLOCK

}
