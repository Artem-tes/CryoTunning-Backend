package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,Integer> {
    @Query(value = "SELECT colors.id FROM colors WHERE color_name = :colorName",nativeQuery = true)
    Integer getIdByNameColor(@Param("colorName") String colorName);

    @Query(value = "SELECT colors.id FROM colors WHERE color_name = :colorName",nativeQuery = true)
    Optional<Integer> getIdByNameColorOptional(@Param("colorName") String colorName);


}
