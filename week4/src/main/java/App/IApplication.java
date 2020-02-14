package App;

import Repository.Book;

import java.io.IOException;
import java.util.List;

public interface IApplication {
    void setName(String name);
    String getName();
    void setup();
    void start() throws IOException;
    void stop();
    public  void updateMessage(List<Book> books);

}
