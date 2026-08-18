package com.cleverson.help_desk.service.presentation.create;

import com.cleverson.help_desk.service.application.dto.CreateServiceInput;
import com.cleverson.help_desk.service.application.useCases.CreateServiceUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class CreateServiceController {
    private final CreateServiceUseCase createServiceUseCase;

    public CreateServiceController(CreateServiceUseCase createServiceUseCase) {
        this.createServiceUseCase = createServiceUseCase;
    }

    @PostMapping()
    public ResponseEntity<CreateServiceResponseDTO> create(@RequestBody @Valid CreateServiceRequestDTO createServiceRequest) {
        CreateServiceInput input = new CreateServiceInput(
                createServiceRequest.title(),
                createServiceRequest.description(),
                createServiceRequest.price()
        );

        var service = createServiceUseCase.execute(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateServiceResponseDTO(new ServiceResponseDTO(
                service.id(),
                service.title(),
                service.price()
        ), "Service created successfully"));
    }
}
