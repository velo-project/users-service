package com.github.veloproject.infrastructure.services;

import com.github.veloproject.application.abstractions.services.IImageFileService;
import com.github.veloproject.infrastructure.services.exceptions.InvalidFileTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ImageFileService implements IImageFileService {
    @Override
    public String uploadImage(MultipartFile file, Integer userId) throws IOException {
        var originalFilename = file.getOriginalFilename();

        if (!originalFilename.endsWith(".jpg") || !originalFilename.endsWith(".jpeg") || !originalFilename.endsWith(".png"))
            throw new InvalidFileTypeException("Image must be jpg, jpeg or png.");

        var filename = UUID.randomUUID()
                .toString()
                .replaceAll("-", "");
        var now = LocalDateTime.now();

        var year = String.valueOf(now.getYear());
        var month = now.getMonthValue();
        var day = now.getDayOfMonth();

        String filePath = MessageFormat
                .format("/{0}/{1}/{2}/Users/{3}",
                        year,
                        month,
                        day,
                        filename);

        var finalFile = new File(filePath);
        var parentDir = finalFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        file.transferTo(finalFile);

        return filePath;
    }
}
