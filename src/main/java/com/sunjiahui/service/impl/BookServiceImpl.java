package com.sunjiahui.service.impl;

import com.sunjiahui.dao.BookRepository;
import com.sunjiahui.model.Book;
import com.sunjiahui.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

/**
 * Created by 孙加辉 on 2018/7/1.
 */
@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookDao;

    //添加书目
    public Book addBook(Book book) {
        bookDao.save(book);
        return null;
    }

    //通过BSDN查询书目
    public Book getBookByBsdn(String Bsdn) {
        return bookDao.findByBsdn(Bsdn);
    }

    //删除书目
    public void deleteByBsdn(String bsdn) {
        bookDao.deleteByBsdn(bsdn);
    }

    //修改书目的数量，如果不足0则返回false
    public boolean modifyBookNumByBsdn(String bsdn, Integer number) {
        boolean flag = true;
        Book book = bookDao.findByBsdn(bsdn);
        int oldNum = book.getNumber();
        int newNum = oldNum + number;
        if (newNum < 0) {
            flag = false;
        } else {
            book.setNumber(newNum);
            bookDao.save(book);
        }
        return flag;
    }

    public LinkedList<Book> getAllBook() {
        return bookDao.findAll();
    }
}