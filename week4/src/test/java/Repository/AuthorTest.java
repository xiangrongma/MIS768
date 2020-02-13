package Repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    @Test
    void testToString() {
        Author shakespeare = new Author("Shakespeare","William");
        assertEquals("Shakespeare William",shakespeare.toString());
    }
}