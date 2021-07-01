import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Program {
    public static void main(String[] args) {

    }
}

class Downloader implements Runnable {
    private final UrlHolder urlHolder;

    public Downloader(UrlHolder urlHolder) {
        this.urlHolder = urlHolder;
    }

    @Override
    public void run() {
        Pair<Integer, URL> pair = null;
        while (true) {
            pair = urlHolder.getUrl();
            if (pair == null)
                break;
            try (InputStream inputStream = pair.getValue().openStream()) {
                Files.copy(inputStream, new File(pair.getValue().getFile()).toPath());
            } catch (IOException e) {
        }
    }
}

class UrlHolder {
    private List<Pair<Integer, URL> > urls;

    UrlHolder() {
        urls = new LinkedList<>();
    }

    synchronized  Pair<Integer, URL> getUrl() {
        Pair<Integer, URL> pair = null;
        if (!urls.isEmpty()) {
            pair = urls.get(0);
            urls.remove(0);
        }
        return pair;
    }

    void parseUrls(String file) throws IOException, InvalidFormatUrlList {
        int number;
        URL url;
        List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath(file));
        for (String line : lines) {
            try {
                number = Integer.parseInt(line);
                url = new URL(line.split(" ")[1]);
            } catch (Exception e) {
                throw new InvalidFormatUrlList("invalid line: " + line);
            }
            urls.add(new Pair<>(number, url));
        }
    }
}

class InvalidFormatUrlList extends Exception {
    InvalidFormatUrlList(String message) {
        super(message);
    }
}