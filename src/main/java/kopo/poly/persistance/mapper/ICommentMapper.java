package kopo.poly.persistance.mapper;

import kopo.poly.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentMapper {

    int commentCount() throws Exception;

    List<CommentDTO> commentList() throws Exception;

    int commentInsert(CommentDTO comment) throws Exception;

    int commentUpdate(CommentDTO comment) throws Exception;

    int commentDelete(int cno) throws Exception;

}
