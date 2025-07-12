package com.cryotunning.cryotunning;

import com.cryotunning.cryotunning.customexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.UserHaveNotCarsException;
import com.cryotunning.cryotunning.entities.User;
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
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteCarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarEntity carEntity =
            new CarEntity(1,1,1,1,1,1,1);

    @InjectMocks
    DeleteCarService deleteCarService = new DeleteCarService(carRepository);

    @Test
    public void positiveExecuteTest(){
        //arrange
        Mockito.when(carRepository.findById(1))
                .thenReturn(Optional.of(carEntity));
        User mockUser = new User();
        mockUser.setId(1);
        //Mockito.when(carRepository.delete(carEntity));
        // как делать в таких ситуацияях
        //act
        ResponseEntity<?> response =
                deleteCarService.execute(new DeleteDto(1),
                        mockUser);
        Assertions.assertEquals(204,response.getStatusCodeValue());
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
