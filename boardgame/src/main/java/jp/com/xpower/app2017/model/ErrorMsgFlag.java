package model;

import java.util.ArrayList;

//エラーメッセージの表示フラグの管理インスタンス
public class ErrorMsgFlag {
	ArrayList<String> errorMsg = new ArrayList<String>();

	public ArrayList<String> getErrorMsg() {
		return eroorMsg;
	}

	public void setErrorMsg(String eroorMsg) {
		this.eroorMsg.add(eroorMsg);
	}
}
