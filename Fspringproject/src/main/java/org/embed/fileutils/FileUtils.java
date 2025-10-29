package org.embed.fileutils;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.embed.dto.UsFileDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.sql.DataSource;

@Component
public class FileUtils {

    private final DataSource dataSource;

    FileUtils(DataSource dataSource) {
        this.dataSource = dataSource;
    }

public List<UsFileDTO> parseFileInfo(int boardId, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		List<UsFileDTO> fileList = new ArrayList<UsFileDTO>();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		String newFileName, originalFileExtension, contentType;
		
		while (iterator.hasNext()) {
			
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for (MultipartFile multipartFile : list) {
				if (multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					UsFileDTO usFile = new UsFileDTO();
					usFile.setUserId(0);
					usFile.setFileSize(multipartFile.getSize());
					usFile.setOriginalFileName(multipartFile.getOriginalFilename());
					usFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(usFile);
					
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
					
				}
			}
		}
		
		return fileList;
	}
		
}
