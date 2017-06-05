package jp.com.xpower.app2017.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.com.xpower.app2017.model.OthelloInfo;
import jp.com.xpower.app2017.model.UserTable;
import jp.com.xpower.app2017.model.UserTableRepository;


@Controller
public class TestController {
	@Autowired
	UserTableRepository userTableRepository;
	@RequestMapping("/test")
	public String test(){
		UserTable gotUser = OthelloInfo.getUser("aaaa", userTableRepository);
		return "/test";
	}
}
