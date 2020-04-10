package edu.unlv.mis768.Repository.Collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

  @Test
  void testToString() {
      Word w = new Word(new Alphabetas[] { Alphabetas.A });
      assertEquals("A", w.value.get(0).toString());
      String s = "Thisisalongword";
      Word w2 = Word.fromString(s);
      assertEquals(s.toUpperCase(), w2.toString());
  }
}