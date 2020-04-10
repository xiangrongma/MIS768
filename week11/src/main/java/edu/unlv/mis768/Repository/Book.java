package edu.unlv.mis768.Repository;

//POJO
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long Id;

    public String title;
    public String author;
    public String subTitle;

    public Book(String a, String t) {
        this.author = a;
        this.title = t;
//        this.subTitle
    }

    @Override
    public String toString() {
        return "{ Id: "+ Id + ", title: "+ title + ", author: "+ author+ ", subTitle" + subTitle +"}";
    }
    public Book() {}

}
