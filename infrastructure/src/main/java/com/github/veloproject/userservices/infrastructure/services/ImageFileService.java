package com.github.veloproject.userservices.infrastructure.services;

import com.github.veloproject.userservices.application.abstractions.services.IImageFileService;
import com.github.veloproject.userservices.infrastructure.services.exceptions.InvalidFileTypeException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        /* Ajustar conforme necessário. */
        float quality = 0.85f;

        ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgParams = jpgWriter.getDefaultWriteParam();
        jpgParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgParams.setCompressionQuality(quality);

        jpgWriter.setOutput(new MemoryCacheImageOutputStream(compressedOutput));
        jpgWriter.write(null, new IIOImage(bufferedImage, null, null), jpgParams);
        jpgWriter.dispose();

        byte[] compressedByteArray = compressedOutput.toByteArray();

        var filename = UUID.randomUUID()
                .toString()
                .replaceAll("-", "");
        var now = LocalDateTime.now();

        var year = String.valueOf(now.getYear());
        var month = now.getMonthValue();
        var day = now.getDayOfMonth();

        String objectPath = MessageFormat.format("images/{0}/{1}/{2}/users/{3}.jpg",
                year,
                month,
                day,
                filename);
        BlobId blobId = BlobId.of(bucketName, objectPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("image/jpeg")
                .build();

        storage.create(blobInfo, compressedByteArray);

        return "https://storage.googleapis.com/" + bucketName + "/" + objectPath;
    }
}
