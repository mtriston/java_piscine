import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class SignatureChecker {
	
	private final Map<String, String> signatures;	

	public SignatureChecker(String signatureFile) {
		this.signatures = new TreeMap<String, String>();
		this.parseFile(signatureFile);
	}

	public boolean isFormatExist(String magic) {
		return signatures.containsKey(magic);
	}

	public String getFormatByMagic(String magic) {
		return signatures.get(magic);
	}

	private void parseFile(String file) {
		try (InputStream inputStream = new FileInputStream(file)) {
			byte []buf = new byte[inputStream.available()];
			inputStream.read(buf);
			for (int i = 0; i < buf.length; ++i) {
				System.out.print(buf[i]);
			}
			// String text = new String(buf);
			// String []lines = text.split("\n");
			// for (String line : lines) {
			// 	System.out.println(line);
			// 	String []str = line.split(", ");
			// 	if (str.length != 2) {
			// 		System.err.printf("Invalid %s\n", file);
			// 		System.exit(-1);
			// 	}
			// 	this.signatures.put(str[1], str[0]);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}