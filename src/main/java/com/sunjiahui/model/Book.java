package com.sunjiahui.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 孙加辉 on 2018/7/1.
 */
@Entity
@Table(name = "books")
public class Book {

    @Column(name = "book_name")
    private String bookName;

    @Id
    @Column(name = "bsdn")
    private String bsdn;

    @Column(name = "author")
    private String author;

    @Column(name = "number")
    private Integer number;

    @Column(name = "price")
    private String price;

    @Column(name = "publish_time")
    private Timestamp publishTime;

    @Column(name = "add_time")
    private Timestamp addTime;

    @Column(name = "note")
    private String note;

    public Book() {
    }

    public Book(String bookName, String bsdn, String author, Integer number, String price, Timestamp publishTime, Timestamp addTime, String note) {
        this.bookName = bookName;
        this.bsdn = bsdn;
        this.author = author;
        this.number = number;
        this.price = price;
        this.publishTime = publishTime;
        this.addTime = addTime;
        this.note = note;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBsdn() {
        return bsdn;
    }

    public void setBsdn(String bsdn) {
        this.bsdn = bsdn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
