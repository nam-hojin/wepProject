package org.embed.fileutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.embed.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtils {

    public List<ProductDTO> parseFileInfo(ProductDTO productDTO, MultipartHttpServletRequest request) throws IOException {
        if (ObjectUtils.isEmpty(request)) {
            return Collections.emptyList();
        }

        List<ProductDTO> productList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();

        // static/images/날짜 폴더 기준으로 생성 (웹에서 접근 가능)
        String folder = "C:/eclipse/eclipse-workspace/Fspringproject/images/" + current.format(format);
        Path dirPath = Paths.get(folder);
        log.info("======================================================> "+ dirPath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            
        }

        Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()) {
            List<MultipartFile> files = request.getFiles(iterator.next());
            for (MultipartFile multipartFile : files) {
                if (!multipartFile.isEmpty()) {
                    String contentType = multipartFile.getContentType();
                    String extension;

                    if (contentType.contains("jpeg") || contentType.contains("jpg")) {
                        extension = ".jpg";
                    } else if (contentType.contains("png")) {
                        extension = ".png";
                    } else if (contentType.contains("gif")) {
                        extension = ".gif";
                    } else {
                        continue;
                    }

                    // 저장할 파일 이름
                    String newFileName = System.nanoTime() + extension;
                    Path filePath = dirPath.resolve(newFileName);

                    // 파일 저장
                    byte[] fileBytes = multipartFile.getBytes();
                    Files.write(filePath, fileBytes);

                    // DTO에 실제 데이터 및 Base64 저장
                    ProductDTO product = new ProductDTO();
                    product.setImageData(fileBytes);
                    product.setImageType(contentType);
                    product.setOriginalFileName(multipartFile.getOriginalFilename());
                    product.setFileSize(multipartFile.getSize());
                    product.setImageUrl("/images/" + current.format(format) + "/" + newFileName);

                    // Base64 문자열도 바로 만들어서 ProductFileDTO로 넣을 수 있음
                    String base64Image = "data:" + contentType + ";base64," +
                            java.util.Base64.getEncoder().encodeToString(fileBytes);
                    ProductDTO.ProductFileDTO fileDTO = new ProductDTO.ProductFileDTO(fileBytes, contentType, base64Image);
                    product.getFiles().add(fileDTO);

                    productList.add(product);
                }
            }
        }

        return productList;
    }
}