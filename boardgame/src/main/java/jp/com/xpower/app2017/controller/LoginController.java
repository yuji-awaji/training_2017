package jp.com.xpower.app2017.controller;

import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.ErrorMessage;
import jp.com.xpower.app2017.model.ErrorMessage.Error;
import jp.com.xpower.app2017.model.RoomCreate;
import jp.com.xpower.app2017.model.SelectUserTable;
import jp.com.xpower.app2017.model.UserTable;

@Controller
public class LoginController {
	@Autowired
	SelectUserTable userTable;
	@Autowired
	RoomCreate roomTable;
	@Autowired
	ErrorMessage errorMessage;
	@RequestMapping("/menu")
    public String requestMenu(HttpSession session,Model model){
    	boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
    		    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    	//セッションにuserIdを保存
    	String userId;
    	//デバッグモードかどうか判定
    	//デバッグの場合、セッションスコープに保存
    	if(isDebug){
	    	userId = "bbbb";
	    	session.setAttribute("userId", userId);
	    	try{
	    		UserTable selectUserTable = userTable.selectUserTable(userId);
	    		//ニックネームを取得
	        	String nickname=selectUserTable.getNickname();
	        	//プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
		        String encoded = Base64.getEncoder() .encodeToString(selectUserTable.getProfileImage());
	        	//リクエストスコープに保存して送る
	        	model.addAttribute("nickname",nickname);
	        	model.addAttribute("image",encoded);
	            return "4_menu";
	    	}catch(Exception e){
	    		session.invalidate();
	    		model.addAttribute("errorMessage",Error.DATABASEERROR);
	    		return "error";
	    	}
	    //デバッグでない場合、セッションスコープから取得
    	}else{
    		return "4_menu";
    	}
    }
}
