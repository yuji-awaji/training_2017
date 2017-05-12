package controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	private String user_id;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String nickname;
	@Column(nullable = false)
	private String profile_image_path;
	@Column(nullable = false)
	private int win;
	@Column(nullable = false)
	private int lose;
	@Column(nullable = false)
	private int draw;

	public UserTable(){

	}
	public UserTable(String user_id,String password,String nickname,
			String profile_image_path){
		this.user_id=user_id;
		this.password=password;
		this.nickname=nickname;
		this.profile_image_path=profile_image_path;
	}

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getProfile_image_path() {
		return profile_image_path;
	}
	public void setProfile_image_path(String profile_image_path) {
		this.profile_image_path = profile_image_path;
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
        return "user_id=" + this.user_id
                + "password=" + this.password
                + "nickname=" + this.nickname
        		+ "profile_image_path= " + this.profile_image_path
        		+ "win=" + this.win
        		+ "lose=" + this.lose
        		+ "draw=" + this.draw;
    }
}
