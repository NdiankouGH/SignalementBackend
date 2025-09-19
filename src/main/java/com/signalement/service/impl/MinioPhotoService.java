package com.signalement.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MinioPhotoService {
    private final MinioClient minioClient;

    private final String bucketName = "signalement";

    public MinioPhotoService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadPhoto(MultipartFile file,String objectName) throws Exception {
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
        return bucketName + "/" + objectName;
    }

    public InputStream getPhoto(String objectName)throws Exception{
       return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );

    }
    public void deletePhoto(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }
}
