package jp.com.xpower.app2017.model;

import java.util.ArrayList;

//エラーメッセージの表示フラグの管理インスタンス
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
