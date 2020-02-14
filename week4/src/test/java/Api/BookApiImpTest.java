package Api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}