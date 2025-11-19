package org.embed.service;

import java.util.List;
import org.embed.dto.ReviewDTO;
import org.embed.dto.ReplyDTO;

public interface ReviewService {
    // 리뷰
    List<ReviewDTO> getAllReviews();
    void createReview(ReviewDTO review);
    void updateReview(ReviewDTO review);
    void deleteReview(Long id);

    // 답변
    List<ReplyDTO> getReplies(Long reviewId);
    void createReply(ReplyDTO reply);
    void deleteReply(Long replyId);
}
