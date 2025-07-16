package com.cryotunning.cryotunning;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.*;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.servicesclass.GetCarsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCarsServiceTest {
    @Mock
    private  CarRepository carRepository;
    @Mock
    private  BrandRepository brandRepository;
    @Mock
    private  ModelRepository modelRepository;
    @Mock
    private  GenerationRepository generationRepository;
    @Mock
    private  ColorRepository colorRepository;

    @InjectMocks
    GetCarsService getCarsService = new GetCarsService(carRepository,brandRepository,modelRepository,generationRepository,colorRepository);

    @Test
    public void positiveGetCarsServiceTest(){
        //arrange
        User mockUser = new User(
                1,null,null,null);
        CarEntity carEntity = new CarEntity(1,1,1,1,1,1,1);
        LinkedList<CarEntity> carEntities = new LinkedList<>();
        carEntities.add(carEntity);
        when(carRepository.getAllCarById(mockUser.getId()))
                .thenReturn(Optional.of(carEntities));
        when(brandRepository.findById(1)).thenReturn(Optional.of(new Brand()));
        when(modelRepository.findById(1)).thenReturn(Optional.of(new Model()));
        when(generationRepository.findById(1)).thenReturn(Optional.of(new Generation()));
        when(colorRepository.findById(1)).thenReturn(Optional.of(new Color()));
        //act
        ResponseEntity<?> response = getCarsService.execute(mockUser);
        //assert
        Assertions.assertEquals(200,response.getStatusCodeValue());

    }
}
