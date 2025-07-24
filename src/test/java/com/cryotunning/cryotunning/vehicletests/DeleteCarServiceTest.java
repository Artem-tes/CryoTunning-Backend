package com.cryotunning.cryotunning.vehicletests;

import com.cryotunning.cryotunning.customexception.vehicleexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.vehicleexception.UserHaveNotCarsException;
import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.repository.carpackage.CarRepository;
import com.cryotunning.cryotunning.service.servicesclass.DeleteCarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteCarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    DeleteCarService deleteCarService;

    @Test
    public void positiveExecuteTest(){
        // arrange
        CarEntity carEntity = new CarEntity();
        carEntity.setIdOwner(1);
        carEntity.setId(1); // <- Добавлено: корректный id автомобиля
        DeleteDto deleteDto = new DeleteDto();
        deleteDto.setIdCarToDelete(1);

        Mockito.when(carRepository.findById(deleteDto.getIdCarToDelete()))
                .thenReturn(Optional.of(carEntity));

        User mockUser = new User();
        mockUser.setId(1);

        // Предположим, что deleteCarByd - void, сделаем стабирование так:
        Mockito.doNothing().when(carRepository).deleteCarByd(1);

        // act
        ResponseEntity<?> response = deleteCarService.execute(deleteDto, mockUser);

        // assert
        Assertions.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void negativeThrowCarNotFoundException(){
        //arrange
        Mockito.when(carRepository.findById(1))
                .thenReturn(Optional.empty());
        //act || assert
        Assertions.assertThrows(CarNotFountException.class,
                ()->deleteCarService.execute(new DeleteDto(1),new User()));
    }

    @Test
    public void negativeUserHaveNotCarsException(){
        CarEntity mockCar = new CarEntity();
        mockCar.setIdOwner(2);
        Mockito.when(carRepository.findById(1))
                .thenReturn(Optional.of(mockCar));
        User mockUser = new User();
        mockUser.setId(1);
        // act || assert
        Assertions.assertThrows(UserHaveNotCarsException.class,
                ()->deleteCarService.execute(new DeleteDto(1),mockUser));

    }


}
