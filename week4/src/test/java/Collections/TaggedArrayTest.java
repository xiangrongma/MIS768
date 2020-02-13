package Collections;

import org.junit.jupiter.api.Test;
import java.util.Spliterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaggedArrayTest {

  private static final TaggedArray<String> taggedArray;

  static {
    String [] data = {"data1","data2"};
    String [] tag = {"tag1","tag2"};
    taggedArray = new TaggedArray<>(data, tag);
  }

  @Test
  void spliterator() {
    StringBuffer sb = new StringBuffer();
    taggedArray.spliterator().forEachRemaining( v -> sb.append(v));
    assertEquals("data1data2", sb.toString());
  }

  @Test
  void testSpliterator() {
    Spliterator<String> spliterator = taggedArray.spliterator();
    long estimateSize = spliterator.estimateSize();
    long exactSizeIfKnown = spliterator.getExactSizeIfKnown();
    assertEquals(exactSizeIfKnown, estimateSize  );

  }
  @Test
  void parEach() {
    StringBuilder sb = new StringBuilder();

    TaggedArray.parEach(taggedArray, sb::append);
    assertEquals("data2data1", sb.toString());

  }

    @Test
    void testParEach() {


    }
}