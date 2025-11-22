package org.embed.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

	private int postId;
	private String title;
	private String content;
	private String writer;
	private int viewCount;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
