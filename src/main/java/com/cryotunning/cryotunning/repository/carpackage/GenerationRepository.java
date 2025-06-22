package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenerationRepository extends JpaRepository<Generation,Integer> {
    @Query(value = "SELECT generations.id FROM generations WHERE generations.generation_name = :genName",nativeQuery = true)
    Integer getIdByGenerationName(@Param("genName") String generationName);

}
