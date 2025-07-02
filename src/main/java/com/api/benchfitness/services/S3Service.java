package com.api.benchfitness.services;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Servicio para generar URLs pre-firmadas de objetos en Amazon S3.
 * Permite acceso temporal a archivos privados en el bucket sin necesidad de credenciales AWS.
 */
@Service
@ConditionalOnProperty(name = {"aws.access.key.id", "aws.access.secret.key.id", "aws.region", "aws.bucket.name"})
public class S3Service {

    // Configuración inyectada desde application.properties
    private final String bucketName;  // Nombre del bucket S3
    private final String accessKey;   // Clave de acceso AWS (IAM)
    private final String secretKey;   // Clave secreta AWS (IAM)
    private final Region region;      // Región AWS donde está el bucket

    /**
     * Constructor que recibe la configuración mediante inyección de Spring.
     * @param bucketName Nombre del bucket S3 (desde application.properties)
     * @param accessKey Clave de acceso AWS (desde application.properties)
     * @param secretKey Clave secreta AWS (desde application.properties)
     * @param region Región AWS como string (se convierte a objeto Region)
     */
    public S3Service(
            @Value("${aws.bucket.name}") String bucketName,
            @Value("${aws.access.key.id}") String accessKey,
            @Value("${aws.access.secret.key.id}") String secretKey,
            @Value("${aws.region}") String region) {
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = Region.of(region);  // Convierte string a objeto Region
    }

    /**
     * Genera una URL pre-firmada para acceder a un objeto privado en S3.
     * @param key La clave/identificador del objeto en el bucket
     * @return URL pre-firmada con tiempo limitado de acceso (5 minutos)
     */
    public String generateSignedUrl(String key) {
        // 1. Crear credenciales AWS a partir de las claves
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        
        // 2. Configurar el pre-firmador (presigner) con región y credenciales
        S3Presigner presigner = S3Presigner.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        // 3. Crear solicitud para obtener el objeto
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // 4. Configurar la solicitud de URL pre-firmada (válida por 5 minutos)
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .getObjectRequest(getObjectRequest)
                .build();

        // 5. Generar la URL pre-firmada
        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
        
        // 6. Obtener la URL como string
        String url = presignedRequest.url().toString();
        
        // 7. Cerrar el presigner para liberar recursos
        presigner.close();

        return url;
    }
}