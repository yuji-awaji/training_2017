package controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignUpController
{

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();//パスワードハッシュ用
	@Autowired
	UserTableRepository UserTableRepository;

	@PostMapping("/upload")
	String upload(@ModelAttribute model.UserInfo userData, Model model) {

		model.UserDataCheck userCheck = new model.UserDataCheck();//ユーザーの入力が正規であるかチェック
		model.ErrorMsgFlag errorFlag = new model.ErrorMsgFlag();//入力ミスがあった場合に表示するエラーメッセージのフラグインスタンス

		//入力チェックメソッド呼び出し
		errorFlag=userCheck.inputCheck(userData);

		//null=エラーであるため、エラー画面を表示
		if(errorFlag.equals(null)){
			model.addAttribute("errorMessage", "画像の読み込みに失敗しました");
			return "error";
		}

		//IDがDBに登録されているかの確認
		if(errorFlag.isId()){
			List<controller.UserTable> selectList = UserTableRepository.findByUserIdContains(userData.getId());
        	if(selectList.isEmpty()){
        		errorFlag.setAlreadyId(true);

        	}
		}

		//入力チェックがすべて問題ない場合の処理
		if(errorFlag.isId()==true && errorFlag.isAlreadyId()==true && errorFlag.isPassword()==true && errorFlag.isRePassword()==true && errorFlag.isNickName()==true && errorFlag.isProfileImageType()==true && errorFlag.isProfileImageSize()==true){
			String extension="jpg";//初期化


			//ユーザーがアップロードした画像の拡張子識別処理
			if(userData.getProfileImage().getOriginalFilename().endsWith(".JPG")){
				extension="jpg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".jpg")){
				extension="jpg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".JPEG")){
				extension="jpeg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".jpeg")){
				extension="jpeg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".BMP")){
				extension="bmp";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".bmp")){
				extension="bmp";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".PNG")){
				extension="png";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".png")){
				extension="png";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".GIF")){
				extension="gif";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".gif")){
				extension="gif";

			}
			File file=null;
			FileInputStream fis=null;
			if(userData.getProfileImage().isEmpty()){//ユーザーデフォルト画像の取得
				file = new File("./src/main/resources/static/img/user_default.jpg");
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					model.addAttribute("errorMessage", "画像の保存に失敗しました");
					return "error";

				}
			}
			else{//ユーザーがアップロードした画像の取得
				file = new File(userData.getProfileImage().getOriginalFilename());
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					model.addAttribute("errorMessage", "画像の保存に失敗しました");
					return "error";
				}

			}
			//画像のByte形式への変換
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			OutputStream os = new BufferedOutputStream(byteOutput);
			int count;
			try {
				while ((count = fis.read()) != -1) {
					os.write(count);
				}
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "画像の保存に失敗しました");
				return "error";
			} finally {
				if (os != null) {
					try {
						os.flush();
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
						model.addAttribute("errorMessage", "画像の保存に失敗しました");
						return "error";
					}
				}
			}

			String hashPassword= bcrypt.encode(userData.getPassword());//パスワードハッシュ化

			//DBへのユーザーデータの保存
			UserTableRepository.save(new UserTable(userData.getId(),hashPassword,userData.getNickName(),byteOutput.toByteArray(),extension));

			//登録したユーザーデータの呼び出し
			List<UserTable> selectList = UserTableRepository.findByUserIdContains(userData.getId());
	        UserTable ut = selectList.get(0);

	        //プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
	        String encoded = Base64.getEncoder() .encodeToString(ut.getProfileImage());

	        //画像の形式、base64形式のバイナリデータ、ユーザー情報のmodelへの格納
			model.addAttribute("image_type",ut.getFileType());
			model.addAttribute("image",encoded);
			model.addAttribute("UserResult", userData);
			//登録完了画面呼び出	し
			return "2_1_registered";
		}else{
			//登録失敗時の処理。フラグを送り、エラーメッセージを表示する
			model.addAttribute("UserInfo", new model.UserInfo());
			model.addAttribute("ErrorFlag", errorFlag);
			return "2_signup";
		}
	}

	//ユーザー登録画面表示処理
	@RequestMapping("/signup")
    public String signup(Model model) {
		model.addAttribute("UserInfo", new model.UserInfo());
		model.addAttribute("ErrorFlag", new model.ErrorMsgFlag(1));//model.ErrorMsgFlagクラスに1を送ると、フラグが初期化される
        return "2_signup";
    }
	//ログイン画面表示処理
	@RequestMapping( "/") // (5)
    public String login(Model model) {// (3)
		model.addAttribute("ErrorFlag", true);
        return "1_login";
    }
}
