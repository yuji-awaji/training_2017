package jp.com.xpower.app2017.model;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.com.xpower.app2017.model.BoardGameConstant.Error;
import jp.com.xpower.app2017.model.BoardGameConstant.State;


@Service
public class RoomCreate {
	@Autowired
	RoomTableRepository roomTableRepository;
	@Autowired
	RoomOperation roomOperation;
	//ルームの処理
    public boolean createRoom(HttpSession session,Model model){
//    	int i;
//    	Integer ref_i;
    	//セッションスコープからユーザーIDを取得 5月18日指摘　位置移動
    	String userId = (String) session.getAttribute("userId");
    	//セッションが切れてた場合
    	if(userId == null){
    		session.invalidate();//セッション破棄
    		model.addAttribute("errorMessage",Error.SESSIONERROR);
    		return true;
    	}

    	//セッションスコープからルームIDを取得
//    	Object sessionRoomId = session.getAttribute("roomId");5月18日指摘　Integer型に変更
    	Integer sessionRoomId = (Integer) session.getAttribute("roomId");
    	//セッションにルームIDがない場合
    	if(sessionRoomId == null){
    		int roomId;
	    	//userIdがroom_master_idと一致かつroom_stateが対戦待機状態のレコードがあるか取得
	    	List<RoomTable> cancelList = roomTableRepository.findByRoomMasterIdAndRoomState(userId,State.WAIT);
	    	//あるのなら、そのレコードのroom_stateを対戦終了状態に変える
		    if(cancelList.isEmpty() == false){
		    	RoomTable roomTable = cancelList.get(0);
		    	roomOperation.updateRoom(roomTable);
		    }
		    //challenger_idがnullのものかつ、room_stateが対戦待機状態かつroom_masster_idとuserIdが一致しないものを取得
		    List<RoomTable> selectList = roomTableRepository.findByChallengerIdIsNullAndRoomStateAndRoomMasterIdNot(State.WAIT,userId);
	    	//取得できなかった場合
	    	if(selectList.isEmpty()){
	    		//roomテーブルに、userIDがroom_master_id、game_idが"othello"のレコードを挿入
	    		roomId=roomOperation.insertRoom(userId);
	    	//nullじゃない場合
	    	}else{
	    		//roomテーブルのレコードを保存
	    		RoomTable roomTable = selectList.get(0);
	    		roomId=roomOperation.updateRoom(roomTable, userId);
	    	}
	    	session.setAttribute("roomId", roomId);
    	}
    	return false;
    }
}
