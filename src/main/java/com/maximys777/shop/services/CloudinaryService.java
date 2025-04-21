package com.maximys777.shop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map<String, String> uploadFile(MultipartFile file, String folder, String resourceType) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", resourceType
            );
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return Map.of(
                    "secure_url", uploadResult.get("secure_url").toString(),
                    "public_id", uploadResult.get("public_id").toString()
            );
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла.", e);
        }
    }

    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка удаления файла.", e);
        }
    }
}
