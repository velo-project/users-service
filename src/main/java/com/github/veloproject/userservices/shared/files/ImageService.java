package com.github.veloproject.userservices.shared.files;

import com.github.veloproject.userservices.shared.exceptions.InvalidFileType;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
public class ImageService {
    @Value("${app.image.upload-dir}")
    private String uploadDir;

    // TODO Criar método utilizando o UserUpdatableFields.
    public String uploadImage(MultipartFile file,
                              String fileFinalName,
                              Integer userId)
            throws IOException, InvalidFileType, InvalidParameterException {
        if (fileFinalName == null || fileFinalName.isEmpty()) {
            throw new InvalidParameterException("Final name must be specified.");
        }
        else if (userId == null) {
            throw new InvalidParameterException("User id must be specified.");
        }

        var fileName = file
                .getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new InvalidParameterException("File name must be not null.");
        }
        else if (!fileName.endsWith(".jpg")
                && !fileName.endsWith(".jpeg")
                && !fileName.endsWith(".png")) {
            throw new InvalidFileType("Image must be jpg, jpeg or png.");
        }

        var now = LocalDateTime.now();

        var year = String.valueOf(now.getYear());
        var month = now.getMonthValue();
        var day = now.getDayOfMonth();
        var userIdToString = String.valueOf(userId);

        String filePath = MessageFormat
                .format(
                        "/{0}/{1}/{2}/users/{3}/{3}_{4}",
                        year,
                        month,
                        day,
                        userIdToString,
                        fileFinalName
                );
        String finalPath = Paths
                .get(uploadDir, filePath)
                .toString();

        File finalFile = new File(finalPath);
        File parentDir = finalFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        file.transferTo(finalFile);

        return finalPath;
    }
}
