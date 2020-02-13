package App;

import Repository.Book;

import java.util.List;

public interface IApplication {
    void setName(String name);
    String getName();
    void setup();
    void start();
    void stop();
    public  void updateMessage(List<Book> books);

}
