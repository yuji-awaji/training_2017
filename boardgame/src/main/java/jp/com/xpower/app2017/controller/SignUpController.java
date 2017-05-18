package controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class SignUpController {

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();// パスワードハッシュ用
	@Autowired
	UserTableRepository UserTableRepository;

	@PostMapping("/upload")
	String upload(@ModelAttribute model.UserInfo userData,Model model) throws IOException {


		model.UserDataCheck userCheck = new model.UserDataCheck();// ユーザーの入力が正規であるかチェック
		model.ErrorMsgFlag errorFlag = new model.ErrorMsgFlag();// 入力ミスがあった場合に表示するエラーメッセージのフラグインスタンス

		// 入力チェックメソッド呼び出し
		errorFlag = userCheck.inputCheck(userData);

		// null=エラーであるため、エラー画面を表示
		if (errorFlag.equals(null)) {
			model.addAttribute("errorMessage", "画像の読み込みに失敗しました");
			return "error";
		}

		// IDがDBに登録されているかの確認


		// 入力チェックがすべて問題ない場合の処理
		if(errorFlag.getErrorMsg().isEmpty()){



			// プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
			String encoded = Base64.getEncoder().encodeToString(ut.getProfileImage());

			// 画像の形式、base64形式のバイナリデータ、ユーザー情報のmodelへの格納
			model.addAttribute("image_type", ut.getFileType());
			model.addAttribute("image", encoded);
			model.addAttribute("UserResult", userData);
			// 登録完了画面呼び出 し
			return "2_1_registered";
		} else {
			// 登録失敗時の処理。フラグを送り、エラーメッセージを表示する
			model.addAttribute("UserInfo", new model.UserInfo());
			model.addAttribute("ErrorFlag", errorFlag);
			return "2_signup";
		}
	}

	// ユーザー登録画面表示処理
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("UserInfo", new model.UserInfo());
		model.addAttribute("ErrorFlag", new model.ErrorMsgFlag(1));// model.ErrorMsgFlagクラスに1を送ると、フラグが初期化される
		return "2_signup";
	}

	// ログイン画面表示処理
	@RequestMapping("/") // (5)
	public String login(Model model) {// (3)
		model.addAttribute("ErrorFlag", true);
		return "1_login";
	}
}
