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

	//部屋を作成する際の値をセットする 5月24日のレビューにてstaticを追加
	public static RoomTable create(String userId,String gameId){
		RoomTable createdRoom = new RoomTable();
		createdRoom.setRoomMasterId(userId);
		createdRoom.setGameId(gameId);
		return createdRoom;
	}

	//
	/*public void updateRoomCancel(){
		//String userId = (String) session.getAttribute("userId");
    	//セッションが切れてた場合
    	if(userId == null){
    		session.invalidate();//セッション破棄
    		model.addAttribute("errorMessage",Error.SESSIONERROR);
    	}

    	//セッションスコープからルームIDを取得
//    	Object sessionRoomId = session.getAttribute("roomId");5月18日指摘　Integer型に変更
    	RoomTable room = (RoomTable) session.getAttribute("room");
    	//セッションにルームIDがない場合
    	if(room == null){
	    	//userIdがroom_master_idと一致かつroom_stateが対戦待機状態のレコードがあるか取得
	    	List<RoomTable> cancelList = roomTableRepository.findByRoomMasterIdAndRoomState(userId,State.WAIT);
	    	//あるのなら、そのレコードのroom_stateを対戦終了状態に変える
		    if(cancelList.isEmpty() == false){
		    	RoomTable roomTable = cancelList.get(0);
		    	updateState(roomTable);
		    }
    	}
	}



	public void create(HttpSession session,Model model){
//    	int i;
//    	Integer ref_i;

    	//セッションスコープからユーザーIDを取得 5月18日指摘　位置移動
    	String userId = (String) session.getAttribute("userId");
    	//セッションが切れてた場合
    	if(userId == null){
    		session.invalidate();//セッション破棄
    		model.addAttribute("errorMessage",Error.SESSIONERROR);

    	}

    	//セッションスコープからルームIDを取得
//    	Object sessionRoomId = session.getAttribute("roomId");5月18日指摘　Integer型に変更
    	RoomTable room = (RoomTable) session.getAttribute("room");
    	//セッションにルームIDがない場合
    	if(room == null){
	    	//userIdがroom_master_idと一致かつroom_stateが対戦待機状態のレコードがあるか取得
	    	List<RoomTable> cancelList = roomTableRepository.findByRoomMasterIdAndRoomState(userId,State.WAIT);
	    	//あるのなら、そのレコードのroom_stateを対戦終了状態に変える
		    if(cancelList.isEmpty() == false){
		    	RoomTable roomTable = cancelList.get(0);
		    	updateState(roomTable);
		    }
		    //challenger_idがnullのものかつ、room_stateが対戦待機状態かつroom_masster_idとuserIdが一致しないものを取得
		    List<RoomTable> selectList = roomTableRepository.findByChallengerIdIsNullAndRoomStateAndRoomMasterIdNot(State.WAIT,userId);
	    	//取得できなかった場合
	    	if(selectList.isEmpty()){
	    		//roomテーブルに、userIDがroom_master_id、game_idが"othello"のレコードを挿入
	    		room=insertMaster(userId);
	    	//nullじゃない場合
	    	}else{
	    		//roomテーブルのレコードを保存
	    		RoomTable roomTable = selectList.get(0);
	    		room=updateChallenger(roomTable, userId);
	    	}
	    	model.addAttribute("room",room);
    	}
    }*/
}