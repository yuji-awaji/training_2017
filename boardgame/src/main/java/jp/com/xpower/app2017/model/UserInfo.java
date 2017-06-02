package jp.com.xpower.app2017.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

//ユーザー登録時のユーザー情報の一時的な格納インスタンス
@Service
@Component
public class UserInfo implements Serializable {

	// フォームの入力内容が格納される変数
	private String id = null;
	private String password = null;
	private String rePassword = null;
	private String nickName = null;
	private MultipartFile profileImage = null;
	// <-フォーム用変数ここまで

	private byte[] imageByte;// ユーザーのプロフ画像のバイナリデータを格納する変数
	private String fileType;// ユーザーのプロフ画像のファイル形式(MIME)を格納するインスタンス
	private ArrayList<String> errorMessage = new ArrayList<String>();// エラーメッセージの格納を行う配列
	private String exceptionMessage;
	private String hashPassword;// ハッシュ化したパスワードを保存する変数

	public ArrayList<String> getErrorMessage() {
		return errorMessage;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public String getFileType() {
		return fileType;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public MultipartFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// 入力値チェックメソッド（入力値に問題がなければtrue,問題があればfalseを返す）。例外発生時にはコントローラーに例外を投げる
	public boolean userInfoValidate(UserTableRepository userTableRepository) throws Exception {

		// IDの文字列の長さ(3以上10以下)と正規表現による半角英数字チェック
		if (!(this.id.length() >= 3 && this.id.length() <= 10
				&& this.id.matches(BoardGameConstant.SignUpConstant.INPUTWORDCHECK))) {
			// 入力値が間違っていた場合に、エラーメッセージを格納
			errorMessage.add(BoardGameConstant.SignUpConstant.IDERROR);
		} else {
			if (!(userTableRepository.findByUserId(this.id).isEmpty())) {
				errorMessage.add(BoardGameConstant.SignUpConstant.ALEADYIDERROR);
			}
		}

		// パスワードの文字列の長さ(8以上32以下)と正規表現による半角英数字チェック
		if (!(this.password.length() >= 8 && this.password.length() <= 32
				&& this.password.matches(BoardGameConstant.SignUpConstant.INPUTWORDCHECK))) {
			// パスワードの入力値が間違っていた場合のエラーメッセージ格納
			errorMessage.add(BoardGameConstant.SignUpConstant.PASSWORDERROR);
		}

		// パスワードと再入力パスワードの一致チェック
		if (!(this.password.equals(this.rePassword))) {
			// パスワードと再入力パスワードが間違っていた場合のエラーメッセージ格納
			errorMessage.add(BoardGameConstant.SignUpConstant.REPASSWORDERROR);
		}
		// ニックネームの文字の長さと、半角全角空白の入力が入ってないかのチェック
		if (!(this.nickName.length() >= 3 && this.nickName.length() <= 20
				&& this.nickName.matches(BoardGameConstant.SignUpConstant.NICKNAMEMATCH))) {
			// ニックネームの入力値が間違っていた場合のエラーメッセージ格納
			errorMessage.add(BoardGameConstant.SignUpConstant.NICKNAMEERROR);
		}
		// ユーザープロフ画像がアップロードされなかった場合の処理
		if (this.profileImage.isEmpty()) {

			// ファイルのMIMEを格納
			fileType = BoardGameConstant.SignUpConstant.DEFAULTIMAGETYPEMIME;

			// ユーザープロフィール画像のバイナリデータへの変換
			BufferedImage readImage;
			try {
				File file = new File(BoardGameConstant.SignUpConstant.DEFAULTIMAGEURL);
				readImage = ImageIO.read(file);
				ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
				ImageIO.write(readImage, BoardGameConstant.SignUpConstant.DEFAULTIMAGETYPE, outPutStream);
				imageByte = outPutStream.toByteArray();
			} catch (Exception e) {
				exceptionMessage = BoardGameConstant.Error.GETDEFAULTIMAGEERORR;
				throw e;
			}

		} else {
			// ユーザー登録画像のファイル形式取得
			fileType = this.profileImage.getContentType();

			// ファイル形式の判別時に、大文字と小文字を区別しないようにする前準備
			Pattern pattern = Pattern.compile(BoardGameConstant.SignUpConstant.FILEMATCH, Pattern.CASE_INSENSITIVE);
			Matcher match = pattern.matcher(fileType);
			// アップロード画像のファイル形式チェック
			if (match.matches()) {
				BufferedImage image = null;
				// 画像の縦横の大きさチェック
				try {
					image = ImageIO.read(new ByteArrayInputStream(this.profileImage.getBytes()));
					if (!(image.getWidth() >= 100 && image.getHeight() >= 100)) {
						// 画像サイズのエラーメッセージ格納
						errorMessage.add(BoardGameConstant.SignUpConstant.IMAGESIZEERROR);
					} else {
						// ユーザーのアップロード画像のバイナリデータ取得
						imageByte = this.profileImage.getBytes();
					}
				} catch (Exception e) {
					exceptionMessage = BoardGameConstant.Error.GETIMAGEERROR;
					throw e;
				}
			} else {
				// ファイルの形式が違うエラーメッセージ
				errorMessage.add(BoardGameConstant.SignUpConstant.FILETYPEERROR);
			}

		}
		// エラーメッセージがない＝登録可能な入力であった時の処理

		if (errorMessage.isEmpty()) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();// Bcryptによるハッシュ化インスタンス生成
			hashPassword = bcrypt.encode(this.password);// 入力されたパスワードのハッシュ化
			return true;
		} else {
			return false;
		}

	}

	// DBへユーザー情報の保存を行うメソッド
	public static void insertUserData(UserInfo formData, UserTableRepository userTableRepository, Model model) {
		// DBに入力値を保存

		// 値を格納
		UserTable userTable = new UserTable();
		userTable.setUserId(formData.getId());
		userTable.setPassword(formData.getHashPassword());
		userTable.setNickname(formData.getNickName());
		userTable.setProfileImage(formData.getImageByte());
		userTable.setFileType(formData.getFileType());
		userTable.setWin(BoardGameConstant.SignUpConstant.NEWUSERSCORE);
		userTable.setLose(BoardGameConstant.SignUpConstant.NEWUSERSCORE);
		userTable.setDraw(BoardGameConstant.SignUpConstant.NEWUSERSCORE);
		// ユーザー情報を保存
		userTableRepository.save(userTable);

		// データベースに入力した値の取り出し
		List<UserTable> selectList = userTableRepository.findByUserId(formData.getId());
		UserTable signUpUserRepository = selectList.get(0);

		// プロフィール画像をHTML上で表示するための、バイナリデータのBase64形式でのエンコード
		String encoded = Base64.getEncoder().encodeToString(signUpUserRepository.getProfileImage());

		// 画像の形式、base64形式のバイナリデータ、ユーザー情報をmodelへの格納
		model.addAttribute("fileType", signUpUserRepository.getFileType());
		model.addAttribute("image", encoded);
		model.addAttribute("userNickName", formData.getNickName());
	}

}