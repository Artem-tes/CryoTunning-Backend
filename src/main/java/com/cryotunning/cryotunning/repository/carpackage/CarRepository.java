package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Integer> {
    @Query(value = "SELECT custom_cars.id FROM custom_cars WHERE custom_cars.id_owner = :idOwner",nativeQuery = true)
    List<Integer> getAllIdCarsForUser(@Param("idOwner") Integer idOwner);

}
