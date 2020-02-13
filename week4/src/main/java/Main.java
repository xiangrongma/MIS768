import Api.BookApiImp;
import App.AbstractApplication;
import App.Cli;
import App.Gui;

import Api.BookApi;
import Repository.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        AbstractApplication cliApp = new Cli();
        cliApp.run();
        AbstractApplication guiApp = new Gui();
        BookApi bookApi= new BookApiImp();
//        Runnable runnable =
        Thread thread = new Thread(() -> {
            List<Book> books = bookApi.getBooks();
            guiApp.updateMessage(books);
        });
        thread.start();
        guiApp.run();
    }
}
