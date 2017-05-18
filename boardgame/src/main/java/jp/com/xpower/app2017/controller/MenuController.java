package jp.com.xpower.app2017.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.ErrorMessage;
import jp.com.xpower.app2017.model.RoomCreate;



@Controller
public class MenuController {
	@Autowired
	RoomCreate roomTable;
	@Autowired
	ErrorMessage errorMessage;

	//ログアウトが押された時の処理
    @RequestMapping("logout")
    public String logout(HttpSession session) {
    	session.invalidate();//セッション破棄
        return "1_login";
    }

    @RequestMapping("user_profile")
    public String userProfile(HttpSession session) {
    	session.invalidate();//セッション破棄
        return "3_user_profile";
    }

    @RequestMapping("/othello")
    public String othello(HttpSession session,Model model) {
//    	int i;
//    	Integer ref_i;

    	//セッションスコープからルームIDを取得
//    	Object sessionRoomId = session.getAttribute("roomId");
    	Integer sessionRoomId = (Integer) session.getAttribute("roomId");

    	//セッションスコープからユーザーIDを取得
    	String userId = (String) session.getAttribute("userId");
    	//セッションが切れてた場合
    	if(userId == null){
    		model.addAttribute("errorMessage",errorMessage.sessionError());
    		return "error";
    	}
    	//セッションにルームIDがない場合
    	if(sessionRoomId == null){
    		//ルームIDを取得
	    	int roomId = roomTable.createRoom(userId);
	    	//ルームIDをセッションスコープに保存
	    	session.setAttribute("roomId", roomId);
    	}
        return "5_othello";
    }

}