package com.cryotunning.cryotunning.controllers.vehiclecontroller;

import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.servicesclass.modification.CreateModificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Контроллер модификаций",
        description = "Позволяет работать с модификациями у автомобилей"

)

@RestController
@RequiredArgsConstructor
public class ModificationController {

    private final UserRepository userRepository;
    private final CreateModificationService createModificationService;

    @GetMapping("/api/user/cars/modification/{idAuto}")
    public ResponseEntity<?> getModification(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("idAuto") int id){
        return createModificationService.execute(id,userRepository.findByUsername(userDetails.getUsername()));
    }
}
