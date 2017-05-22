package jp.com.xpower.app2017.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("controller")
public class UserDataCheck {
	byte[] imageByte=null;
	SignupError errorMessage = new SignupError();
	String exceptionMessage;
	final String defaultImageURL="./src/main/resources/static/img/user_default.jpg";
	final String fileMatch=".jpg$|.png$|.jpeg$|.gif$|.bmp$";
	final String nickNameMatch="[^　\\s]+";
	final String idError="IDは3文字以上10文字以下の半角英数字で<br></br>入力してください<br></br>";
	final String passwordError="パスワードは8文字以上32文字以下の半角英数字で<br></br>入力してください";
	String fileType=null;

	@Autowired
	UserTableRepository userTableRepository;
	@Autowired(required = true)
	SelectUserTable userTable;

	public void checkData(UserInfo userData) {
		exceptionMessage=null;

		System.out.println("aaaiaaaaadsao");
		// IDの文字列の長さチェック
		if (!(userData.getId().length() >= 3 && userData.getId().length() <= 10 && userData.getId().matches("[0-9a-zA-Z]+"))) {
			// IDの文字の形式チェック
			System.out.println("aaaaa");
			errorMessage.addErrorMessage(idError);
		}
		//else{
			//UserTable selectUserTable = (UserTable) userTable.selectUserTable("111");
	//	}


		System.out.println("aaaaa");
		// パスワードの文字列の長さチェック
		if (!(userData.getPassword().length() >= 8 && userData.getPassword().length() <= 32 && userData.getPassword().matches("[0-9a-zA-Z]+"))) {
			// パスワードの文字の形式チェック
			errorMessage.addErrorMessage(passwordError);


		}
		// パスワードと再入力パスワードの一致チェック
		System.out.println("bbb");
		if (!(userData.getPassword().equals(userData.getRePassword()))) {
			errorMessage.addErrorMessage("入力されたパスワードが一致しません");
		}
		System.out.println("ccc");
		// ニックネームの文字の長さチェック
		if (!(userData.getNickName().length() >= 3 && userData.getNickName().length() <= 20 && userData.getNickName().matches(nickNameMatch))) {
			errorMessage.addErrorMessage("ニックネームは3文字以上20文字以下の半角全角文字<br></br>（スペースを除く）で入力してください	");

		}



		System.out.println("ddd");
		if (userData.getProfileImage().isEmpty()) {
			System.out.println("eee");
			File file = null;
			FileInputStream fis = null;
			fileType="jpg";
			if (userData.getProfileImage().isEmpty()) {// ユーザーデフォルト画像の取得
				file = new File(defaultImageURL);
				try {
					BufferedImage readImage = ImageIO.read(file);
					ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
					ImageIO.write(readImage, fileType, outPutStream);
					imageByte=outPutStream.toByteArray();
				}
				catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					exceptionMessage="デフォルト画像の読み込み中にエラーが発生しました。";

				}
			}
			System.out.println("fff");
		}
		else {
			System.out.println("ggg");
			int lastDotPosition=userData.getProfileImage().getOriginalFilename().lastIndexOf(".");
			fileType=userData.getProfileImage().getOriginalFilename().substring(lastDotPosition);
			Pattern pattern = Pattern.compile(fileMatch, Pattern.CASE_INSENSITIVE);
			Matcher match=pattern.matcher(fileType);
			System.out.println(fileType);
			// アップロード画像のファイル形式チェック
			if (match.matches()){
				BufferedImage image = null;
				// 画像の縦横の大きさチェック
				System.out.println("aaaiao");
				try {
					//ByteArrayInputStream baos = userData.getProfileImage().getInputStream();
					image = ImageIO.read(new ByteArrayInputStream(userData.getProfileImage().getBytes()));
					if (!(image.getWidth() >= 100 && image.getHeight() >= 100)) {
						errorMessage.addErrorMessage("指定された画像が最低画像サイズより小さいため<br></br>登録できません<br></br>100×100ピクセルより大きい画像を指定してください");

					}else{
						imageByte=userData.getProfileImage().getBytes();
					}

				} catch (Exception e) {
					exceptionMessage="画像の読み込み中にエラーが発生しました。";

				}
			}else{
				errorMessage.addErrorMessage("指定されたファイルが画像ではありません<br></br>画像ファイル（bmp,jpg/jpeg,gif,png）を指定してください");
			}

		}

	}

	public SignupError getErrorMessage() {
		return errorMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getFileType() {
		return fileType;
	}



	public byte[] getImageByte() {
		return imageByte;
	}




}
