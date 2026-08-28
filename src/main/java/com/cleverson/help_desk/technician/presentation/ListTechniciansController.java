package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.application.useCases.ListTechniciansUseCase;
import com.cleverson.help_desk.technician.application.dto.TechnicianSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/technicians")
public class ListTechniciansController {
    private final ListTechniciansUseCase listTechniciansUseCase;

    public ListTechniciansController(ListTechniciansUseCase listTechniciansUseCase) {
        this.listTechniciansUseCase = listTechniciansUseCase;
    }

    @GetMapping()
    public ResponseEntity<List<TechnicianSummaryResponse>> list() {
        List<TechnicianSummaryResponse> response = this.listTechniciansUseCase.execute();
        return ResponseEntity.ok(response);
    }

}
