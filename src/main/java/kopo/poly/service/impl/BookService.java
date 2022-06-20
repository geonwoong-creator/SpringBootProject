package kopo.poly.service.impl;

import kopo.poly.dto.BookDTO;
import kopo.poly.persistance.mapper.IBookMapper;
import kopo.poly.service.IBookService;
import kopo.poly.util.CmmUtil;
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

        BookDTO rDTO = bookMapper.getUserExists(pDTO);

        if (rDTO == null) {
            rDTO = new BookDTO();
        }

        if (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")) {
            throw new RuntimeException("msg");
        } else {

            bookMapper.InsertBook(pDTO);
        }
    }

    @Override
    public BookDTO getBookInfo(BookDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBookInfo start!");

        return bookMapper.getBookInfo(pDTO);
    }

    @Override
    public BookDTO getBookSeq(BookDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".chatroom create!");

        return bookMapper.getBookSeq(pDTO);
    }

    @Transactional
    @Override
    public void deleteBook(BookDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".deleteNoticeInfo start!");

        bookMapper.deleteBook(pDTO);

    }
}
