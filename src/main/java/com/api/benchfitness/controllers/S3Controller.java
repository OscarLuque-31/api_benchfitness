package com.api.benchfitness.controllers;

import com.api.benchfitness.services.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gifs")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    /**
     * Genera una URL pre-firmada para acceder a un archivo en S3
     * @param fileKey Ruta del archivo en el bucket
     * @return URL pre-firmada con acceso temporal
     */
    @GetMapping("/signed-url")
    public ResponseEntity<String> getSignedUrl(@RequestParam String fileKey) {
        try {
            String signedUrl = s3Service.generateSignedUrl(fileKey);
            return ResponseEntity.ok(signedUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al generar URL: " + e.getMessage());
        }
    }
}