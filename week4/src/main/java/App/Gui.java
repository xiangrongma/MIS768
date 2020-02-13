package App;

import Repository.Book;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class Gui extends AbstractApplication implements IApplication {
    private static final Logger logger = LogManager.getLogger(Gui.class);
    private static Display display;
    private static Shell shell;
    private static String name = "GUI App.Application" ;
    private static String clickUrl;
    private static Text messages;
    private static Table table;
    private static boolean dataBecomeReady = false;
    private static boolean needUpdate = false;
    private static List<Book> bookList = new ArrayList<>();

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
//        shell.setSize(800,600);
        shell.setBounds(10, 10, 1280, 600);

        shell.setText(name);

        messages= new Text(shell, SWT.BORDER |
                SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        messages.setSize(1280,100);
        messages.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

        messages.setText(textbuffer);
        messages.pack();

        table = new Table(shell, SWT.VIRTUAL | SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION);
        table.setItemCount( 700000 );
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setSize(1280,400);
        table.setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,10,10));

        String[] header = { "Id", "Title", "Author" };
        for (String title : header) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(title);
        }
        for (int i = 0; i < header.length; i++) {
            table.getColumn(i).pack();
        }

        table.addListener( SWT.SetData, event -> {
            TableItem item = (TableItem)event.item;
            populateItem(item);


        });
        /* Add Menu*/
        Menu contextMenu = new Menu(table);
        table.setMenu(contextMenu);
        MenuItem mItem1 = new MenuItem(contextMenu, SWT.None);
        mItem1.setText("open book");
        mItem1.addListener(SWT.Selection, event -> {
                org.eclipse.swt.program.Program.launch(clickUrl);
                });
        table.addListener(SWT.MouseDown, event -> {
            TableItem[] selection = table.getSelection();
            if(selection.length!=0 && (event.button == 3)){
                contextMenu.setVisible(true);
                clickUrl = "http://www.gutenberg.org/ebooks/"+ selection[0].getText();
            }
        });
        table.addListener(SWT.Resize, new Listener() {

            @Override
            public void handleEvent(Event event) {
                Table table = (Table)event.widget;
                int columnCount = table.getColumnCount();
                if(columnCount == 0)
                    return;
                Rectangle area = table.getClientArea();
                int totalAreaWdith = area.width;
                int lineWidth = table.getGridLineWidth();
                int totalGridLineWidth = (columnCount-1)*lineWidth;
                int totalColumnWidth = 0;
                for(TableColumn column: table.getColumns())
                {
                    totalColumnWidth = totalColumnWidth+column.getWidth();
                }
                int diff = totalAreaWdith-(totalColumnWidth+totalGridLineWidth);
                TableColumn lastCol = table.getColumns()[columnCount-1];
                lastCol.setWidth(diff+lastCol.getWidth());

            }
        });
        table.pack();
        GridLayout layout = new GridLayout();

        layout.numColumns = 1;



        shell.setLayout(layout);
        shell.pack();
        shell.open();
        logger.trace("Entering application.");
    }

    public void start() {
        logger.error("Enter UI Main Loop");


        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();

            if (dataBecomeReady && needUpdate){
                needUpdate = false;
                table.setItemCount( bookList.size() );
                table.setRedraw(true);
                setTextbuffer(textbuffer + "\nDone!");

            }

        }
        display.dispose ();
        stop();
    }

    @Override
    public void stop() {
        logger.error("Exiting App.Application");
    }


    @Override
    public synchronized void updateMessage(List<Book> data) {
        dataBecomeReady = true;
        needUpdate = true;
        bookList = data;
        setTextbuffer(textbuffer + "Loading data to table");

    }
    static void populateItem(TableItem item) {
        if(dataBecomeReady) {
            int idx = table.indexOf(item);
            if (bookList.size() > idx) {
                Book book = bookList.get(idx);
                item.setText( 0, ""+book.Id );
                item.setText( 1, book.title);
                item.setText( 2, book.author);
            }

        }

    }
}