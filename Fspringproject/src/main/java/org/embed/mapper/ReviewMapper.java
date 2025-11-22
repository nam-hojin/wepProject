package org.embed.mapper;

import java.util.List;
import org.embed.dto.ReviewDTO;
import org.embed.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

	List<ReviewDTO> selectAllReviews();

	void insertReview(ReviewDTO review);

	void updateReview(ReviewDTO review);

	void deleteReview(Long id);

	List<ReplyDTO> selectRepliesByReviewId(Long reviewId);

	void insertReply(ReplyDTO reply);

	void deleteReply(Long replyId);
}