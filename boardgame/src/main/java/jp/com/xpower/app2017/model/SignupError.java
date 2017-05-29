package jp.com.xpower.app2017.model;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

//エラーメッセージの表示の管理インスタンス
@Service
public class SignupError {
	ArrayList<String> errorMessage = new ArrayList<String>();
	public ArrayList<String> getErrorMessage() {
		return errorMessage;
	}

	public void addErrorMessage(String eroorMessage) {
		this.errorMessage.add(eroorMessage);
	}
	public void addErrorMessage(int i,String eroorMessage) {
		this.errorMessage.add(i,eroorMessage);
	}
}
