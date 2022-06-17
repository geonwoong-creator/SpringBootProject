package kopo.poly.controller;

import kopo.poly.dto.BookDTO;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IBookService;
import kopo.poly.service.IMailService;
import kopo.poly.service.IMainService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class BookController {

    @Resource(name = "BookService")
    private IBookService bookService;

    @Resource(name = "MainService")
    private IMainService mainService;

    @Resource(name = "MailService")
    private IMailService mailService;



    @PostMapping(value = "product/Book")
    public String InsertBoot(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {

        String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        log.info("SS_USER_ID : " + Userid);

        UserInfoDTO uDTO = new UserInfoDTO();
        uDTO.setUser_id(Userid);



        UserInfoDTO rDTO = mainService.getUserInfo(uDTO);
        String encEmail = rDTO.getEmail();



        log.info(this.getClass().getName() + ".InsertBook start!");

        String msg="";

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String seller = CmmUtil.nvl(request.getParameter("seller"));
            String product_name = CmmUtil.nvl(request.getParameter("product_name"));
            String product_addr = CmmUtil.nvl(request.getParameter("product_addr"));
            String product_mcoed = CmmUtil.nvl(request.getParameter("product_mcoed"));
            String product_price = CmmUtil.nvl(request.getParameter("product_price"));
            String pay_type = CmmUtil.nvl(request.getParameter("pay_type"));
            String email = EncryptUtil.decAES128CBC(encEmail);
            log.info("user_id : " + user_id);
            log.info("seller : " + seller);
            log.info("product_name : " + product_name);
            log.info("product_addr : " + product_addr);
            log.info("product_mcoed : " + product_mcoed);
            log.info("product_price : " + product_price);
            log.info("pay_type : " + pay_type);
            log.info("email : " + email);


            BookDTO pDTO = new BookDTO();
            pDTO.setUser_id(user_id);
            pDTO.setSeller(seller);
            pDTO.setProduct_name(product_name);
            pDTO.setProduct_addr(product_addr);
            pDTO.setProduct_mcoed(product_mcoed);
            pDTO.setProduct_price(product_addr);
            pDTO.setPay_type(pay_type);
            pDTO.setUser_email(email);
            bookService.InsertBook(pDTO);

            MailDTO mDTO = new MailDTO();
            mDTO.setToMail(email);
            mDTO.setTitle("결제완료");
            mDTO.setContents("주소 : " + product_addr + " 장소 이름 : " + product_name + " 가격 : " + product_price + " 결제 방법 : " + pay_type + " 결재가 완료 되었습니다." );

            mailService.doSendMail(mDTO);


            msg = "예약되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".InsertBook End!");

            model.addAttribute("msg", msg);
        }

        return "/product/MsgToList";
    }

    @RequestMapping(value = "/product/BookResult")
    public String BookInfo(HttpSession session, ModelMap model) {

        log.info(this.getClass().getName() + ".BookInfo");

        try {
            String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            log.info("SS_USER_ID : " + Userid);

            BookDTO pDTO = new BookDTO();
            pDTO.setUser_id(Userid);

            BookDTO rDTO = bookService.getBookInfo(pDTO);

            log.info("getBookInfo success!!");

            model.addAttribute("rDTO", rDTO);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".getBookInfo END!!");
        }

        log.info(this.getClass().getName() + ".BookResult End!!");

        return "/product/BookResult";
    }
}
