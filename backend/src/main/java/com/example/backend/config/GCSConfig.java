package com.example.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class GCSConfig {
    @Value("${gcs.project.id}")
    private String projectId;

    @Bean
    public Storage storage() throws IOException {
        ClassPathResource resource = new ClassPathResource("project-moa-384615-1d5a025687fe.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
