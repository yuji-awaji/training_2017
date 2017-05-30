package jp.com.xpower.app2017.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SelectUserTable {
	@Autowired
	UserTableRepository userTableRepository;
	//ユーザーテーブルを取得する
    public UserTable selectUserTable(String userId){
    	//userIdにひもづけた情報を取得
        List<UserTable> selectList = userTableRepository.findByUserId(userId);
        //一個目を保存
        UserTable userTable = selectList.get(0);
        return userTable;
    }
}
