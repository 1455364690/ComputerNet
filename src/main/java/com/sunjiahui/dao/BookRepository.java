package com.sunjiahui.dao;

import com.sunjiahui.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

/**
 * Created by 孙加辉 on 2018/7/1.
 */
@Repository("bookDao")
public interface BookRepository extends CrudRepository<Book, Integer> {
    LinkedList<Book> findAll();
    //Book findByBookId(Integer bookId);
    Book findByBsdn(String bsdn);
    void deleteByBsdn(String bsdn);
}
