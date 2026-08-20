package com.cleverson.help_desk.service.presentation.list;

import com.cleverson.help_desk.service.application.dto.ListServicesInput;
import com.cleverson.help_desk.service.application.useCases.ListServicesUseCase;
import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ListServicesController {
    private final ListServicesUseCase listServicesUseCase;

    public ListServicesController(ListServicesUseCase listServicesUseCase) {
        this.listServicesUseCase = listServicesUseCase;
    }

    @GetMapping()
    public ResponseEntity<List<Service>> list(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(required = false) Boolean isActive) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // if not an admin, force the listing of only active services.
        if(!isAdmin) {
            isActive = true;
        }

        ListServicesInput input = new ListServicesInput(
                isActive
        );

        List<Service> services = this.listServicesUseCase.execute(input);
        return ResponseEntity.ok(services);
    }

}
