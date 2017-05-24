package jp.com.xpower.app2017.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.BoardGameConstant.Error;
import jp.com.xpower.app2017.model.RoomState.State;
import jp.com.xpower.app2017.model.RoomTable;
import jp.com.xpower.app2017.model.RoomTableRepository;


@Controller
public class MenuController {
	@Autowired
	RoomTableRepository roomTableRepository;
    /*工事中
    @RequestMapping("/userProfile")//5月19日　メンバー間で命名規則再確認、変更
    public String userProfile() {
        return "3_user_profile";
    }*/

    @RequestMapping("/othello")
    public String othello(HttpSession session,Model model) {
//    	int i;
//    	Integer ref_i;
    	/*boolean isError=roomTable.createRoom(session,model);
    	if(isError==true){
    		return "/error";
    	}*/

    	//ログイン確認
    	String userId = (String) session.getAttribute("userId");
    	System.out.println(userId+"ユーザーID");
    	if(userId == null){
    		session.invalidate();//セッション破棄
    		model.addAttribute("errorMessage",Error.SESSIONERROR);
    		return "/error";
    	}

    	//セッションにルームがあるか確認
    	RoomTable room = (RoomTable) session.getAttribute("room");
    	if(room != null){
    		return "5_othello";
    	}

    	//ログインしているユーザーがmasterで部屋状態が待機中の部屋を探索
    	List<RoomTable> invalidRooms = roomTableRepository.findByRoomMasterIdAndRoomState(userId,State.WAIT);

    	//いらない部屋を対戦終了状態にする
    	for(RoomTable invalidRoom:invalidRooms){
    		invalidRoom.setRoomState(State.END);
    		roomTableRepository.save(invalidRoom);
    	}
    	//チャレンジャーが入れる部屋があるかどうかを調べる
    	List<RoomTable> challengebleRooms = roomTableRepository.findByChallengerIdIsNullAndRoomStateAndRoomMasterIdNot(State.WAIT,userId);
    	if(challengebleRooms.isEmpty()){
    		//部屋のレコードを新たに作る
    		RoomTable createdRoom = new RoomTable();
    		createdRoom=createdRoom.create(userId, "othello");
    		roomTableRepository.save(createdRoom);
    		session.setAttribute("room", createdRoom);
    		session.setAttribute("roomMasterNickname",(String)session.getAttribute("nickname"));
    		session.setAttribute("roomMasterImage",(String)session.getAttribute("image"));
    	}else{
    		//部屋にチャレンジャーIDを入れて、対戦開始状態にする。
    		RoomTable targetRoom = challengebleRooms.get(0);
    		targetRoom.setChallengerId(userId);
    		targetRoom.setRoomState(State.MIDST);
    		Date currentTime = new Date();
    		targetRoom.setBattleStartTime(currentTime);
    		roomTableRepository.save(targetRoom);
    		session.setAttribute("room", targetRoom);
    		session.setAttribute("challengerNickname",(String)session.getAttribute("nickname"));
    		session.setAttribute("challengerImage",(String)session.getAttribute("image"));
    	}

        return "5_othello";
    }

}