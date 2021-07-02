
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {

    private static final String FILE_WITH_URLS = "files_urls.txt";

    public static void main(String[] args) {

        int threadsCount = 0;
        List<Thread> threads;
        UrlHolder urlHolder;

        if (args.length != 1 || args[0].substring(0, 15).compareToIgnoreCase("--threadsCount=") != 0) {
            System.err.println("Invalid argument!");
            System.err.println("Example: java Program --threadsCount=3");
            System.exit(-1);
        }

        try {
            threadsCount = Integer.parseInt(args[0].substring(15));
            if (threadsCount <= 0)
                throw new IllegalArgumentException();
        } catch (Exception e) {
            System.err.println("Invalid value of argument!");
            System.err.println("Example: java Program --threadsCount=3");
            System.exit(-1);
        }

        threads = new ArrayList<>();
        urlHolder = new UrlHolder();

        try {
            urlHolder.parseUrls(FILE_WITH_URLS);
        } catch (IOException | InvalidFormatUrlList e) {
            System.err.printf("An error occurred while parsing the %s:\n", FILE_WITH_URLS);
            System.err.println(e);
            System.err.println(e.getCause().getMessage());
        }

        for (int i = 0; i < threadsCount; ++i) {
            threads.add(new Thread(new Downloader(urlHolder, i + 1)));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}