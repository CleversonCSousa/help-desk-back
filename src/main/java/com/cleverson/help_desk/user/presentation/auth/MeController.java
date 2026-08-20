package com.cleverson.help_desk.user.presentation.auth;

import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

    @GetMapping
    public ResponseEntity<MeResponseDTO> getMe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        MeResponseDTO response = new MeResponseDTO(
                user.name(),
                user.email(),
                user.role().name(),
                user.avatarUrl()
        );
        return ResponseEntity.ok(response);
    }
}
