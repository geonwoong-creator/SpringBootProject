package kopo.poly.service;

import kopo.poly.dto.CommentDTO;

import java.util.List;

public interface ICommentService {


    List<CommentDTO> commentList() throws Exception;

    int commentInsert(CommentDTO comment) throws Exception;

    int commentUpdate(CommentDTO comment) throws Exception;

    int commentDelete(int cno) throws Exception;
}
