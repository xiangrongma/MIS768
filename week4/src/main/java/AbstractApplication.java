import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractApplication implements IApplication{
    private static final Logger logger = LogManager.getLogger(Gui.class);
    private static final String name = "Application";

    final void run() {
        setup();
        logger.error("Running Application " + this.getName() );
        start();
    }

    public abstract void updateMessage(String newText);
}
