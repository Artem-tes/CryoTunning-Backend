package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Integer> {
    @Query(value = "SELECT custom_cars.id FROM custom_cars WHERE custom_cars.id_owner = :idOwner",nativeQuery = true)
    List<Integer> getAllIdCarsForUser(@Param("idOwner") Integer idOwner);

    @Query(value = "SELECT * FROM custom_cars WHERE id_owner = :idOwner",nativeQuery = true)
    Optional<LinkedList<CarEntity>> getAllCarById(@Param("idOwner") Integer idOwner);

    @Query(value = "DELETE * FROM custom_cars WHERE id = :id",nativeQuery = true)
    void deleteCarByd(@Param("id") Integer id);


}
