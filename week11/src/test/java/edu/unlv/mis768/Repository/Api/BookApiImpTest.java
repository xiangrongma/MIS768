package edu.unlv.mis768.Repository.Api;

import edu.unlv.mis768.Repository.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BookApiImpTest {

  @Test
  void genPathFromId() {
      assertEquals( "/1/2/3/4/5/123456" , BookApiImp.genPathFromId(123456L));
      assertEquals( "/1/2/3/4/12345"    , BookApiImp.genPathFromId(12345L));
      assertEquals( "/1/2/3/1234"       , BookApiImp.genPathFromId(1234L));
      assertEquals( "/1/2/123"          , BookApiImp.genPathFromId(123L));
      assertEquals( "/1/12"             , BookApiImp.genPathFromId(12L));
      assertEquals( "/1"                , BookApiImp.genPathFromId(1L));
  }

  @Test
  void genDownloadList() {
      BookApiImp apiImp = new BookApiImp();
      String[] downloadList = apiImp.genDownloadList().toArray(new String[0]);
//      assertEquals("", downloadList[0]);
      assertEquals("https://www.mirrorservice.org/sites/ftp.ibiblio.org/pub/docs/books/gutenberg/1/0/9/3/10930/10930.zip", downloadList[12121]);

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
        assertEquals(books.get(100).Id , 133L);

    }

    @Test
    void getBook() {
        BookApiImp apiImp = new BookApiImp();
        final List<Book> books = apiImp.getBooks();
        final Book tdtw = apiImp.getBook(133L); //== .equals
        assertEquals(tdtw.title , "The Damnation of Theron Ware");

    }

    @Test
    void getAuthors() {
    }
}