package model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import controller.UserTableRepository;

public class UserDataCheck {

	ErrorMsgFlag errorFlag = new ErrorMsgFlag();

	@Autowired
	UserTableRepository UserTableRepository;

	public ErrorMsgFlag inputCheck(UserInfo userData) {

		// IDの文字列の長さチェック
		if (userData.getId().length() >= 3 && userData.getId().length() <= 10) {
			// IDの文字の形式チェック
			if (userData.getId().matches("[0-9a-zA-Z]+")) {
				errorFlag.setId(true);

			}
		}
		// パスワードの文字列の長さチェック
		if (userData.getPassword().length() >= 8 && userData.getPassword().length() <= 32) {
			// パスワードの文字の形式チェック
			if (userData.getPassword().matches("[0-9a-zA-Z]+")) {
				errorFlag.setPassword(true);

			}
		}
		// パスワードと再入力パスワードの一致チェック
		if (userData.getPassword().equals(userData.getRePassword())) {
			errorFlag.setRePassword(true);
		}

		// ニックネームの文字の長さチェック
		if (userData.getNickName().length() >= 3 && userData.getNickName().length() <= 20) {
			// 半角スペースをチェック
			if (userData.getNickName().indexOf(" ") == -1) {
				// 全角スペースをチェック
				if (userData.getNickName().indexOf("　") == -1) {
					errorFlag.setNickName(true);
				}
			}
		}
		// アップロード画像のファイル形式チェック
		if (userData.getProfileImage().getOriginalFilename().endsWith(".JPG")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".jpg")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".JPEG")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".jpeg")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".BMP")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".bmp")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".PNG")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".png")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".GIF")
				|| userData.getProfileImage().getOriginalFilename().endsWith(".gif")) {
			errorFlag.setProfileImageType(true);

			BufferedImage image = null;
			String imageName = userData.getProfileImage().getOriginalFilename();

			// 画像の縦横の大きさチェック
			try {
				image = ImageIO.read(new File(imageName));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			if (image.getWidth() >= 100 && image.getHeight() >= 100) {
				errorFlag.setProfileImageSize(true);

			}

		}
		// 画像がアップロードされていないときの処理
		else if (userData.getProfileImage().isEmpty()) {
			errorFlag.setProfileImageType(true);
			errorFlag.setProfileImageSize(true);
		}
		return errorFlag;
	}
}
