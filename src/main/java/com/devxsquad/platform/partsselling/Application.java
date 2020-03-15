package com.devxsquad.platform.partsselling;

import com.devxsquad.platform.partsselling.service.InitCarFilesStructureService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EntityScan(basePackages = {"com.devxsquad.platform.partsselling.model.entity"})
public class Application {

    private final InitCarFilesStructureService initCarFilesStructureService;

    @Bean
    public void initDriveFolderStructure() {
        initCarFilesStructureService.initStructure();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
