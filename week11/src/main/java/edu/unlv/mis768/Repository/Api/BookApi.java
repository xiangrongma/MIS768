package edu.unlv.mis768.Repository.Api;

import edu.unlv.mis768.Repository.Author;
import edu.unlv.mis768.Repository.Book;

import java.util.List;

public interface BookApi {

    List<Book> getBooks();
    Book getBook(Long bookid);

    List<Author> getAuthors();
    void downloadBooks();
}
