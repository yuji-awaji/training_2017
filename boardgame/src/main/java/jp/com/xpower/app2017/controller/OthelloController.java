package jp.com.xpower.app2017.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.com.xpower.app2017.model.BoardGameConstant.Error;
import jp.com.xpower.app2017.model.BoardGameConstant.State;
import jp.com.xpower.app2017.model.OthelloInfo;
import jp.com.xpower.app2017.model.RoomTable;
import jp.com.xpower.app2017.model.RoomTableRepository;
import jp.com.xpower.app2017.model.SelectUserTable;
import jp.com.xpower.app2017.model.UserTable;
import jp.com.xpower.app2017.model.UserTableRepository;

@Controller
public class OthelloController {

	@Autowired
	RoomTableRepository roomTableRepository;
	@Autowired
	SelectUserTable userTable;
	@Autowired
	UserTableRepository userTableRepository;


	@RequestMapping(value = "/redirectmenu", method = RequestMethod.GET)
	public String redirectMenu(HttpSession session) {
		//バグ：セッションを削除していなかった
		session.removeAttribute("room");
		session.removeAttribute("isMasterFlag");
		// メニュー画面へリダイレクト
		return "redirect:/menu";
	}

	@RequestMapping(value = "/redirecterror", method = RequestMethod.GET)
	public String redirectError(HttpSession session, Model model) {
		model.addAttribute("errorMessage", Error.DATABASEERROR);
		session.removeAttribute("room");
		session.removeAttribute("isMasterFlag");
		return "/error";
	}

	@RequestMapping(value = "/checkroomstate", method = RequestMethod.POST, headers = {
			"Content-type=application/json,application/xml" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> checkRoomState(@RequestBody String inputdata, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// セッションからroomを取得
		RoomTable getRoom = (RoomTable) session.getAttribute("room");
		// DBから現在のroomを取得
		RoomTable checkedRoom = OthelloInfo.getCheckRoom(getRoom.getRoomId(), roomTableRepository);
		// セッションに詰めなおす
		session.setAttribute("room", checkedRoom);

		// マスターフラグをセッションから取得
		boolean isMasterFlag = (boolean) session.getAttribute("isMasterFlag");

		String nickname = null;
		String encoded = null;
		String fileType = null;

		HashMap<String, String> opponentData = new HashMap<>();

		// user情報を表示するために、json形式に変更
		// 0の場合対戦待機中
		if (checkedRoom.getRoomState() == 0) {
			opponentData.put("roomState", "0");
			// 1の場合対戦を開始する
		} else if (checkedRoom.getRoomState() == 1) {
			//session.setAttribute("room", checkedRoom);
			UserTable userTable;
			if (isMasterFlag == true) {
				userTable = OthelloInfo.getUser(checkedRoom.getChallengerId(), userTableRepository);
			} else {
				userTable = OthelloInfo.getUser(checkedRoom.getRoomMasterId(), userTableRepository);
			}
			nickname = userTable.getNickname();
			encoded = Base64.getEncoder().encodeToString(userTable.getProfileImage());
			fileType = userTable.getFileType();
			opponentData.put("roomState", "1");
			// 2の場合メニュー画面へリダイレクト
		} else {
			opponentData.put("roomState", "2");
		}
		opponentData.put("userNickname", nickname);
		opponentData.put("userImage", encoded);
		opponentData.put("imageFileType", fileType);
		return opponentData;
	}

	@ResponseBody
	@RequestMapping(value = "/endgame", method = RequestMethod.POST, headers = {
			"Content-type=application/json,application/xml" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String endGame(@RequestBody String data, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");

		// セッションからroomを取ってくる
		RoomTable invalidRoom = (RoomTable) session.getAttribute("room");
		// roomstateを2（対戦終了状態）でセッションに保存
		invalidRoom.setRoomState(State.END);
		try{
			// ゲーム終了処理roomStateを2（対戦終了状態）に変える
			OthelloInfo.endRoomState(invalidRoom, roomTableRepository);
		}catch(Exception e){
			return data;
		}


		try{
			ObjectMapper mapper = new ObjectMapper();
	        JsonNode json = mapper.readTree(data);
	        String result = json.get("result").asText();

	        UserTable userTable = OthelloInfo.getUser(userId, userTableRepository);
	        if(result.equals("win")){
	        	int win = userTable.getWin();
		        userTable.setWin(win+1);
	        }else if(result.equals("lose")){
	        	int lose = userTable.getLose();
		        userTable.setLose(lose+1);
	        }else{
	        	int draw = userTable.getDraw();
	        	userTable.setDraw(draw+1);
	        }
	        userTableRepository.save(userTable);

		}catch(Exception e){
			return data;
		}
		// roomとMasterFlagのセッションを削除
		session.removeAttribute("room");
		session.removeAttribute("isMasterFlag");

		return null;
	}


}
