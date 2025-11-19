package org.embed.dto;

import lombok.Data;

@Data
public class UsFileDTO {

	
	private int id;
	private int userId;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;

	
}
