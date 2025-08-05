package com.cryotunning.cryotunning.repository.carpackage;

import com.cryotunning.cryotunning.entities.dbentities.vehiclebase.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {
    @Query(value = "SELECT brands.id FROM brands WHERE brands.brand_name = :brandName",nativeQuery = true)
    Integer getIdByBrandName(@Param("brandName") String brandName);

    @Query(value = "SELECT brands.id FROM brands WHERE brands.brand_name = :brandName",nativeQuery = true)
    Optional<Integer> getIdByBrandNameOptional(@Param("brandName") String brandName);


}
