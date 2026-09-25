package com.cleverson.help_desk.user.presentation.me;

import com.cleverson.help_desk.user.application.dto.UpdateUserPasswordInput;
import com.cleverson.help_desk.user.application.dto.UpdateUserProfileInput;
import com.cleverson.help_desk.user.application.useCases.UpdateUserAvatarUseCase;
import com.cleverson.help_desk.user.application.useCases.UpdateUserPasswordUseCase;
import com.cleverson.help_desk.user.application.useCases.UpdateUserProfileUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("users/me")
public class ProfileController {
    private final UpdateUserAvatarUseCase updateUserAvatarUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    public ProfileController(UpdateUserAvatarUseCase updateUserAvatarUseCase, UpdateUserProfileUseCase updateUserProfileUseCase, UpdateUserPasswordUseCase updateUserPasswordUseCase) {
        this.updateUserAvatarUseCase = updateUserAvatarUseCase;
        this.updateUserProfileUseCase = updateUserProfileUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(
            @RequestBody @Valid UpdateProfileRequestDTO request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        updateUserProfileUseCase.execute(userDetails.getUser().id(), new UpdateUserProfileInput(
                request.name(),
                request.email()
        ));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody @Valid UpdateUserPasswordRequestDTO request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        updateUserPasswordUseCase.execute(userDetails.getUser().id(), new UpdateUserPasswordInput(
                request.currentPassword(),
                request.newPassword()
        ));

        return ResponseEntity.noContent().build();
    }
}
