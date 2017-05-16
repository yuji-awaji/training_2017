package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class signup_controller
{

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	@Autowired
	UserTableRepository UserTableRepository;

	@PostMapping("/upload") String upload(@ModelAttribute model.UserInfo userData, Model model) {

		model.UserDataCheck userCheck = new model.UserDataCheck();
		model.ErrorMsgFlag errorFlag = new model.ErrorMsgFlag();

		errorFlag=userCheck.inputCheck(userData);
		if(errorFlag.isId()){
			List<controller.UserTable> selectList = UserTableRepository.findByUserIdContains(userData.getId());
        	if(selectList.isEmpty()){
        		errorFlag.setAlreadyId(true);

        	}
		}
		if(errorFlag.isId()==true && errorFlag.isAlreadyId()==true && errorFlag.isPassword()==true && errorFlag.isRePassword()==true && errorFlag.isNickName()==true && errorFlag.isProfileImageType()==true && errorFlag.isProfileImageSize()==true){
			String extension="jpg";
			BufferedImage image;
			userData.setImageURL("img/user_default.jpg");

			if(userData.getProfileImage().getOriginalFilename().endsWith(".JPG")){
				extension="jpg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".jpg")){
				extension="jpg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".JPEG")){
				extension="jpeg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".jpeg")){
				extension="jpeg";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".BMP")){
				extension="bmp";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".bmp")){
				extension="bmp";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".PNG")){
				extension="png";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".png")){
				extension="png";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith("GIF")){
				extension="gif";
			}else if(userData.getProfileImage().getOriginalFilename().endsWith(".gif")){
				extension="gif";

			}
			if(userData.getProfileImage().isEmpty()){
				model.addAttribute("Image", false);
			}
			else{
				model.addAttribute("Image", true);
				String imageName=userData.getProfileImage().getOriginalFilename();
				try {
					image = ImageIO.read( new File( imageName ) );
					userData.setImageURL("UserImage/"+userData.getId()+"."+extension);
					ImageIO.write(image, extension, new File("./src/main/resources/static/"+userData.getImageURL()));
				}catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMessage", "画像保存エラー");
					//TODO
					return "error";

				}
			}
			String hashPassword= bcrypt.encode(userData.getPassword());

			UserTableRepository.save(new UserTable(userData.getId(),hashPassword,userData.getNickName(), userData.getImageURL()));

			model.addAttribute("UserResult", userData);
			return "2_1_registered";
		}else{
			model.addAttribute("UserInfo", new model.UserInfo());
			model.addAttribute("ErrorFlag", errorFlag);
			return "2_signup";
		}
	}

	@RequestMapping("/signup") // (5)
    public String index(Model model) {// (3)
		model.addAttribute("UserInfo", new model.UserInfo());
		model.addAttribute("ErrorFlag", new model.ErrorMsgFlag(1));
        return "2_signup";
    }
	@RequestMapping(value = "/") // (5)
    public String login(Model model) {// (3)
		model.addAttribute("ErrorFlag", true);
        return "1_login";
    }

}
