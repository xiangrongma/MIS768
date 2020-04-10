package edu.unlv.mis768.Repository;

//POJO
public class Book {
    public String title;
    public String author;
    public Integer Id;
    public String subTitle;

    @Override
    public String toString() {
        return "{ Id: "+ Id + ", title: "+ title + ", author: "+ author+ ", subTitle" + subTitle +"}";
    }
}
