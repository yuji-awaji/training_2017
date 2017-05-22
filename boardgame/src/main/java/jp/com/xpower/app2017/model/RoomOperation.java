package jp.com.xpower.app2017.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.com.xpower.app2017.model.BoardGameConstant.State;

@Service
public class RoomOperation {
	@Autowired
	RoomTableRepository roomTableRepository;
	public int insertRoom(String userId){
		RoomTable roomTable = new RoomTable();
		roomTable.setRoomMasterId(userId);
		roomTable.setGameId("othello");
    	roomTable.setRoomState(State.WAIT);
    	roomTableRepository.save(roomTable);
    	int roomId=roomTable.getRoomId();
    	return roomId;
	}
	public int updateRoom(RoomTable roomTable,String userId){
		roomTable.setChallengerId(userId);
		Date date = new Date();
    	roomTable.setBattleStartTime(date);
    	roomTable.setRoomState(State.MIDST);
    	roomTableRepository.save(roomTable);
    	int roomId = roomTable.getRoomId();
    	return roomId;
	}
	public int updateRoom(RoomTable roomTable){
    	roomTable.setRoomState(State.END);
    	roomTableRepository.save(roomTable);
    	int roomId = roomTable.getRoomId();
    	return roomId;
	}
}
