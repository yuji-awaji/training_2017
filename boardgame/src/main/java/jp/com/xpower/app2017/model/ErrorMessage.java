package jp.com.xpower.app2017.model;

import org.springframework.stereotype.Service;

@Service
public final class ErrorMessage {
	public static class Error{
		public static final String GETIMAGEERROR = "画像の読み込み中にエラーが発生しました";
        public static final String GETDEFAULTIMAGEERORR = "ユーザーデフォルト画像の読み込みに失敗しました";
        public static final String SESSIONERROR = "セッションが切れました。もう一度ログインしてください";
        public static final String DATABASEERROR = "データベース起動していません。お手数ですが、少し時間をおいてください";
	}
}
