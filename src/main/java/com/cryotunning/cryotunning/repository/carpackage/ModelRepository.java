package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModelRepository extends JpaRepository<Model,Integer> {
    @Query(value = "SELECT models.id FROM models WHERE models.model_name = :modelName",nativeQuery = true)
    Integer getIdByModelName(@Param("modelName") String modelName);

}
