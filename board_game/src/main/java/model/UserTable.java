package controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	@Column(nullable = false)
	private String profileImagePath;
	@Column(nullable = false)
	private int win;
	@Column(nullable = false)
	private int lose;
	@Column(nullable = false)
	private int draw;

	public UserTable(){

	}
	public UserTable(String userId,String password,String nickname,
			String profileImagePath){
		this.userId=userId;
		this.password=password;
		this.nickname=nickname;
		this.profileImagePath=profileImagePath;
	}

	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
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
	public String getprofileImagePath() {
		return profileImagePath;
	}
	public void setprofileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
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

	@Override
    public String toString()
    {
        return this.nickname+","+profileImagePath+","+win+","+lose+","+draw;

    }
}