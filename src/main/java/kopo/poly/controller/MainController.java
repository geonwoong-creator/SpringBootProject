package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MainController {

    @RequestMapping(value = "main/main")
    public void mainPageGET() {
        log.info(".MainPage Start!");
    }

    @RequestMapping(value = "/user/Logout")
    public void UserLogout() {
        log.info(".UserLogout");
    }
}
