package jp.com.xpower.app2017.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.SignupError;
import jp.com.xpower.app2017.model.UserInfo;
import jp.com.xpower.app2017.model.UserTableRepository;

@Controller
public class SignUpController {

	@Autowired
	UserTableRepository userTableRepository;

	// ユーザー登録画面表示処理。
	@RequestMapping("/signup")
	public String signup(Model model) {
		// ユーザーの入力を受けとるインスタンスをスコープに保存。
		model.addAttribute("UserInfo", new UserInfo());
		// 発生したエラーの内容を送る。（初回表示なのでエラーメッセージは空であり表示されない）
		model.addAttribute("ErrorFlag", new SignupError());
		return "2_signup";
	}

	// ユーザー登録画面でユーザーが新規登録ボタンを押したときに呼ばれる処理
	@PostMapping("/confirm")
	String confirm(@ModelAttribute UserInfo userInfo, Model model) {

		boolean formDataValidation;// 入力値正誤格納変数

		// 入力値の整合性の確認。ファイルを開くので例外のtry catch
		try {
			formDataValidation=userInfo.userInfoValidate(userTableRepository);

		} catch (Exception e) {// 例外発生時の処理。例外の内容をエラーページに送信

			model.addAttribute("errorMessage", userInfo.getExceptionMessage());
			return "error";
		}
		//入力値がDBに保存可能である場合の処理
		if (formDataValidation) {

			//データベースへ値を登録するメソッドの呼び出し
			UserInfo.insertUserData(userInfo, userTableRepository, model);
			return "2_1_registered";

		} else {//入力値がDBに保存不可である場合の処理
			// ユーザーの入力を受けとるインスタンスをスコープに保存
			model.addAttribute("UserInfo", userInfo);
			// 入力が不可であった理由のメッセージをクライアントの表示画面に送る
			model.addAttribute("ErrorMessage", userInfo.getErrorMessage());
			return "2_signup";

		}
	}

}
