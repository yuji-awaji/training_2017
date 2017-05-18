package jp.com.xpower.app2017.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoomTable{
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private int roomId;
	@Column(nullable = false)
	private String gameId;
	@Column(nullable = false)
	private String roomMasterId;
	@Column(nullable = true)
	private String challengerId;
	@Column(nullable = true)
	private int roomState;
	@Column(nullable = true)
	private Date battleStartTime;

	public RoomTable(){

	}

	public int getRoomId() {
		return roomId;
	}


	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}


	public String getGameId() {
		return gameId;
	}


	public void setGameId(String gameId) {
		this.gameId = gameId;
	}


	public String getRoomMasterId() {
		return roomMasterId;
	}


	public void setRoomMasterId(String roomMasterId) {
		this.roomMasterId = roomMasterId;
	}


	public String getChallengerId() {
		return challengerId;
	}


	public void setChallengerId(String challengerId) {
		this.challengerId = challengerId;
	}


	public int getRoomState() {
		return roomState;
	}


	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}


	public Date getBattleStartTime() {
		return battleStartTime;
	}


	public void setBattleStartTime(Date battleStartTime) {
		this.battleStartTime = battleStartTime;
	}
}