package kopo.poly.controller;

import kopo.poly.dto.BookDTO;
import kopo.poly.service.IBookService;
import kopo.poly.util.CmmUtil;
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

    @PostMapping(value = "product/Book")
    public String InsertBoot(HttpSession session, HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".InsertBook start!");

        String msg="";

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String seller = CmmUtil.nvl(request.getParameter("seller"));
            String product_name = CmmUtil.nvl(request.getParameter("product_name"));
            String product_addr = CmmUtil.nvl(request.getParameter("product_addr"));

            log.info("user_id : " + user_id);
            log.info("seller : " + seller);
            log.info("product_name : " + product_name);
            log.info("product_addr : " + product_addr);


            BookDTO pDTO = new BookDTO();
            pDTO.setUser_id(user_id);
            pDTO.setSeller(seller);
            pDTO.setProduct_name(product_name);
            pDTO.setProduct_addr(product_addr);

            bookService.InsertBook(pDTO);

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
