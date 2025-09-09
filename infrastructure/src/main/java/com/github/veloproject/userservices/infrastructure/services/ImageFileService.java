package com.github.veloproject.userservices.infrastructure.services;

import com.github.veloproject.userservices.application.abstractions.services.IImageFileService;
import com.github.veloproject.userservices.infrastructure.services.exceptions.InvalidFileTypeException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ImageFileService implements IImageFileService {

    private final Storage storage;

    @Value("${gcp.bucket}")
    private String bucketName;

    public ImageFileService(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String uploadImage(MultipartFile file, Integer userId) throws IOException {
        var originalFilename = file.getOriginalFilename();
        if (originalFilename == null ||
                !(originalFilename.endsWith(".jpg") ||
                        originalFilename.endsWith(".jpeg") ||
                        originalFilename.endsWith(".png"))) {
            throw new InvalidFileTypeException("Image must be jpg, jpeg or png.");
        }

        var filename = UUID.randomUUID()
                .toString()
                .replaceAll("-", "");
        var now = LocalDateTime.now();

        var year = String.valueOf(now.getYear());
        var month = now.getMonthValue();
        var day = now.getDayOfMonth();

        String objectPath = MessageFormat.format("images/{0}/{1}/{2}/users/{3}{4}",
                year,
                month,
                day,
                filename,
                originalFilename.substring(originalFilename.lastIndexOf(".")));

        BlobId blobId = BlobId.of(bucketName, objectPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        return objectPath;
    }
}
