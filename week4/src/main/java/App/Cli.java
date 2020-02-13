package App;

import Repository.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Cli extends AbstractApplication  {
    private static final Logger logger = LogManager.getLogger(Cli.class);
    private static String name = "CLI App.Application";

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected void finalize() throws Throwable {
        stop();
        super.finalize();
    }

    @Override
    public void setup() {
        logger.error("Calling Setup On " + name);

    }

    @Override
    public void start() {
        logger.error("Entering " + name);

    }

    @Override
    public void stop() {
        logger.error("Exiting " + name);
    }

    @Override
    public  void updateMessage(List<Book> books){
        logger.error("not implemented yet");
    }
}
