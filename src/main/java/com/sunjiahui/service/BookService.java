package com.sunjiahui.service;

import com.sunjiahui.model.Book;

import java.util.LinkedList;

/**
 * Created by 孙加辉 on 2018/7/1.
 */
public interface BookService {
    Book addBook(Book book);
    Book getBookByBsdn(String BSDN);
    void deleteByBsdn(String bsdn);
    boolean modifyBookNumByBsdn(String Bsdn,Integer number);
    LinkedList<Book> getAllBook();
}
