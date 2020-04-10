package edu.unlv.mis768;

import edu.unlv.mis768.Repository.Api.BookApiImp;
import edu.unlv.mis768.App.AbstractApplication;
import edu.unlv.mis768.App.Cli;
import edu.unlv.mis768.App.Gui;

import edu.unlv.mis768.Repository.Api.BookApi;
import edu.unlv.mis768.Repository.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        List<String> argsList = new ArrayList<String>();
        HashMap<String, String> optsList = new HashMap<>();
        List<String> doubleOptsList = new ArrayList<String>();

        // parse Arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case '-':
                    if (args[i].length() < 2)
                        throw new IllegalArgumentException("Not a valid argument: "+args[i]);
                    if (args[i].charAt(1) == '-') {
                        if (args[i].length() < 3)
                            throw new IllegalArgumentException("Not a valid argument: "+args[i]);
                        // --opt
                        doubleOptsList.add(args[i].substring(2));
                    } else {
                        if (args.length-1 == i) {
                            // a short optoin like -cli, set to "true"
                            optsList.put(args[i].substring(1), "true");
                        } else {
                            optsList.put(args[i].substring(1), args[i+1]);
                        }
                        // -opt
                        i++;
                    }
                    break;
                default:
                    // arg
                    argsList.add(args[i]);
                    break;
            }
        }
        AbstractApplication app ;
        if (optsList.containsKey("gui") || optsList.containsKey("g")) {
            app = new Gui();

        } else if (optsList.containsKey("shell") || optsList.containsKey("c")) {
            app = new Cli();

        } else {
            app = new Gui(); // default to gui
        }
        logger.error(optsList);
        logger.error(doubleOptsList);

        BookApi bookApi= new BookApiImp();

        Thread thread = new Thread(() -> {
            List<Book> books = bookApi.getBooks();
            app.updateMessage(books);
        });
        thread.start();
        app.run();
    }
}
