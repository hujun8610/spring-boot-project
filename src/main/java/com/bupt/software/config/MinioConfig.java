package com.bupt.software.config;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hujun on 2024/1/20.
 */
@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.access-secret}")
    private String minioSecretKey;

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

    @Bean
    public MinioClient minioClient(OkHttpClient okHttpClient){
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioAccessKey, minioSecretKey)
                .httpClient(okHttpClient)
                .build();

    }

}
