package org.embed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsFileDTO {

	private int id;
	private int userId;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;

}
