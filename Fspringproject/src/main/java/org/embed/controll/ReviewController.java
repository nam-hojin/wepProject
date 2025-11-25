package org.embed.controll;

import java.time.LocalDateTime;
import java.util.List;

import org.embed.dto.ReplyDTO;
import org.embed.dto.ReviewDTO;
import org.embed.dto.UsDTO;
import org.embed.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("/reviews.go")
	public String getReviews(Model model) {
		List<ReviewDTO> reviews = reviewService.getAllReviews();
		model.addAttribute("reviews", reviews);
		return "review/reviewList";
	}

	@GetMapping("/create.go")
	public String createReviewForm(Model model) {
		model.addAttribute("newReview", new ReviewDTO());
		return "review/reviewCreate";
	}

	@PostMapping("/create1.go")
	public String addReview(@ModelAttribute ReviewDTO newReview, HttpSession session) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		newReview.setUserId(loginUser.getUserId());
		newReview.setUserName(loginUser.getName());
		newReview.setUserPassword(loginUser.getUserPassword()); // 반드시 넣기
		newReview.setCreatedAt(LocalDateTime.now().toString());

		reviewService.createReview(newReview);
		return "redirect:/reviews.go";
	}

	@GetMapping("/delete/{id}.go")
	public String deleteReview(@PathVariable("id") Long id) {
		reviewService.deleteReview(id);
		return "redirect:/reviews.go";
	}

	@GetMapping("/edit1/{id}.go")
	public String editReviewForm(@PathVariable("id") Long id, Model model) {
		List<ReviewDTO> reviews = reviewService.getAllReviews();
		ReviewDTO review = reviews.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
		if (review != null) {
			model.addAttribute("review", review);
			return "review/editReview";
		}
		return "redirect:/reviews.go";
	}

	@PostMapping("/edit2.go")
	public String editReview(@ModelAttribute ReviewDTO editedReview, HttpSession session) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		editedReview.setUserPassword(loginUser.getUserPassword());
		reviewService.updateReview(editedReview);
		return "redirect:/reviews.go";
	}

	@GetMapping("/review/{id}.go")
	public String viewReview(@PathVariable("id") Long id, Model model) {
		List<ReviewDTO> reviews = reviewService.getAllReviews();
		ReviewDTO review = reviews.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
		if (review != null) {
			model.addAttribute("review", review);
			model.addAttribute("newReply", new ReplyDTO());
			return "review/reviewDetail";
		}
		return "redirect:/reviews.go";
	}

	@PostMapping("/reply/create5.go")
	public String addReply(@ModelAttribute ReplyDTO newReply, @RequestParam(name = "reviewId") Long reviewId,
			HttpSession session) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		newReply.setUserId(loginUser.getUserId());
		newReply.setUserName(loginUser.getName());
		newReply.setUserPassword(loginUser.getUserPassword()); // 세션 사용
		newReply.setReviewId(reviewId);
		newReply.setCreatedAt(LocalDateTime.now().toString());

		reviewService.createReply(newReply);
		return "redirect:/review/" + reviewId + ".go";
	}

	@GetMapping("/reply/delete/{reviewId}/{replyId}.go")
	public String deleteReply(@PathVariable("reviewId") Long reviewId, @PathVariable("replyId") Long replyId) {
		reviewService.deleteReply(replyId);
		return "redirect:/review/" + reviewId + ".go";
	}
}
