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
    @RequestMapping("/userProfile")//5月19日 メンバー間で命名規則再確認、変更
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
    	try{
	    	//ログイン確認
	    	String userId = (String) session.getAttribute("userId");

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

	    	//不要な部屋を対戦終了状態にする 5月24日レビューにてコメント変更 いらない→不要な
	    	for(RoomTable invalidRoom:invalidRooms){
	    		invalidRoom.setRoomState(State.END);
	    		roomTableRepository.save(invalidRoom);
	    	}

	    	//チャレンジャーが入れる部屋があるかどうかを調べる
	    	//5月24日レビューにて変数名修正 challengebleRooms→challengeableRooms
	    	List<RoomTable> challengeableRooms = roomTableRepository.findByChallengerIdIsNullAndRoomStateAndRoomMasterIdNot(State.WAIT,userId);
	    	if(challengeableRooms.isEmpty()){
	    		//部屋のレコードを新たに作る 5月24日のレビューにて = の両隣が詰まっていたので空けた
	    		RoomTable createdRoom = RoomTable.create(userId, "othello");
	    		roomTableRepository.save(createdRoom);
	    		session.setAttribute("room", createdRoom);
	    		//boolean isMasterFlag = true; 5月24日のレビューにて修正
	    		session.setAttribute("isMasterFlag", true);

	    		/*5月24日レビューにて修正 isMasterFlagに変更してマスターかどうかを判定
	    		 * session.setAttribute("roomMasterNickname",(String)session.getAttribute("nickname"));
	    		session.setAttribute("roomMasterImage",(String)session.getAttribute("image"));*/
	    	}else{
	    		//部屋にチャレンジャーIDを入れて、対戦開始状態にする。

	    		//5月24日レビューにて修正 変数名をtergetRoomからchallengeableRoomに変更
	    		//RoomTable tergetRoom = challengebleRooms.get(0);
	    		RoomTable challengeableRoom = challengeableRooms.get(0);
	    		//5月24日レビューにて修正 変数名をtergetRoomからchallengeableRoomに変更
	    		challengeableRoom.setChallengerId(userId);
	    		//5月24日レビューにて修正 変数名をtergetRoomからchallengeableRoomに変更
	    		challengeableRoom.setRoomState(State.MIDST);
	    		Date currentTime = new Date();
	    		//5月24日レビューにて修正 変数名をtergetRoomからchallengeableRoomに変更
	    		challengeableRoom.setBattleStartTime(currentTime);
	    		roomTableRepository.save(challengeableRoom);
	    		//5月24日レビューにて修正 変数名をtergetRoomからchallengeableRoomに変更
	    		session.setAttribute("room", challengeableRoom);
	    		//boolean isMasterFlag = false;
	    		session.setAttribute("isMasterFlag", false);
	    		/*5月24日レビューにて指摘 isMasterFlagに変更してマスターかどうかを判定
	    		 * session.setAttribute("challengerNickname",(String)session.getAttribute("nickname"));
	    		session.setAttribute("challengerImage",(String)session.getAttribute("image"));*/
	    	}
    	}catch(Exception e){
    		session.invalidate();//セッション破棄
    		model.addAttribute("errorMessage",Error.DATABASEERROR);
    		return "/error";
    	}
        return "5_othello";
    }

}