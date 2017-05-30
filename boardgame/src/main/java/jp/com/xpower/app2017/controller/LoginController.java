package jp.com.xpower.app2017.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.BoardGameConstant.Error;
import jp.com.xpower.app2017.model.UserInfo;
import jp.com.xpower.app2017.model.UserTable;
import jp.com.xpower.app2017.model.UserTableRepository;

@Controller
public class LoginController {
	@Autowired
	UserTableRepository userTableRepository;
	//ログアウトが押された時の処理
    //5月23日 MenuControllerから移動
	@RequestMapping("/logout")
    public String logout(HttpSession session,Model model) {
    	session.invalidate();//セッション破棄
    	model.addAttribute("UserInfo", new UserInfo());
    	return "1_login";
    }

    @RequestMapping("/")//5月26日 仮ログインのために変更
    public String top(Model model) {
    	model.addAttribute("UserInfo", new UserInfo());
        return "1_login";
    }
    @RequestMapping("/login")//5月26日 仮ログインのために変更
    public String login(Model model) {
    	model.addAttribute("UserInfo", new UserInfo());
        return "1_login";
    }

    @RequestMapping("/menu")//5月26日 仮ログインのために変更
    public String menu(Model model) {
        return "4_menu";
    }

	@PostMapping("/loginMenu")//5月26日 仮ログインのために変更
    public String loginMenu(HttpSession session,Model model,@ModelAttribute UserInfo userInfo){
		try{
	    	String formUserId = userInfo.getId();
	    	List<UserTable> matchedUsers = userTableRepository.findByUserId(formUserId);
	    	if(matchedUsers.isEmpty()){
	    		model.addAttribute("errorMessage","ID、もしくはパスワードが違います");
	    		return "/error";
	    	}
	    	UserTable matchedUser = matchedUsers.get(0);
	        String encoded = Base64.getEncoder() .encodeToString(matchedUser.getProfileImage());
	        String formPassword = userInfo.getPassword();
	    	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();// Bcryptによるハッシュ化インスタンス生成
	    	//パスワードの比較
			if(!bcrypt.matches(formPassword, matchedUser.getPassword())){
				model.addAttribute("errorMessage","ID、もしくはパスワードが違います");
	    		return "/error";
			}
	        session.setAttribute("userId", formUserId);
	        session.setAttribute("nickname", matchedUser.getNickname());
	      //プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
	        session.setAttribute("image", encoded);
		}catch(Exception e){
			model.addAttribute("errorMessage",Error.DATABASEERROR);
    		return "/error";
		}
    	return "4_menu";
    }
}
