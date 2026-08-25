package com.cleverson.help_desk.customer.application.dto;

import java.util.UUID;

public record UpdateCustomerInput(
        UUID id,
        String name,
        String email
) {
}
