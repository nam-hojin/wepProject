package org.embed.fileutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.embed.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {

    private final String BASE_PATH = "C:/eclipse/eclipse-workspace/Fspringproject/images/";

    // 단일 이미지 저장
    public String saveFile(ProductDTO product) throws IOException {
        MultipartFile file = product.getImageFile();
        if (file == null || file.isEmpty()) return null;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime now = ZonedDateTime.now();
        String folder = BASE_PATH + now.format(format);

        Path dirPath = Paths.get(folder);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        String contentType = file.getContentType();
        String extension = ".png";
        if (contentType != null) {
            if (contentType.contains("jpeg") || contentType.contains("jpg")) extension = ".jpg";
            else if (contentType.contains("gif")) extension = ".gif";
        }

        String newFileName = System.nanoTime() + extension;
        Path filePath = dirPath.resolve(newFileName);
        byte[] bytes = file.getBytes();
        Files.write(filePath, bytes);

        return "/images/" + now.format(format) + "/" + newFileName;
    }
}
