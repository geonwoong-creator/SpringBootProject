package kopo.poly.service.impl;

import kopo.poly.dto.CommentDTO;
import kopo.poly.persistance.mapper.ICommentMapper;
import kopo.poly.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("CommentService")
public class CommentService implements ICommentService {

    private final ICommentMapper commentMapper;

    @Autowired
    public CommentService(ICommentMapper commentMapper) {this.commentMapper = commentMapper;}

    @Override
    public List<CommentDTO> commentList() throws Exception {

        log.info(this.getClass().getName() + ".getCommentList Start!");

        return commentMapper.commentList();
    }

   @Transactional
    @Override
    public int commentInsert(CommentDTO comment) throws Exception {

        log.info(this.getClass().getName() + ".commentInset Start!");

        return commentMapper.commentInsert(comment);
   }

   @Transactional
    @Override
    public int commentUpdate(CommentDTO comment) throws Exception {

        log.info(this.getClass().getName() + ".commentUpdate start!");

        return commentMapper.commentUpdate(comment);
   }

   @Transactional
    @Override
    public int commentDelete(int cno) throws Exception {

        log.info(this.getClass().getName() + ".commentDelete Start!");

        return commentMapper.commentDelete(cno);
   }

}
