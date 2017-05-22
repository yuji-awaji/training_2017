package jp.com.xpower.app2017.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.RoomCreate;



@Controller
public class MenuController {
	@Autowired
	RoomCreate roomTable;

	//ログアウトが押された時の処理
    @RequestMapping("/logout")//5月19日　メンバー間で命名規則再確認、変更
    public String requestLogout(HttpSession session) {
    	session.invalidate();//セッション破棄
        return "1_login";
    }

    @RequestMapping("/userProfile")//5月19日　メンバー間で命名規則再確認、変更
    public String requestUserProfile() {
        return "3_user_profile";
    }

    @RequestMapping("/othello")
    public String requestOthello(HttpSession session,Model model) {
//    	int i;
//    	Integer ref_i;
    	boolean isError=roomTable.createRoom(session,model);
    	if(isError==true){
    		return "/error";
    	}
        return "5_othello";
    }

}