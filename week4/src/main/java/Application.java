import static java.lang.Thread.sleep;

public class Application {
    public static void main(String[] args) {
        AbstractApplication cliApp = new Cli();
        cliApp.run();
        AbstractApplication guiApp = new Gui();
        Runnable runnable =
                () -> {
                    System.out.println("We are on another thread now, lets sleep");
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("I will change Message area now");
                    String text = "Did you know... \n" +
                            "\n" +
                            "It is impossible to lick your elbow.\n" +
                            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                            "Over 75% of people who read this\n" +
                            "will try to lick their elbow.\n";
                    guiApp.updateMessage(text);
                    //
        };
        Thread thread = new Thread(runnable);
        thread.start();
        guiApp.run();
    }
}
