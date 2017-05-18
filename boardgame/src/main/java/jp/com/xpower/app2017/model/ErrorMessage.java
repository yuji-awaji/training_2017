package jp.com.xpower.app2017.model;

import org.springframework.stereotype.Controller;

@Controller
public class ErrorMessage {
	public String databaseError(){
		return "データベースエラーです";
	}
	public String sessionError(){
		return "セッションが切れました";
	}
}
