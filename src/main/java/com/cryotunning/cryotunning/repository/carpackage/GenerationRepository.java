package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenerationRepository extends JpaRepository<Generation,Integer> {
    @Query(value = "SELECT generations.id FROM generations WHERE generations.generation_name = :genName",nativeQuery = true)
    Integer getIdByGenerationName(@Param("genName") String generationName);

    @Query(value = "SELECT generations.id FROM generations WHERE generations.generation_name = :genName",nativeQuery = true)
    Optional<Integer> getIdByGenerationNameOptional(@Param("genName") String generationName);

}
