package com.cryotunning.cryotunning.vehicletests;

import com.cryotunning.cryotunning.entities.dbentities.*;
import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Brand;
import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Color;
import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Generation;
import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Model;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.repository.carpackage.*;
import com.cryotunning.cryotunning.service.servicesclass.GetUserCarByIdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetCarByIDTest {
    @Mock
    CarRepository carRepository;
    @Mock
    BrandRepository brandRepository;
    @Mock
    ModelRepository modelRepository;
    @Mock
    GenerationRepository generationRepository;
    @Mock
    ColorRepository colorRepository;

    @InjectMocks
    GetUserCarByIdService getUserCarByIdService;

    @Test
    public void positiveExecuteTest(){
        User userMock = new User();
        userMock.setId(1);
        GetCarRequestDTO getCarRequestDTO = new GetCarRequestDTO(1);

        Mockito.when(carRepository.findById(1))
                .thenReturn(Optional.of(new CarEntity(
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1
                )));
        Mockito.when(brandRepository.findById(1))
                .thenReturn(Optional.of(new Brand()));
        Mockito.when(modelRepository.findById(1))
                .thenReturn(Optional.of(new Model()));
        Mockito.when(generationRepository.findById(1))
                .thenReturn(Optional.of(new Generation()));
        Mockito.when(colorRepository.findById(1))
                .thenReturn(Optional.of(new Color()));

        ResponseEntity<CarResponseDTO> carResponseDTOResponseEntity =
                getUserCarByIdService.execute(getCarRequestDTO,userMock);

        Assertions.assertEquals(HttpStatusCode.valueOf(200),
                carResponseDTOResponseEntity.getStatusCode());


    }





}
