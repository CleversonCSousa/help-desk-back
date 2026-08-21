package com.cleverson.help_desk.customer.presentation.list;

import com.cleverson.help_desk.customer.application.useCases.ListCustomersUseCase;
import com.cleverson.help_desk.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class ListCustomersController {
    private final ListCustomersUseCase listCustomersUseCase;

    public ListCustomersController(ListCustomersUseCase listCustomersUseCase) {
        this.listCustomersUseCase = listCustomersUseCase;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDTO>> list() {
        List<User> users = listCustomersUseCase.execute();

        List<CustomerResponseDTO> response = users.stream()
                .map(user -> new CustomerResponseDTO(
                        user.id(),
                        user.name(),
                        user.email(),
                        user.avatarUrl()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
