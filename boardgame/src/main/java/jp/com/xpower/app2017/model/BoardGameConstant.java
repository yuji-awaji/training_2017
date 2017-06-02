package jp.com.xpower.app2017.model;

public final class BoardGameConstant {
	public static class State {
		public static final int WAIT = 0;
		public static final int MIDST = 1;
		public static final int END = 2;

	}

	public static class Error {
		public static final String GETIMAGEERROR = "画像の読み込み中にエラーが発生しました";
		public static final String GETDEFAULTIMAGEERORR = "ユーザーデフォルト画像の読み込みに失敗しました";
		public static final String SESSIONERROR = "セッションが切れました。もう一度ログインしてください";
		public static final String DATABASEERROR = "データベース起動していません。お手数ですが、少し時間をおいてください";
	}

	// ユーザー登録機能用定数
	public static class SignUpConstant {
		public static final String ALEADYIDERROR = "IDは既に登録済みです<br></br>別のIDを入力してください";
		public static final String DEFAULTIMAGEURL = "static/img/user_default.jpg";
		public static final String FILEMATCH = "image/jpg|image/png|image/jpeg|image/gif|image/bmp";
		public static final String NICKNAMEMATCH = "[^　\\s]+";
		public static final String IDERROR = "IDは3文字以上10文字以下の半角英数字で<br></br>入力してください<br></br>";
		public static final String PASSWORDERROR = "パスワードは8文字以上32文字以下の半角英数字で<br></br>入力してください";
		public static final String INPUTWORDCHECK = "[0-9a-zA-Z]+";
		public static final String REPASSWORDERROR = "入力されたパスワードが一致しません";
		public static final String NICKNAMEERROR = "ニックネームは3文字以上20文字以下の半角全角文字<br></br>（スペースを除く）で入力してください";
		public static final String IMAGESIZEERROR = "指定された画像が最低画像サイズより小さいため<br></br>登録できません<br></br>100×100ピクセルより大きい画像を指定してください";
		public static final String FILETYPEERROR = "指定されたファイルが画像ではありません<br></br>画像ファイル（bmp,jpg/jpeg,gif,png）を指定してください";
		public static final int NEWUSERSCORE = 0;
		public static final String DEFAULTIMAGETYPE="jpg";
		public static final String DEFAULTIMAGETYPEMIME="image/jpeg";

	}
}

