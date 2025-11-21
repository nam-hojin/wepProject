package org.embed.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long id;                 // 리뷰 고유번호
    private int userId;              // 작성자 ID
    private String userName;         // 작성자 이름
    private String userPassword;     // 작성자 비밀번호 (수정/삭제 확인용)
    private String content;          // 리뷰 내용
    private String createdAt;        // 작성일
    private List<ReplyDTO> replies = new ArrayList<>(); // 답변 리스트
}

