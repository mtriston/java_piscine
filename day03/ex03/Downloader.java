import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Downloader implements Runnable {

    private final UrlHolder urlHolder;
    private final int id;

    public Downloader(UrlHolder urlHolder, int id) {
        this.urlHolder = urlHolder;
        this.id = id;
    }

    @Override
    public void run() {
        Pair<Integer, URL> pair = null;
        while (true) {
            pair = urlHolder.getUrl();
            if (pair == null)
                break;
            System.out.printf("Thread-%d start download file number %d\n", id, pair.getKey());
            try (InputStream inputStream = pair.getValue().openStream()) {

                String date = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss_").format(Calendar.getInstance().getTime());
                String fileName = pair.getValue().getFile().substring(pair.getValue().getFile().lastIndexOf('/') + 1);

                Files.copy(inputStream, new File(date + fileName).toPath());

                System.out.printf("Thread-%d finish download file number %d\n", id, pair.getKey());
            } catch (Exception e) {
                System.err.printf("Thread-%d can't download file number %d: %s\n", id, pair.getKey(), e.getMessage());
            }
        }
    }
}