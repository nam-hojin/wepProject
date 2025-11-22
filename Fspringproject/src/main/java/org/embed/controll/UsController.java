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

	@GetMapping("/main.to")
	public String main() {
		return "main";
	}

	@GetMapping("/addUI.to")
	public String addUI() {
		return "addUs";
	}

	@PostMapping("/insertUs.to")
	public String insertUs(@ModelAttribute UsDTO user) throws Exception {
		user.setRole(user.getRole() != null ? user.getRole() : "ROLE_USER");
		usService.registerUser(user);
		return "redirect:/login.to";
	}

	@GetMapping("/login.to")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/login.to")
	public String login(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,
			HttpSession session, Model model) {

		try {

			UsDTO user = usService.findByUserName(userName); // 아이디로 사용자 조회

			String errorMessage = (user == null) ? "존재하지 않는 아이디입니다."
					: (!user.getUserPassword().equals(userPassword) ? "비밀번호가 올바르지 않습니다." : null);

			if (errorMessage != null) {
				model.addAttribute("errorMessage", errorMessage);
				return "login";
			}

			log.info("로그인 사용자: {}, 권한: {}", user.getName(), user.getRole());
			session.setAttribute("loginUser", user);

			return "redirect:/main.to";

		} catch (Exception e) {
			e.printStackTrace();
			return "login";
		}
	}

	@GetMapping("/logout.to")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main.to";
	}

	@GetMapping("/update.to")
	public String showUpdatePage(HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		model.addAttribute("user", loginUser);
		return "update";
	}

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

	@GetMapping("/delete.to")
	public String showDeletePage(HttpSession session, Model model) {
		UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login.to";

		model.addAttribute("user", loginUser);
		return "delete";
	}

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

		if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
			return "redirect:/main.to";
		}

		model.addAttribute("users", usService.getAllUsers());
		return "userList";
	}

}
