package jp.com.xpower.app2017.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@ResponseBody
	@RequestMapping(value = "/endgame", method = RequestMethod.POST, headers = {"Content-type=application/json,application/xml"},consumes = MediaType.APPLICATION_JSON_VALUE)
	//5/25指摘：意味のないリターンをやめる
	public void endGame(@RequestBody String data, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		//セッションからroomを取ってくる
		RoomTable invalidRoom = (RoomTable) session.getAttribute("room");
		//ゲーム終了処理roomStateを2（対戦終了状態）に変える
		invalidRoom.setRoomState(State.END);
		roomTableRepository.save(invalidRoom);
		//roomとMasterFlagのセッションを削除
		session.removeAttribute("room");
		session.removeAttribute("isMasterFlag");
    }

	@RequestMapping(value = "/redirectmenu", method = RequestMethod.GET)
	  public String redirectMenu() {
		//メニュー画面へリダイレクト
		return "redirect:/menu";
	  }

	@RequestMapping(value = "/checkroomstate", method = RequestMethod.POST, headers = {"Content-type=application/json,application/xml"},consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> checkRoomState(@RequestBody String inputdata ,HttpServletRequest request, HttpServletResponse response, HttpSession session){
		RoomTable getRoom = (RoomTable) session.getAttribute("room");
		//5/25指摘：サービスクラスに分ける
		//getRoom.getRoomId()を引数にしてcheckedRoomをリターン
		//RoomTable checkedRoom = roomTableRepository.findOne(getRoom.getRoomId());
		OthelloInfo othelloinfo = new OthelloInfo();
		RoomTable checkedRoom = othelloinfo.getCheckRoom(getRoom.getRoomId(), roomTableRepository);
		//5/25指摘：チャレンジャーと対戦開始時刻がnullになる
		//セッションに詰めなおして解決
		session.setAttribute("room", checkedRoom);

		//マスターフラグをセッションから取得
		boolean isMasterFlag = (boolean) session.getAttribute("isMasterFlag");

		String nickname = null;
		String encoded = null;
		String fileType = null;

		//5/25指摘：RoomStateをそのまま返せばよいのでは
		//user情報を表示するために、json形式に変更
		//0の場合対戦待機中
		if(checkedRoom.getRoomState() == 0 ){
			HashMap<String, String> userData = new HashMap<>();
			userData.put("roomState", "0");
			userData.put("userNickname",nickname);
			userData.put("userImage", encoded);
			userData.put("imageFileType", fileType);
			return userData;
		//1の場合対戦を開始する
		}else if(checkedRoom.getRoomState() == 1){
			session.setAttribute("room", checkedRoom);
			if(isMasterFlag == true && checkedRoom.getChallengerId() != null){
				//userIdにひもづけた情報を取得
		        List<UserTable> selectList = userTableRepository.findByUserId(checkedRoom.getChallengerId());
		        //一個目を保存
		        UserTable userTable = selectList.get(0);
				nickname = userTable.getNickname();
				encoded = Base64.getEncoder() .encodeToString(userTable.getProfileImage());
				fileType = userTable.getFileType();
			}else if(isMasterFlag == false && checkedRoom.getChallengerId() != null){
				//userIdにひもづけた情報を取得
		        List<UserTable> selectList = userTableRepository.findByUserId(checkedRoom.getRoomMasterId());
		        //一個目を保存
		        UserTable userTable = selectList.get(0);
				nickname = userTable.getNickname();
				encoded = Base64.getEncoder() .encodeToString(userTable.getProfileImage());
				fileType = userTable.getFileType();
			}
			HashMap<String, String> userData = new HashMap<>();
			userData.put("roomState", "1");
			userData.put("userNickname",nickname);
			userData.put("userImage", encoded);
			userData.put("imageFileType", fileType);
			return userData;
			//2の場合メニュー画面へリダイレクト
		}else{
			HashMap<String, String> userData = new HashMap<>();
			userData.put("roomState", "2");
			userData.put("userNickname",nickname);
			userData.put("userImage", encoded);
			userData.put("imageFileType", fileType);
			return userData;
		}
	}

}
