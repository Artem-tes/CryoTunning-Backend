package com.cryotunning.cryotunning.vehicletests;

import com.cryotunning.cryotunning.customexception.vehicleexception.NullFindCarInfoException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserCannotCreateCarException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.*;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.servicesclass.CreateCarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreateCarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private ColorRepository colorRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private ModelRepository modelRepository;
    @Mock
    private GenerationRepository generationRepository;


    @InjectMocks
    CreateCarService createCarService;


    // simple data test
    @Test
    public void testCreateCarEndPoint() {
        //arrange valid method one
        CreateCarDTO mockCarDTO = new CreateCarDTO();
        mockCarDTO.setBrand("1");
        mockCarDTO.setModel("2");
        mockCarDTO.setGeneration("3");
        mockCarDTO.setColor("4");
        when(brandRepository.getIdByBrandNameOptional("1")).thenReturn(Optional.of(1));
        when(modelRepository.getIdByModelNameOptional("2")).thenReturn(Optional.of(2));
        when(generationRepository.getIdByGenerationNameOptional("3")).thenReturn(Optional.of(3));
        when(colorRepository.getIdByNameColorOptional("4")).thenReturn(Optional.of(4));
        //arrange valid method two
        User mockUser = new User();
        mockUser.setId(1);
        LinkedList<CarEntity> listOfCar = new LinkedList<>();
        for (int i = 0; i < 1; i++) {
            listOfCar.add(new CarEntity());
        }
        when(carRepository.getAllCarById(1)).thenReturn(Optional.of(
                listOfCar
        ));

        when(carRepository.save(Mockito.any(CarEntity.class))).thenReturn(new CarEntity());
        //act
        ResponseEntity<CarResponseDTO> response = createCarService.execute(mockCarDTO,mockUser );
        //assert
        assertEquals(201, response.getStatusCodeValue());

    }

    // throw exception test
    // 1 - throw NullFieldCarInfoException(cannot resolve связи между id - name)
    @Test
    public void testNullFieldCarException(){
        // assert
        CreateCarDTO carDTO = new CreateCarDTO();
        carDTO.setColor("1");
        carDTO.setGeneration("1");
        carDTO.setModel("1");
        carDTO.setBrand("1");
        when(brandRepository.getIdByBrandNameOptional(carDTO.getBrand()))
                .thenReturn(Optional.empty());
        when(modelRepository.getIdByModelNameOptional(carDTO.getModel()))
                .thenReturn(Optional.empty());
        when(generationRepository.getIdByGenerationNameOptional(carDTO.getGeneration()))
                .thenReturn(Optional.empty());
        when(colorRepository.getIdByNameColorOptional(carDTO.getColor()))
                .thenReturn(Optional.empty());
        //check
        assertThrows(NullFindCarInfoException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createCarService.execute(carDTO,new User());
            }
        });
    }


}