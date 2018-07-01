package com.sunjiahui.controller;

import com.sunjiahui.model.Book;
import com.sunjiahui.service.BookService;
import com.sunjiahui.service.impl.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by 孙加辉 on 2018/7/1.
 * 书目控制类
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Value("${storage.upload-dir}")
    private String filePath;

    //获取所有书目
    @GetMapping("/getAllBooks")
    @ResponseBody
    public HashMap<String, Object> getAllBooks() {
        HashMap<String, Object> map = new HashMap<>();
        LinkedList<Book> books = null;
        try {
            books = bookService.getAllBook();
        } catch (Exception e) {
            map.put("code", "2");
            map.put("message", e.toString());
            return map;
        }

        if (books == null || books.size() == 0) {
            map.put("code", "3");
            map.put("message", "the library is empty");
            return map;
        }

        map.put("code", "1");
        map.put("books", books);
        return map;
    }

    @PostMapping("/addBook")
    @ResponseBody
    public HashMap<String, Object> addBook(@RequestParam String bookName,
                                           @RequestParam String bsdn,
                                           @RequestParam String author,
                                           @RequestParam Integer number,
                                           @RequestParam String price,
                                           @RequestParam Timestamp publishTime,
                                           @RequestParam String note,
                                           @RequestParam MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        Book book = new Book();

        //存储图片，图片名为对应的bsdn
        String oldName = file.getOriginalFilename();
        String[] names = oldName.split("\\.");
        String fileName = bsdn + "." + names[names.length - 1];
        //System.out.println(contentType+","+fileName+","+filePath);
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            map.put("code", "4");
            map.put("message", "add:failure:" + e.toString());
            return map;
        }

        book.setBookName(bookName);
        book.setBsdn(bsdn);
        book.setAuthor(author);
        book.setNumber(number);
        book.setPrice(price);
        book.setImg(fileName);
        book.setPublishTime(publishTime);
        book.setAddTime(currTime);
        book.setNote(note);

        try {
            bookService.addBook(book);
        } catch (Exception e) {
            map.put("code", "2");
            map.put("message", "add:failure:" + e.toString());
            return map;
        }
        map.put("code", "1");
        map.put("message", "add:success");
        return map;
    }

    //删除书目
    @PostMapping("/deleteBook")
    @ResponseBody
    public HashMap<String, Object> deleteBook(@RequestParam String bsdn) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            bookService.deleteByBsdn(bsdn);
        } catch (Exception e) {
            map.put("code", "2");
            map.put("message", "delete:failure:" + e.toString());
            return map;
        }
        map.put("code", "1");
        map.put("message", "delete:success");
        return map;
    }

    //修改书目数量
    @PostMapping("/modifyBook")
    @ResponseBody
    public HashMap<String, Object> modifyBook(@RequestParam String bsdn, @RequestParam Integer number) {
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = true;
        try {
            flag = bookService.modifyBookNumByBsdn(bsdn, number);
        } catch (Exception e) {
            map.put("code", "2");
            map.put("message", "modify:failure:" + e.toString());
        }
        if (!flag) {
            map.put("code", "3");
            map.put("message", "modify:failure:库存不足");
            return map;
        }
        map.put("code", "1");
        map.put("message", "modify:success");
        return map;
    }

    //通过Bsdn查询书目
    @PostMapping("/getBookByBsdn")
    @ResponseBody
    public HashMap<String, Object> getBookById(@RequestParam String bsdn) {
        HashMap<String, Object> map = new HashMap<>();
        Book book = null;
        try {
            book = bookService.getBookByBsdn(bsdn);
        } catch (Exception e) {
            map.put("code", "2");
            map.put("message", "getBookById:failure:" + e.toString());
            return map;
        }
        if (book == null) {
            map.put("code", "3");
            map.put("message", "the result is empty");
            return map;
        }

        map.put("code", "1");
        map.put("message", "getBookById:success");
        map.put("book", book);
        return map;
    }


}
