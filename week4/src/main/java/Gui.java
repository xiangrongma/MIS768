
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Gui extends AbstractApplication implements IApplication {
    private static final Logger logger = LogManager.getLogger(Gui.class);
    private static Display display;
    private static Shell shell;
    private static Layout layout;
    private static String name = "GUI Application" ;

    private static Text messages;
    private boolean textChanged = false;

    public static String getTextbuffer() {
        return textbuffer;
    }

    public static void setTextbuffer(String textbuffer) {
        Gui.textbuffer = textbuffer;
    }

    private static String textbuffer = "Hello";

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setup() {
        logger.error("Calling Setup on " + name);
        try {
        setupUI();
        } catch ( SWTException e ) {
            logger.error(e);
            logger.info("Please add -XstartOnFirstThread to your configuration(run)");
        }
    }

    private void setupUI() {
        display = new Display ();
        shell = new Shell(display);
        shell.setSize(800,600);
        shell.setText(name);
        messages= new Text(shell, SWT.BORDER |
                SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        messages.setSize(700,600);
//        messages.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

        messages.setText(textbuffer);
        messages.pack();

        layout = new FillLayout();
        shell.setLayout(layout);
        shell.pack();
        shell.open();
        logger.trace("Entering application.");
    }

    public void start() {
        logger.error("Enter UI Main Loop");


        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
            if (textChanged){
                messages.setText(getTextbuffer());
                textChanged = false;
            }

        }
        display.dispose ();
        stop();
    }

    @Override
    public void stop() {
        logger.error("Exiting Application");
    }


    @Override
    public void updateMessage(String newText) {
        setTextbuffer(newText);
        this.textChanged = true;
    }
}