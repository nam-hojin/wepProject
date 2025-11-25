package org.embed.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
	private Long id;
	private int userId;
	private String userName;
	private String userPassword;
	private String content;
	private String createdAt;
	private List<ReplyDTO> replies = new ArrayList<>();
}
