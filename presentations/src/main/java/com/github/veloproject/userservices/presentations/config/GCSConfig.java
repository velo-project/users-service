package com.github.veloproject.userservices.presentations.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GCSConfig {
    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.credentials.file}")
    private String credentialsPath;

    @Value("${gcp.bucket}")
    private String bucket;

    @Bean
    public Storage storage() throws IOException {
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialsPath)))
                .build()
                .getService();
    }
}
