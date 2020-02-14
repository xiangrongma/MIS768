package Api;

import Repository.Author;
import Repository.Book;

import java.util.List;

public interface BookApi {

    List<Book> getBooks();

    List<Author> getAuthors();
    void downloadBooks();
}
