package jp.com.xpower.app2017.model;

public final class BoardGameConstant {
	public static class State{
		public static final int WAIT = 0;
        public static final int MIDST = 1;
        public static final int END = 2;
	}
	public static class Error{
		public static final String GETIMAGEERROR = "画像の読み込み中にエラーが発生しました";
	    public static final String GETDEFAULTIMAGEERORR = "ユーザーデフォルト画像の読み込みに失敗しました";
	    public static final String SESSIONERROR = "セッションが切れました。もう一度ログインしてください";
	    public static final String DATABASEERROR = "データベース起動していません。お手数ですが、少し時間をおいてください";
	}
}
