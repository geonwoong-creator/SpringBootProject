package kopo.poly.controller;

import kopo.poly.dto.MapDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IMainService;
import kopo.poly.service.IMapService;
import kopo.poly.service.IProductService;
import kopo.poly.service.IS3Service;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/*
 * Controller 선언해야만 Spring 프레임워크에서 Controller인지 인식 가능
 * 자바 서블릿 역할 수행
 *
 * slf4j는 스프링 프레임워크에서 로그 처리하는 인터페이스 기술이며,
 * 로그처리 기술인 log4j와 logback과 인터페이스 역할 수행함
 * 스프링 프레임워크는 기본으로 logback을 채택해서 로그 처리함
 * */
@Slf4j
@Controller
public class ProductController {

    /*

     * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
     */
    @Resource(name = "ProductService")
    private IProductService productService;
    @Resource(name = "S3Service")
    private  IS3Service s3Service;

    @Resource(name = "MapService")
    private IMapService mapService;

    @Resource(name = "MainService")
    private IMainService mainService;

    /**
     * 게시판 리스트 보여주기
     *
     * GetMapping(value = "notice/NoticeList") =>  GET방식을 통해 접속되는 URL이 notice/NoticeList인 경우 아래 함수를 실행함
     */
    @GetMapping(value = "product/ProductList")
    public String ProductList(ModelMap model, HttpSession session)
            throws Exception {

        String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUser_id(Userid);

        UserInfoDTO uDTO = mainService.getUserInfo(pDTO);


        model.addAttribute("uDTO", uDTO);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ProductList start!");


        // 공지사항 리스트 가져오기
        List<ProductDTO> rList = productService.getProductList();
        log.info("rList : " + rList);
        if (rList == null) {
            rList = new ArrayList<>();

        }

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".ProductList end!");

        // 함수 처리가 끝나고 보여줄 JSP 파일명(/WEB-INF/view/notice/NoticeList.jsp)
        return "/product/ProductList";

    }





    /**
     * 게시판 작성 페이지 이동
     *
     * 이 함수는 게시판 작성 페이지로 접근하기 위해 만듬. WEB-INF 밑에 존재하는 JSP는 직접 호출할 수 없음 따라서 WEB-INF 밑에
     * 존재하는 JSP를 호출하기 위해서는 반드시 Controller 등록해야함
     *
     * GetMapping(value = "notice/NoticeReg") =>  GET방식을 통해 접속되는 URL이 notice/NoticeReg인 경우 아래 함수를 실행함
     */
    @GetMapping(value = "product/ProductReg")
    public String ProductReg(ModelMap model) {

        log.info(this.getClass().getName() + ".ProductReg start!");

        log.info(this.getClass().getName() + ".ProductReg end!");
        //행정번호
        log.info(this.getClass().getName() + ".getMapInfo start");

        try {
            MapDTO pDTO = new MapDTO();

            List<MapDTO> rList = mapService.getMapInfo(pDTO);

            model.addAttribute("rList", rList);
        }catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            log.info(this.getClass().getName() + ".getMapinfo End!");
        }

        log.info(this.getClass().getName() + ".getMapinfo End");

        return "/product/ProductReg";
    }

    /**
     * 게시판 글 등록
     */
    @PostMapping(value = "product/ProductInsert")
    public String NoticeInsert(HttpSession session, HttpServletRequest request, ModelMap model, @RequestPart MultipartFile files) {

        log.info(this.getClass().getName() + ".ProductInsert start!");

        String msg = "";



        try {
            /*
             * 게시판 글 등록되기 위해 사용되는 form객체의 하위 input 객체 등을 받아오기 위해 사용함
             */
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String product_name = CmmUtil.nvl(request.getParameter("product_name")); // 제목
            String addr = CmmUtil.nvl(request.getParameter("addr"));
            String price = CmmUtil.nvl(request.getParameter("price")); //가격
            String mCome = CmmUtil.nvl(request.getParameter("mCome"));// 공지글 여부
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용
            String filename = files.getOriginalFilename();
            String filenameExtension = FilenameUtils.getExtension(filename).toLowerCase();
            File destinationFile;
            String destinationFileName = null;
            String fileUrl = s3Service.uploadImageInS3(files, "upload");

            /*
             * ####################################################################################
             * 반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함 반드시 작성할 것
             * ####################################################################################
             */
            log.info("user_id : " + user_id);
            log.info("product_name : " + product_name);
            log.info("addr : " + addr);
            log.info("contents : " + contents);
            log.info("price : " + price);

            ProductDTO pDTO = new ProductDTO();


            do {
                destinationFileName  = RandomStringUtils.randomAlphanumeric(32) + "." + filenameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            }while (destinationFile.exists());

            destinationFile.getParentFile().mkdir();
            files.transferTo(destinationFile);

            pDTO.setUser_id(user_id);
            pDTO.setProduct_name(product_name);
            pDTO.setAddr(addr);
            pDTO.setMcoed(mCome);
            pDTO.setPrice(price);
            pDTO.setContents(contents);
            pDTO.setFilename(destinationFileName);
            pDTO.setFileoriname(filename);
            pDTO.setFileurl(fileUrl);


            /*
             * 게시글 등록하기위한 비즈니스 로직을 호출
             */
            productService.InsertProduct(pDTO);

            // 저장이 완료되면 사용자에게 보여줄 메시지
            msg = "등록되었습니다.";


        } catch (Exception e) {

            // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".NoticeInsert end!");

            // 결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/product/MsgToList";
    }


    /**
     * 게시판 상세보기
     */
    @GetMapping(value = "product/ProductInfo")
    public String ProductInfo(HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".ProductInfo start!");

        String msg = "";

        try {
            /*
             * 게시판 글 등록되기 위해 사용되는 form객체의 하위 input 객체 등을 받아오기 위해 사용함
             */
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 공지글번호(PK)

            /*
             * ####################################################################################
             * 반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함 반드시 작성할 것
             * ####################################################################################
             */
            log.info("nSeq : " + nSeq);

            /*
             * 값 전달은 반드시 DTO 객체를 이용해서 처리함 전달 받은 값을 DTO 객체에 넣는다.
             */
            ProductDTO pDTO = new ProductDTO();
            pDTO.setProduct_seq(nSeq);

            // 공지사항 상세정보 가져오기
            ProductDTO rDTO = productService.getProduct(pDTO);
            log.info("rDTO " + rDTO.getProduct_seq());
            log.info("rDTO " + rDTO.getProduct_seq());
            if (rDTO == null) {
                rDTO = new ProductDTO();

            }

            log.info("getProductInfo success!!!");

            // 조회된 리스트 결과값 넣어주기
            model.addAttribute("rDTO", rDTO);


        } catch (Exception e) {

            // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".NoticeInsert end!");

            // 결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        log.info(this.getClass().getName() + ".ProductInfo end!");

        return "/product/ProductInfo";
    }

    /**
     * 게시판 수정 보기
     */
    @GetMapping(value = "product/ProductEditInfo")
    public String ProductEditInfo(HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".ProductEditInfo start!");

        String msg = "";

        try {

            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 공지글번호(PK)

            log.info("nSeq : " + nSeq);

            ProductDTO pDTO = new ProductDTO();

            pDTO.setProduct_seq(nSeq);

            /*
             * ####################################################### 공지사항 수정정보 가져오기(상세보기
             * 쿼리와 동일하여, 같은 서비스 쿼리 사용함)
             * #######################################################
             */
            ProductDTO rDTO = productService.getProduct(pDTO);

            if (rDTO == null) {
                rDTO = new ProductDTO();

            }

            // 조회된 리스트 결과값 넣어주기
            model.addAttribute("rDTO", rDTO);

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".NoticeUpdate end!");

            // 결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        log.info(this.getClass().getName() + ".ProductEditInfo end!");

        return "/product/ProductEditInfo";
    }

    /**
     * 게시판 글 수정
     */
    @PostMapping(value = "product/ProductUpdate")
    public String NoticeUpdate(HttpSession session, HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".NoticeUpdate start!");

        String msg = "";

        try {

            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 아이디
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 글번호(PK)
            String product_name = CmmUtil.nvl(request.getParameter("product_name")); // 제목
            String addr = CmmUtil.nvl(request.getParameter("addr")); // 공지글 여부
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용
            String price = CmmUtil.nvl(request.getParameter("price"));//가격

            log.info("user_id : " + user_id);
            log.info("nSeq : " + nSeq);
            log.info("product_name : " + product_name);
            log.info("addr : " + addr);
            log.info("contents : " + contents);
            log.info("price : " + price);

            ProductDTO pDTO = new ProductDTO();

            pDTO.setUser_id(user_id);
            pDTO.setProduct_seq(nSeq);
            pDTO.setProduct_name(product_name);
            pDTO.setAddr(addr);
            pDTO.setContents(contents);
            pDTO.setPrice(price);
            // 게시글 수정하기 DB
            productService.updateProduct(pDTO);

            msg = "수정되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".ProductUpdate end!");

            // 결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/product/MsgToList";
    }

    /**
     * 게시판 글 삭제
     */
    @GetMapping(value = "product/ProductDelete")
    public String NoticeDelete(HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".ProductDelete start!");

        String msg = "";

        try {

            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 글번호(PK)

            log.info("nSeq : " + nSeq);

            ProductDTO pDTO = new ProductDTO();

            pDTO.setProduct_seq(nSeq);

            // 게시글 삭제하기 DB
            productService.deleteProduct(pDTO);

            msg = "삭제되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".ProductDelete end!");

            // 결과 메시지 전달하기
            model.addAttribute("msg", msg);

        }

        return "/product/MsgToList";
    }

}
