package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/login")
    public String index() {
        return "1_login";
    }
    @RequestMapping("/registered")
    public String Registered() {
        return "2_1_registered";
    }
    @RequestMapping("/signup")
    public String Signup() {
        return "2_signup";
    }
    @RequestMapping("/user_profile")
    public String UserProfile() {
        return "3_user_profile";
    }
    @RequestMapping("/menu")
    public String Menu(Model model) {
    	abc();
    	String nickname = "山田";
    	model.addAttribute("nickname",nickname);
        return "4_menu";
    }
    @RequestMapping("/othello")
    public String Othello() {
        return "5_othello";
    }
    @Autowired
	UserTableRepository UserTableRepository;
    public void abc(){
		 UserTable person1 = this.UserTableRepository.save(new UserTable("user5", "abcd", "山田","aaa"));
	        System.out.println("DB登録変更結果確認");
	        UserTableRepository.findAll().forEach(p -> System.out.println(p.toString()));
	}
}