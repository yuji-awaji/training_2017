package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OthelloController {

	@Autowired
	UserTableRepository repository;

	@RequestMapping(value = "/othello", method = RequestMethod.GET)
	//public ModelAndView index(@ModelAttribute UserTable user, ModelAndView mav) {
	public String index(){

		/* roomidをセッションスコープから取ってきて
		 * masterとchallengerのユーザーIDを取得する。
		 * 石の色はmaster：黒、challenger：白
		 */
		//roomidをセッションスコープから取得

		//roomidをもとにユーザー情報を取得
		/*String master = "test1";
		String challenger = "test2";

		//白か黒か石の色情報を付加する

		mav.setViewName("5_othello");
		UserTable[] data = new UserTable[2];
		data[0] = repository.findByuserid(master);
		data[1] = repository.findByuserid(challenger);
		Iterable<UserTable> data = repository.findAll();
		mav.addObject("datalist", data);*/
		//return mav;
		return "5_othello";
    }

	@RequestMapping(value="/othello", method=RequestMethod.POST)
    public ModelAndView test(@ModelAttribute UserTable user, Model model) {
		model.addAttribute("user", user);

		System.out.println(user.getWin());

		return null;

    }
}
