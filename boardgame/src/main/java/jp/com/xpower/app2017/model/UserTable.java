package jp.com.xpower.app2017.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTable{
	@Id
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String nickname;
	@Lob
    @Column(length=16777215,nullable = false )
	private byte[]  profileImage;
	@Column(nullable = false)
	private String fileType;
	@Column(nullable = false)
	private int win;
	@Column(nullable = false)
	private int lose;
	@Column(nullable = false)
	private int draw;

	public UserTable(){

	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public byte[] getprofileImagePath() {
		return profileImage;
	}
	public void setprofileImagePath(byte[] profileImagePath) {
		this.profileImage = profileImagePath;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public byte[] getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}