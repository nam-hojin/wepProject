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

    // 루트 접근 시 목록 페이지로 리다이렉트
    @GetMapping
    public String redirectToList() {
        return "redirect:/question.eo/list.eo";
    }

    // 1. 질문 목록
    @GetMapping("/list.eo")
    public String questionList(Model model) throws Exception {
        List<QuestionDTO> list = questionService.getQuestionList();
        model.addAttribute("questionList", list);
        return "question/questionList";
    }

    // 2. 질문 상세
    @GetMapping("/detail.eo/{postId}")
    public String questionDetail(@PathVariable("postId") int postId, Model model) throws Exception {
        QuestionDTO question = questionService.getQuestionDetail(postId);
        model.addAttribute("question", question);
        return "question/questionDetail";
    }

    // 3. 질문 작성 (관리자만)
    @GetMapping("/create.eo")
    public String createForm(HttpSession session, Model model) {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자만 질문을 작성할 수 있습니다.");
            return "redirect:/question.eo/list.eo";
        }
        return "question/questionCreate";
    }

    @PostMapping("/create.eo")
    public String createQuestion(@ModelAttribute QuestionDTO question, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/question.eo/list.eo";
        }
        questionService.createQuestion(question);
        return "redirect:/question.eo/list.eo";
    }

    // 4. 질문 수정 (관리자만)
    @GetMapping("/update.eo/{postId}")
    public String updateForm(@PathVariable("postId") int postId, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/question.eo/list.eo";
        }
        QuestionDTO question = questionService.getQuestionDetail(postId);
        model.addAttribute("question", question);
        return "question/questionUpdate";
    }

    @PostMapping("/update.eo")
    public String updateQuestion(@ModelAttribute QuestionDTO question, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/question.eo/list.eo";
        }
        questionService.updateQuestion(question);
        return "redirect:/question.eo/detail.eo/" + question.getPostId();
    }
    @GetMapping("/main.to") 
    public String mainPage() {
        return "main"; 
    }
}