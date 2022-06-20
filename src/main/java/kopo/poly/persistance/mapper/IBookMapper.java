package kopo.poly.persistance.mapper;

import kopo.poly.dto.BookDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBookMapper {

    void InsertBook(BookDTO pDTO) throws Exception;

    BookDTO getBookInfo(BookDTO pDTO) throws Exception;

    BookDTO getBookSeq(BookDTO pDTO) throws Exception;

    BookDTO getUserExists(BookDTO pDTO) throws Exception;

    void deleteBook(BookDTO pDTO) throws Exception;
}
