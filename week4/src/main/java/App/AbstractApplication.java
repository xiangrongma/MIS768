package App;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractApplication implements IApplication{
    private static final Logger logger = LogManager.getLogger(Gui.class);
    private static final String name = "App.Application";

    public final void run() {
        setup();
        logger.error("Running App.Application " + this.getName() );
        start();
    }

}
