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

import jp.com.xpower.app2017.model.RoomCreate;
import jp.com.xpower.app2017.model.UserInfo;
import jp.com.xpower.app2017.model.UserTable;
import jp.com.xpower.app2017.model.UserTableRepository;

@Controller
public class LoginController {
	@Autowired
	UserTableRepository userTableRepository;
	@Autowired
	RoomCreate roomTable;
	//ログアウトが押された時の処理
    @RequestMapping("/logout")//5月19日 メンバー間で命名規則再確認、変更  5月26日 仮ログインのために変更
    //5月23日 MenuControllerから移動
    public String logout(HttpSession session,Model model) {
    	session.invalidate();//セッション破棄
    	model.addAttribute("UserInfo", new UserInfo());
    	return "1_login";
    }


    @RequestMapping("/login")//5月26日 仮ログインのために変更
    public String login(Model model) {
    	model.addAttribute("UserInfo", new UserInfo());
        return "1_login";
    }

	@PostMapping("/menu")//5月26日 仮ログインのために変更
    public String requestMenu(HttpSession session,Model model,@ModelAttribute UserInfo userInfo){
    	String formUserId = userInfo.getId();
    	List<UserTable> matchedUsers = userTableRepository.findByUserId(formUserId);
    	if(matchedUsers.isEmpty()){
    		model.addAttribute("errorMessage","入力したユーザーは存在しません");
    		return "/error";
    	}
    	UserTable matchedUser = matchedUsers.get(0);
        String encoded = Base64.getEncoder() .encodeToString(matchedUser.getProfileImage());
        String formPassword = userInfo.getPassword();
    	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();// Bcryptによるハッシュ化インスタンス生成
    	//パスワードの比較
		if(!bcrypt.matches(formPassword, matchedUser.getPassword())){
			model.addAttribute("errorMessage","パスワードが間違っています");
    		return "/error";
		}
        session.setAttribute("userId", formUserId);
        session.setAttribute("nickname", matchedUser.getNickname());
      //プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
        session.setAttribute("image", encoded);

    	return "4_menu";
    }
}
