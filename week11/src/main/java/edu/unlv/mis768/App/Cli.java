package edu.unlv.mis768.App;

import edu.unlv.mis768.Repository.Api.BookApi;
import edu.unlv.mis768.Repository.Api.BookApiImp;
import edu.unlv.mis768.Repository.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Cli extends AbstractApplication  {
    private static final Logger logger = LogManager.getLogger(Cli.class);
    private static String name = "CLI edu.unlv.mis768.App.Application";
    private static List<Book> bookList = new ArrayList<>();

    @Override
    public void setName(String name) {
        Cli.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public void setup() {
        logger.info("Calling Setup On " + name);

    }

    @Override
    public void start() throws IOException
    {
        logger.info("Entering " + name);
        repl();

    }

    private void repl() throws IOException {
        // create a BufferedReader using System.in
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("# Welcome to MIS 768.");
        System.out.println("# Enter 'stop' to quit.");
        System.out.println("# To see available commands, type  'help'.");
        String cmd = "";
        System.out.print("> ");

        do {
            System.out.print("> ");
            cmd = bufferedReader.readLine();
            System.out.println(eval(cmd));
        }   while(!cmd.equals("quit"));
    }

    public String eval(String cmd) {
        StringBuilder ret = new StringBuilder();
        final String[] availableCommands = {"list", "get", "save", "help"};
        int cmdIndex;
        // match
        for( cmdIndex = 0; cmdIndex < availableCommands.length; cmdIndex++)
            if(cmd.contains(availableCommands[cmdIndex])) break;

        switch (cmdIndex) {
            // list
            case 0:{
                ret.append("Found ").append(bookList.size()).append(" Books");

                long count = bookList.stream()
                        .limit(10)
                        .map(b -> ret.append(b).append("\n"))
                        .count();
                ret.append("displaying first ").append(count).append(" Books");
                break;

            }
            // get
            case 1:{
                String[] args = cmd.split(" ");
                if(args.length <= 1) {
                    ret.append("Usage: get keyword ").append(args);
                } else {
                    String bookId = args[1];
                    ret.append("Looking up book matching ").append(bookId).append("...");
                    long count = bookList.stream()
                            .filter(b -> ("" + b.Id).contains(bookId))
                            .map(b -> ret.append(b).append("\n"))
                            .count();
                    ret.append("Found ").append(count).append(" Books");

                }
                break;

            }
            // save
            case 2:{
                BookApi api = new BookApiImp();
                api.downloadBooks();
                break;

            }
            case 3: {
                for( String s: availableCommands){
                    ret.append(s + "\n");
                }
                break;

            }
            case 4: {
                ret.append("Not a valid command , try help");

            }
            default:{
                ret.append("Not a valid command , try help");

            }


        }
        return ret.toString();
    }
    @Override
    public void stop() {
        logger.info("Exiting " + name);
    }

    @Override
    public  void updateMessage(List<Book> books){
//        logger.info("not implemented yet");
        System.err.println("# Book Lists loaded from Disk");
        bookList = books;

    }
}
