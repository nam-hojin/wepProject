package org.embed.controll;

import java.util.List;

import org.embed.dto.NoticeDTO;
import org.embed.dto.UsDTO;
import org.embed.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/notice.do")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /** 루트 접근 시 목록 페이지로 리다이렉트 */
    @GetMapping
    public String redirectToList() {
        return "redirect:/notice.do/list.do";
    }

    /** 1. 공지 목록 페이지 */
    @GetMapping("/list.do")
    public String noticeList(Model model) throws Exception {
        List<NoticeDTO> list = noticeService.getNoticeList();
        model.addAttribute("noticeList", list);
        return "notice/noticeList";
    }

    /** 2. 공지 상세 페이지 */
    @GetMapping("/detail.do/{postId}")
    public String noticeDetail(@PathVariable("postId") int postId, Model model) throws Exception {
        NoticeDTO notice = noticeService.getNoticeDetail(postId);
        model.addAttribute("notice", notice);
        return "notice/noticeDetail";
    }

    /** 3. 공지 작성 페이지 (관리자만 접근 가능) */
    @GetMapping("/create.do")
    public String createForm(HttpSession session, Model model) {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            model.addAttribute("errorMessage", "로그인 후 이용 가능합니다.");
            return "redirect:/login.to";
        }

        if (!"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자만 공지사항을 작성할 수 있습니다.");
            return "redirect:/notice.do/list.do";
        }

        return "notice/noticeCreate";
    }

    /** 3-2. 공지 등록 처리 (관리자만 가능) */
    @PostMapping("/create.do")
    public String createNotice(@ModelAttribute NoticeDTO notice, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");

        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/notice.do/list.do";
        }

        // 로그인한 관리자 이름을 작성자로 설정
        notice.setWriter(loginUser.getName());

        noticeService.createNotice(notice);
        return "redirect:/notice.do/list.do";
    }

    /** 4. 공지 수정 페이지 (관리자만 접근 가능) */
    @GetMapping("/update.do/{postId}")
    public String updateForm(@PathVariable("postId") int postId, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");

        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/notice.do/list.do";
        }

        NoticeDTO notice = noticeService.getNoticeDetail(postId);
        model.addAttribute("notice", notice);
        return "notice/noticeUpdate";
    }

    /** 4-2. 공지 수정 처리 (관리자만 가능) */
    @PostMapping("/update.do")
    public String updateNotice(@ModelAttribute NoticeDTO notice, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");

        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/notice.do/list.do";
        }

        // 작성자 이름 업데이트 (필요 없으면 생략 가능)
        notice.setWriter(loginUser.getName());

        noticeService.updateNotice(notice);
        return "redirect:/notice.do/detail.do/" + notice.getPostId();
    }

    /** 5. 공지 삭제 (관리자만 가능) */
    @GetMapping("/delete.do/{postId}")
    public String deleteNotice(@PathVariable("postId") int postId, HttpSession session, Model model) throws Exception {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");

        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            model.addAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/notice.do/list.do";
        }

        noticeService.deleteNotice(postId);
        return "redirect:/notice.do/list.do";
    }
    @GetMapping("/main.to") 
    public String mainPage() {
        return "main"; // main.html 뷰
    }
}