package jp.com.xpower.app2017.controller;


import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.SelectUserTable;
import jp.com.xpower.app2017.model.SignupError;
import jp.com.xpower.app2017.model.UserDataCheck;
import jp.com.xpower.app2017.model.UserInfo;
import jp.com.xpower.app2017.model.UserTable;
import jp.com.xpower.app2017.model.UserTableRepository;


@Controller
@ComponentScan("model")
public class SignUpController {
	final String aleadyIdError="IDは既に登録済みです<br></br>別のIDを入力してください";

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();// パスワードハッシュ用
	@Autowired
	SelectUserTable userTable;
	@Autowired
	UserTableRepository userTableRepository;
	@PostMapping("/upload")
	String upload(@ModelAttribute UserInfo userData,Model model) {
		UserDataCheck userCheck = new UserDataCheck();// ユーザーの入力が正規であるかチェック
		// 入力チェックメソッド呼び出し
		userCheck.checkData(userData);
		UserTable selectUserTable = userTable.selectUserTable(userData.getId());
		if (selectUserTable.getuserId()!=null) {
			userCheck.getErrorMessage().addErrorMessage(0,aleadyIdError);
		}


		if(userCheck.getErrorMessage().getErrorMessage().isEmpty()){
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();// パスワードハッシュ用
			String hashPassword= bcrypt.encode(userData.getPassword());
			UserTable addDataBase= new UserTable();
			addDataBase.setUserId(userData.getId());
			addDataBase.setPassword(hashPassword);
			addDataBase.setNickname(userData.getNickName());
			addDataBase.setProfileImage(userCheck.getImageByte());
			addDataBase.setFileType(userCheck.getFileType());
			addDataBase.setWin(0);
			addDataBase.setLose(0);
			addDataBase.setDraw(0);
			userTableRepository.save(addDataBase);
			System.out.println("aasjjasj");
			List<UserTable> selectList = userTableRepository.findByUserId(userData.getId());
			UserTable signUpUserRepository=new UserTable();
			signUpUserRepository=selectList.get(0);
			//プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
			String encoded = Base64.getEncoder().encodeToString(signUpUserRepository.getProfileImage());
			// 画像の形式、base64形式のバイナリデータ、ユーザー情報のmodelへの格納
			model.addAttribute("image_type", signUpUserRepository.getFileType());
			model.addAttribute("image", encoded);
			model.addAttribute("UserResult", userData);
			// 登録完了画面呼び出 し
			return "2_1_registered";
		}
		else {

			if(userCheck.getExceptionMessage()==null){
				// 登録失敗時の処理。フラグを送り、エラーメッセージを表示する
				System.out.println("lll");
				model.addAttribute("UserInfo", new UserInfo());
				System.out.println("jjj");
				model.addAttribute("ErrorMessage",userCheck.getErrorMessage().getErrorMessage() );
				System.out.println("kkk");
				return "2_signup";
			}
			else{
				System.out.println("mmm");
				model.addAttribute("errorMessage",userCheck.getExceptionMessage());
				return "error";
			}
		}
	}





	// ユーザー登録画面表示処理
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("UserInfo", new UserInfo());
		model.addAttribute("ErrorFlag", new SignupError());
		return "2_signup";
	}

}
