package kopo.poly.service.impl;

import kopo.poly.dto.BookDTO;
import kopo.poly.persistance.mapper.IBookMapper;
import kopo.poly.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("BookService")
public class BookService  implements IBookService {

    private final IBookMapper bookMapper;

    @Autowired
    public BookService(IBookMapper bookMapper) {this.bookMapper = bookMapper;}

    @Transactional
    @Override
    public void InsertBook(BookDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".InsertBook start!");

        bookMapper.InsertBook(pDTO);
    }

    @Override
    public BookDTO getBookInfo(BookDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBookInfo start!");

        return bookMapper.getBookInfo(pDTO);
    }
}
