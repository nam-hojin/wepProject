package org.embed.service;

import java.util.List;

import org.embed.dto.ReviewDTO;
import org.embed.dto.ReplyDTO;
import org.embed.mapper.ReviewMapper;
import org.embed.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    // 리뷰 관련
    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewMapper.selectAllReviews();
    }

    @Override
    @Transactional
    public void createReview(ReviewDTO review) {
        reviewMapper.insertReview(review);
    }

    @Override
    @Transactional
    public void updateReview(ReviewDTO review) {
        reviewMapper.updateReview(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        reviewMapper.deleteReview(id);
    }

    // 답변 관련
    @Override
    public List<ReplyDTO> getReplies(Long reviewId) {
        return reviewMapper.selectRepliesByReviewId(reviewId);
    }

    @Override
    @Transactional
    public void createReply(ReplyDTO reply) {
        reviewMapper.insertReply(reply);
    }

    @Override
    @Transactional
    public void deleteReply(Long replyId) {
        reviewMapper.deleteReply(replyId);
    }
}