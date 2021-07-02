import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class UrlHolder {

    private List<Pair<Integer, URL>> urls;

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
            String[] tokens = line.split(" ");
            if (tokens.length != 2)
                throw new InvalidFormatUrlList("invalid line: " + line);
            try {
                number = Integer.parseInt(tokens[0]);
                url = new URL(tokens[1]);
            } catch (Exception e) {
                e.initCause(new InvalidFormatUrlList("invalid line: " + line));
                throw e;
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

class Pair<A, B> {
    private final A key;
    private final B value;

    public Pair(A key, B value) {
        super();
        this.key = key;
        this.value = value;
    }

    public A getKey() {
        return key;
    }

    public B getValue() {
        return value;
    }
}