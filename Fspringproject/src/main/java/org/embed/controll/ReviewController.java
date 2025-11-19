package org.embed.controll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.embed.dto.ReplyDTO;
import org.embed.dto.ReviewDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReviewController {

    private List<ReviewDTO> reviewList = new ArrayList<>();
    private Long replyIdSeq = 1L;

    @GetMapping("/reviews.go")
    public String getReviews(Model model) {
        model.addAttribute("reviews", reviewList);
        return "review/reviewList";
    }

    @GetMapping("/create.go")
    public String createReviewForm(Model model) {
        model.addAttribute("newReview", new ReviewDTO());
        return "review/reviewCreate";
    }

    @PostMapping("/create1.go")
    public String addReview(@ModelAttribute ReviewDTO newReview) {
        newReview.setId((long) (reviewList.size() + 1));
        newReview.setCreatedAt(LocalDateTime.now().toString());
        reviewList.add(newReview);
        return "redirect:/reviews.go";
    }

    @GetMapping("/delete/{id}.go")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewList.removeIf(r -> r.getId().equals(id));
        return "redirect:/reviews.go";
    }

    @GetMapping("/edit1/{id}.go")
    public String editReviewForm(@PathVariable("id") Long id, Model model) {
        ReviewDTO review = reviewList.stream()
            .filter(r -> r.getId().equals(id))
            .findFirst()
            .orElse(null);
        if (review != null) {
            model.addAttribute("review", review);
            return "review/editReview";
        }
        return "redirect:/reviews.go";
    }

    @PostMapping("/edit2.go")
    public String editReview(@ModelAttribute ReviewDTO editedReview) {
        for (ReviewDTO r : reviewList) {
            if (r.getId().equals(editedReview.getId())) {
                r.setUserName(editedReview.getUserName());
                r.setUserId(editedReview.getUserId());
                r.setContent(editedReview.getContent());
                break;
            }
        }
        return "redirect:/reviews.go";
    }

    // 답변 작성 페이지로 이동
    @GetMapping("/reply/create/{reviewId}.go")
    public String replyCreateForm(@PathVariable("reviewId") Long reviewId, Model model) {
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("newReply", new ReplyDTO());
        return "review/replyCreate";
    }

    // 답변 등록 처리
    @PostMapping("/reply/create5.go")
    public String addReply(
            @ModelAttribute ReplyDTO newReply,
            @RequestParam("reviewId") Long reviewId) {

        ReviewDTO review = reviewList.stream()
            .filter(r -> r.getId().equals(reviewId))
            .findFirst()
            .orElse(null);

        if (review != null) {
            newReply.setId(replyIdSeq++);
            newReply.setReviewId(reviewId);
            newReply.setCreatedAt(LocalDateTime.now().toString());
            review.getReplies().add(newReply);
        }

        return "redirect:/reviews.go";
    }

    // 답변 삭제
    @GetMapping("/reply/delete/{reviewId}/{replyId}.go")
    public String deleteReply(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("replyId") Long replyId) {

        ReviewDTO review = reviewList.stream()
            .filter(r -> r.getId().equals(reviewId))
            .findFirst()
            .orElse(null);
        if (review != null) {
            review.getReplies().removeIf(r -> r.getId().equals(replyId));
        }
        return "redirect:/reviews.go";
    }
}
