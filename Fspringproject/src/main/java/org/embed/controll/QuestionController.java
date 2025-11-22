package org.embed.controll;

import java.util.List;

import org.embed.dto.QuestionDTO;
import org.embed.dto.UsDTO;
import org.embed.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/question.eo")
public class QuestionController {

	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping
	public String redirectToList() {
		return "redirect:/question.eo/list.eo";
	}

	@GetMapping("/list.eo")
	public String questionList(Model model) throws Exception {
		List<QuestionDTO> list = questionService.getQuestionList();
		model.addAttribute("questionList", list);
		return "question/questionList";
	}

	@GetMapping("/detail.eo/{postId}")
	public String questionDetail(@PathVariable("postId") int postId, Model model) throws Exception {
		QuestionDTO question = questionService.getQuestionDetail(postId);
		model.addAttribute("question", question);
		return "question/questionDetail";
	}

	@GetMapping("/create.eo")
	public String createForm(HttpSession session) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login.to";
		}
		return "question/questionCreate";
	}

	@PostMapping("/create.eo")
	public String createQuestion(@ModelAttribute QuestionDTO question, HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login.to";
		}

		// 작성자 자동 설정
		question.setWriter(loginUser.getName());

		try {
			questionService.createQuestion(question, loginUser);
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/question.eo/list.eo";
		}
		return "redirect:/question.eo/list.eo";
	}

	@GetMapping("/update.eo/{postId}")
	public String updateForm(@PathVariable("postId") int postId, HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login.to";
		}

		QuestionDTO question = questionService.getQuestionDetail(postId);

		// 작성자가 로그인 사용자와 다르면 수정 불가
		if (!loginUser.getName().equals(question.getWriter()) && !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
			model.addAttribute("errorMessage", "수정 권한이 없습니다.");
			return "redirect:/question.eo/list.eo";
		}

		model.addAttribute("question", question);
		return "question/questionUpdate";
	}

	@PostMapping("/update.eo")
	public String updateQuestion(@ModelAttribute QuestionDTO question, HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login.to";
		}

		// 작성자 자동 설정
		question.setWriter(loginUser.getName());

		try {
			questionService.updateQuestion(question, loginUser);
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/question.eo/list.eo";
		}

		return "redirect:/question.eo/detail.eo/" + question.getPostId();
	}

	@GetMapping("/delete.eo/{postId}")
	public String deleteQuestion(@PathVariable("postId") int postId, HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login.to";
		}

		try {
			QuestionDTO question = questionService.getQuestionDetail(postId);

			// 작성자 본인 또는 관리자만 삭제 가능
			if (!loginUser.getName().equals(question.getWriter()) && !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
				model.addAttribute("errorMessage", "삭제 권한이 없습니다.");
				return "redirect:/question.eo/list.eo";
			}

			questionService.deleteQuestion(postId, loginUser);
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/question.eo/list.eo";
	}

	@GetMapping("/main.to")
	public String mainPage() {
		return "main";
	}
}
