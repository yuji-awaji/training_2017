package model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;
//ユーザー登録時のユーザー情報の一時的な格納インスタンス
public class UserInfo implements Serializable{

    private String id=null;
    private String password=null;
    private String rePassword=null;
    private String nickName=null;
    private MultipartFile profileImage=null;
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


}