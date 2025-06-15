package com.github.veloproject.userservices.shared.files;

import com.github.veloproject.userservices.shared.exceptions.InvalidFileType;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ImageService {
    @Value("${app.image.upload-dir}")
    private String uploadDir;

    public String uploadImage(MultipartFile file,
                              String fileFinalName,
                              Integer userId)
            throws IOException, InvalidFileType, InvalidParameterException {
        if (fileFinalName == null || fileFinalName.isEmpty()) {
            throw new  InvalidParameterException("File final name must be specified.");
        }

        var fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new InvalidParameterException("File name must be not null.");
        }
        else if (!fileName.endsWith(".jpg")
                && !fileName.endsWith(".jpeg")
                && !fileName.endsWith(".png")) {
            throw new InvalidFileType("Image must be jpg, jpeg or png.");
        }

        var now = LocalDateTime.now();
        String filePath = "/"
                + now.getYear() + "/"
                + now.getMonthValue() + "/"
                + now.getDayOfMonth()
                + "/users"
                + "/" + userId
                + "/" + userId + "_" + fileFinalName;

        String finalPath = Paths.get(uploadDir, filePath).toString();
        File finalFile = new File(finalPath);

        File parentDir = finalFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        file.transferTo(finalFile);

        return finalPath;
    }
}
