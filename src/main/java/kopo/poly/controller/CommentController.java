package kopo.poly.controller;

import kopo.poly.dto.CommentDTO;
import kopo.poly.service.ICommentService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource(name = "CommentService")
    private ICommentService commentService;

    @RequestMapping("/list")
    @ResponseBody
    private List<CommentDTO> CommentList(Model model) throws Exception{

        return commentService.commentList();
    }

    @RequestMapping("/insert")
    @ResponseBody
    private int CommentInsert(HttpSession session, @RequestParam String notice_seq, @RequestParam String content) throws Exception{

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setNotice_seq(notice_seq);
        commentDTO.setContent(content);
        commentDTO.setUser_id(CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")));

        return commentService.commentInsert(commentDTO);
    }

    @RequestMapping("/update")
    @ResponseBody
    private int CommentUpdate(@RequestParam String cno, @RequestParam String content) throws Exception {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCno(cno);
        commentDTO.setContent(content);

        return commentService.commentUpdate(commentDTO);
    }

    @RequestMapping("/delete/{cno}")
    @ResponseBody
    private int CommentDelete(@PathVariable int cno) throws Exception {

        return commentService.commentDelete(cno);
    }

}
