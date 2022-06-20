package kopo.poly.service;

import kopo.poly.dto.BookDTO;

public interface IBookService {

    void InsertBook(BookDTO pDTO) throws Exception;

    BookDTO getBookInfo(BookDTO pDTO) throws Exception;

    BookDTO getBookSeq(BookDTO pDTO) throws Exception;

    void deleteBook(BookDTO pDTO) throws Exception;
}
