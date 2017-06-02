package jp.com.xpower.app2017.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class OthelloInfo implements Serializable{
	//roomStateを2に変える
	public static void endRoomState(RoomTable invalidRoom, RoomTableRepository roomTableRepository){
		roomTableRepository.save(invalidRoom);
	}
	public static RoomTable getCheckRoom(int roomId, RoomTableRepository roomTableRepository){
		RoomTable checkedRoom = roomTableRepository.findOne(roomId);
		return checkedRoom;
	}

	public static UserTable getUser(String userId, UserTableRepository userTableRepository){
		List<UserTable> selectList = userTableRepository.findByUserId(userId);
        UserTable userTable = selectList.get(0);
		return userTable;
	}

}
