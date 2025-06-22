package com.cryotunning.cryotunning;

import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.dbentities.*;
import com.cryotunning.cryotunning.repository.UserRepository;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.serviceimpl.VehicleServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ColorRepository colorRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private ModelRepository modelRepository;
    @Mock
    private GenerationRepository generationRepository;

    @InjectMocks
    private VehicleServiceIMPL vehicleService;

    @Test
    public void testGetCarById(){

        /// /// id owner = 2
        User mockUser = new User(2,null,null,null);

        CarEntity mockCar = new CarEntity();
        mockCar.setId(1);
        mockCar.setIdOwner(2);
        mockCar.setBrandId(3);
        mockCar.setModelId(4);
        mockCar.setGenerationId(5);
        mockCar.setColorId(6);

        // validate calls
        when(carRepository.findById(1)).thenReturn(Optional.of(mockCar));
        when(brandRepository.findById(3)).thenReturn(Optional.of(new Brand(null,null)));
        when(modelRepository.findById(4)).thenReturn(Optional.of(new Model(null,null,null)));
        when(generationRepository.findById(5)).thenReturn(Optional.of(new Generation(null,null,null)));
        when(colorRepository.findById(6)).thenReturn(Optional.of(new Color(null,null)));

        //act
        ResponseEntity<?> response = vehicleService.getCarById(mockUser,1);

        // assert
        assertEquals(response.getStatusCodeValue(),200);

    }

}
