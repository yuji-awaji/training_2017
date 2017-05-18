package jp.com.xpower.app2017.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jp.com.xpower.app2017.model.RoomState.State;


@Controller
public class RoomCreate {
	@Autowired
    RoomTableRepository roomTableRepository;
	//ルームの処理
    public int createRoom(String userId){
    	RoomState roomState = new RoomState();
    	int roomId;
    	List<RoomTable> cancelList = roomTableRepository.findByRoomMasterIdAndRoomState(userId,State.WAIT);
	    if(cancelList.isEmpty() == false){
	    	RoomTable cancelTable = cancelList.get(0);
	    	cancelTable.setRoomState(State.END);
	    	roomTableRepository.save(cancelTable);
	    }

	    //challengerIdがnullのものがあるかどうかをselect
	    List<RoomTable> selectList = roomTableRepository.findByChallengerIdIsNullAndRoomStateAndRoomMasterIdNot(State.WAIT,userId);
    	//nullだった場合
    	if(selectList.isEmpty()){
    		//roomテーブルに新たにroomレコードをinsert
    		RoomTable room1 = new RoomTable();
    		room1.setRoomMasterId(userId);
    		room1.setGameId("othello");
    		roomTableRepository.save(room1);
    		//ルームIDを取得
    		roomId = room1.getRoomId();
    	//nullじゃない場合
    	}else{
    		//roomテーブルのレコードを保存
    		RoomTable roomTable = selectList.get(0);
    		//userIdをchallengerIdにset
	    	roomTable.setChallengerId(userId);
	    	roomTable.setRoomState(State.MIDST);
	    	Date date = new Date();
	    	roomTable.setBattleStartTime(date);
	    	//roomテーブルをupdate
	    	roomTableRepository.save(roomTable);
	    	//roomIdの取得
	    	roomId = roomTable.getRoomId();
    	}
    	return roomId;
    }
}
