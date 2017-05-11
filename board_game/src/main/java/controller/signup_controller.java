package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class signup_controller {

	@PostMapping("/upload") void upload(@ModelAttribute model.UserInfo userData, Model model) {
		System.out.println(userData.getId());
	}
	@RequestMapping("/hello") // (5)
    public String index(Model model) {// (3)
		model.addAttribute("UserInfo", new model.UserInfo());
        return "2_signup";
    }

}
