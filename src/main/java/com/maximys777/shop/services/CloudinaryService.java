package com.maximys777.shop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

    public String extractPublicIdFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath(); // /.../upload/v1234567890/folder/filename.jpg

            // Получаем путь после "upload/"
            String[] parts = path.split("/upload/");
            if (parts.length < 2) return null;

            String publicPath = parts[1]; // v1234567890/folder/filename.jpg

            // Убираем версию (v123456...)
            String[] publicParts = publicPath.split("/", 2);
            if (publicParts.length < 2) return null;

            String publicIdWithExtension = publicParts[1]; // folder/filename.jpg

            // Убираем расширение
            int dotIndex = publicIdWithExtension.lastIndexOf('.');
            if (dotIndex != -1) {
                return publicIdWithExtension.substring(0, dotIndex); // folder/filename
            }

            return publicIdWithExtension;

        } catch (URISyntaxException e) {
            throw new RuntimeException("Невозможно извлечь public_id из URL", e);
        }
    }

}
