package kopo.poly.controller;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IMainService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MainController {

    @Resource(name = "MainService")
    private IMainService mainService;

    @RequestMapping(value = "main/main")
    public void mainPageGET() {
        log.info(".MainPage Start!");
    }

    @RequestMapping(value = "/user/Logout")
    public void UserLogout() {
        log.info(".UserLogout");
    }
    //
    @RequestMapping(value = "/main/UserSetting")
    public String UserInfo(HttpSession session, ModelMap model) {

        log.info(this.getClass().getName() + ".UserSetting");

        try {
            String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            log.info("SS_USER_ID : " + Userid);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUser_id(Userid);



            UserInfoDTO rDTO = mainService.getUserInfo(pDTO);
            String encEmail = rDTO.getEmail();
            String email = EncryptUtil.decAES128CBC(encEmail);
            rDTO.setEmail(email);

            if (rDTO == null) {
                rDTO = new UserInfoDTO();
            }

            log.info("getUserInfo success!!");

            model.addAttribute("rDTO", rDTO);
         } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".getUserInfo END!!");
        }

        log.info(this.getClass().getName() + ".UserSetting End!!");

        return "/main/UserSetting";
    }




}
