package Api;

import Repository.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BookApiImpTest {

  @Test
  void genPathFromId() {
      assertEquals( "/1/2/3/4/5/123456" , BookApiImp.genPathFromId(123456));
      assertEquals( "/1/2/3/4/12345"    , BookApiImp.genPathFromId(12345));
      assertEquals( "/1/2/3/1234"       , BookApiImp.genPathFromId(1234));
      assertEquals( "/1/2/123"          , BookApiImp.genPathFromId(123));
      assertEquals( "/1/12"             , BookApiImp.genPathFromId(12));
      assertEquals( "/1"                , BookApiImp.genPathFromId(1));
  }

  @Test
  void genDownloadList() {
      BookApiImp apiImp = new BookApiImp();
      String[] downloadList = apiImp.genDownloadList().toArray(new String[0]);
//      assertEquals("", downloadList[0]);
      assertEquals("", downloadList[12121]);

  }

    @Test
    void testGenPathFromId() {
    }

    @Test
    void main() {
    }

    @Test
    void testGenDownloadList() {
    }

    @Test
    void downloadBooks() {
    }

    @Test
    void getBooks() {
        BookApiImp apiImp = new BookApiImp();
        final List<Book> books = apiImp.getBooks();
        assertEquals(books.get(100).title , "The Damnation of Theron Ware");
        assertEquals(books.get(100).Id , 133);

    }

    @Test
    void getBook() {
        BookApiImp apiImp = new BookApiImp();
        final List<Book> books = apiImp.getBooks();
        final Book tdtw = apiImp.getBook(133); //== .equals
        assertEquals(tdtw.title , "The Damnation of Theron Ware");

    }

    @Test
    void getAuthors() {
    }
}