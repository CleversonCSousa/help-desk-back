package com.cleverson.help_desk.user.application.dto;

public record UpdateUserPasswordInput(
        String currentPassword,
        String newPassword
) {}