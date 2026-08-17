package com.cleverson.help_desk.user.domain;

import java.util.UUID;

public record User(UUID id, String name, String email, String password, String avatarUrl, UserRole role) {

}
