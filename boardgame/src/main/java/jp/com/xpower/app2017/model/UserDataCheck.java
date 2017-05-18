package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import controller.UserTable;
import controller.UserTableRepository;

public class UserDataCheck {

	ErrorMsgFlag errorFlag = new ErrorMsgFlag();

	@Autowired
	UserTableRepository UserTableRepository;

	public ErrorMsgFlag inputCheck(UserInfo userData) {

		// IDの文字列の長さチェック
		if (!(userData.getId().length() >= 3 && userData.getId().length() <= 10 && userData.getId().matches("[0-9a-zA-Z]+"))) {
			// IDの文字の形式チェック

			errorFlag.setErrorMsg("IDは3文字以上10文字以下の半角英数字で<br></br>入力してください<br></br>");
			List<UserTable> selectList = UserTableRepository.findByUserIdContains(userData.getId());
			if (selectList.isEmpty()) {
				errorFlag.setErrorMsg("IDは既に登録済みです<br></br>別のIDを入力してください");

			}

		}
		// パスワードの文字列の長さチェック
		if (!(userData.getPassword().length() >= 8 && userData.getPassword().length() <= 32 && userData.getPassword().matches("[0-9a-zA-Z]+"))) {
			// パスワードの文字の形式チェック
			errorFlag.setErrorMsg("パスワードは8文字以上32文字以下の半角英数字で<br></br>入力してください");


		}
		// パスワードと再入力パスワードの一致チェック
		if (!(userData.getPassword().equals(userData.getRePassword()))) {
			errorFlag.setErrorMsg("入力されたパスワードが一致しません");
		}

		// ニックネームの文字の長さチェック
		if (!(userData.getNickName().length() >= 3 && userData.getNickName().length() <= 20 && userData.getId().matches("^\\s|^　]+"))) {
			errorFlag.setErrorMsg("ニックネームは3文字以上20文字以下の半角全角文字<br></br>（スペースを除く）で入力してください	");

		}

		int lastDotPosition=userData.getProfileImage().getOriginalFilebyteename().lastIndexOf(".");
		String fileType=userData.getProfileImage().getOriginalFilename().substring(lastDotPosition + 1);
		 Pattern p = Pattern.compile(fileType, Pattern.CASE_INSENSITIVE);
		// アップロード画像のファイル形式チェック

		byte[] imageByte=null;
		if (fileType.matches("[.jpg|.png|.jpeg|.gif|.bmp]")) {
			BufferedImage image = null;
			// 画像の縦横の大きさチェック
			System.out.println("aaaiao");
			try {
				//ByteArrayInputStream baos = userData.getProfileImage().getInputStream();
				image = ImageIO.read(new ByteArrayInputStream(userData.getProfileImage().getBytes()));
				System.out.println("aaadsaiao");

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			if (!(image.getWidth() >= 100 && image.getHeight() >= 100)) {
				errorFlag.setErrorMsg("指定された画像が最低画像サイズより小さいため<br></br>登録できません<br></br>100×100ピクセルより大きい画像を指定してください");

			}else{
				imageByte=userData.getProfileImage().getBytes();
			}
			System.out.println("aaaiaoaaa");
		}
		// 画像がアップロードされていないときの処理
		else if (userData.getProfileImage().isEmpty()) {
			File file = null;
			FileInputStream fis = null;
			if (userData.getProfileImage().isEmpty()) {// ユーザーデフォルト画像の取得
				file = new File("./src/main/resources/static/img/user_default.jpg");
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO 自動生成された catch ブロック
					return null;

				}
			}
		}else{
			errorFlag.setErrorMsg("指定されたファイルが画像ではありません<br></br>画像ファイル（bmp,jpg/jpeg,gif,png）を指定してください");
		}



		return errorFlag;
	}
}
