package com.cleverson.help_desk.user.application.useCases;

import com.cleverson.help_desk.infrastructure.storage.MinioStorageService;
import com.cleverson.help_desk.user.application.exceptions.UnsupportedFileTypeException;
import com.cleverson.help_desk.user.application.exceptions.UserNotFoundException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UpdateUserAvatarUseCase {

    private final MinioStorageService minioStorageService;
    private final UserRepository userRepository;

    public UpdateUserAvatarUseCase(MinioStorageService minioStorageService, UserRepository userRepository) {
        this.minioStorageService = minioStorageService;
        this.userRepository = userRepository;
    }

    public String execute(UUID userId, MultipartFile file) throws Exception {
        var user = this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (!isValidImage(file)) {
            throw new UnsupportedFileTypeException();
        }

        String fileName = userId.toString();

        this.minioStorageService.uploadAvatar(file, fileName);

        this.userRepository.save(new User(
                user.id(), user.name(), user.email(), user.password(),
                fileName, user.role()
        ));

        return fileName;
    }

    private boolean isValidImage(MultipartFile file) {
        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(file.getInputStream());

            return mimeType.equals(MediaType.IMAGE_JPEG_VALUE) || mimeType.equals(MediaType.IMAGE_PNG_VALUE);

        } catch (IOException e) {
            return false;
        }
    }
}
