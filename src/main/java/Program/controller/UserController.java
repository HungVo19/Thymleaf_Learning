package Program.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Program.entity.User;
import Program.service.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@GetMapping()
	public String findAll(Pageable pageable, Model model) {
		List<User> users = userService.findAll(Pageable.unpaged()).toList();
		model.addAttribute("users", users);
		return "manage_users";
	}
}
