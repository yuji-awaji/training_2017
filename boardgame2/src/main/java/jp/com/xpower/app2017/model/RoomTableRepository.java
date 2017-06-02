package jp.com.xpower.app2017.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTableRepository extends JpaRepository<RoomTable, Integer>
{
	public List<RoomTable> findByChallengerIdIsNullAndRoomStateAndRoomMasterIdNot(int roomState,String userId);
	public List<RoomTable> findByRoomMasterIdAndRoomState(String userId,int roomState);
}
