package com.cleverson.help_desk.service.presentation.create;

public record CreateServiceResponseDTO (
    ServiceResponseDTO service,
    String message
) {
}
