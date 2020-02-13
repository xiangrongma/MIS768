package Api;

import Repository.Author;
import Repository.Book;

import java.util.List;

public interface BookApi {
    static void main(String[] args) {
        BookApi api = new BookApiImp();
        api.getBooks();
    }

    List<Book> getBooks();

    List<Author> getAuthors();
}
