package org.embed.controll;

import org.embed.dto.UsDTO;
import org.embed.service.UsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UsController {

	private final UsService usService;

	@Autowired
	public UsController(UsService usService) {
		this.usService = usService;
	}

	// 메인 페이지
	@GetMapping("/main.to")
	public String main() {
		return "main";
	}

	// 회원가입 페이지
	@GetMapping("/addUI.to")
	public String addUI() {
		return "addUs";
	}

	// 회원가입 처리 (role 기본값 "ROLE_USER" 적용, 삼항 연산 사용)
	@PostMapping("/insertUs.to")
	public String insertUs(@ModelAttribute UsDTO user) throws Exception {
		user.setRole(user.getRole() != null ? user.getRole() : "ROLE_USER");
		usService.registerUser(user);
		return "redirect:/login.to";
	}

	// 로그인 페이지
	@GetMapping("/login.to")
	public String loginPage() {
		return "login";
	}

	// 로그인 처리 (세션 방식)
	@PostMapping("/login.to")
	public String login(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,
			HttpSession session, Model model) {

		try {
			// 아이디 존재 여부 먼저 체크
			UsDTO user = usService.findByUserName(userName); // 아이디로 사용자 조회

			// 삼항 연산자로 아이디 및 비밀번호 체크
			String errorMessage = (user == null) ? "존재하지 않는 아이디입니다."
					: (!user.getUserPassword().equals(userPassword) ? "비밀번호가 올바르지 않습니다." : null);

			// 에러 발생 시 다시 로그인 페이지
			if (errorMessage != null) {
				model.addAttribute("errorMessage", errorMessage);
				return "login";
			}

			// 로그인 성공
			log.info("로그인 사용자: {}, 권한: {}", user.getName(), user.getRole());
			session.setAttribute("loginUser", user);

			return "redirect:/main.to";

		} catch (Exception e) {
			e.printStackTrace();
			return "login";
		}
	}

	// 로그아웃
	@GetMapping("/logout.to")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main.to";
	}

	// 사용자 정보 수정 페이지
	@GetMapping("/update.to")
	public String showUpdatePage(HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		model.addAttribute("user", loginUser);
		return "update";
	}

	// 사용자 정보 수정 처리
	@PostMapping("/update.to")
	public String updateUser(@ModelAttribute UsDTO us, HttpSession session) throws Exception {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		us.setUserId(loginUser.getUserId());
		us.setRole(us.getRole() != null ? us.getRole() : loginUser.getRole()); // 삼항 연산

		usService.updateUs(us);
		session.setAttribute("loginUser", us);

		return "redirect:/main.to";
	}

	// 사용자 삭제 페이지
	@GetMapping("/delete.to")
	public String showDeletePage(HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		model.addAttribute("user", loginUser);
		return "delete";
	}

	// 사용자 삭제 처리
	@PostMapping("/delete.to")
	public String deleteUser(@RequestParam("userId") int userId, HttpSession session, Model model) {
		try {
			int result = usService.deleteUs(userId);
			if (result > 0) {
				session.invalidate();
				return "deleteResult";
			} else {
				model.addAttribute("errorMessage", "해당 회원을 찾을 수 없습니다.");
				return "delete";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "회원탈퇴 중 오류가 발생했습니다.");
			return "delete";
		}
	}
	@GetMapping("/admin/users.to")
	public String adminUserList(HttpSession session, Model model) throws Exception {
	    UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");

	    // 관리자 아니면 메인으로
	    if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
	        return "redirect:/main.to";
	    }

	    // 모든 사용자 조회
	    model.addAttribute("users", usService.getAllUsers());
	    return "userList";  // templates 바로 아래 userList.html 참조
	}
	

}
