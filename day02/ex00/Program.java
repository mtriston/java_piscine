import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Program {
	public static void main(String[] args) {
		SignatureChecker signatureChecker = new SignatureChecker("signatures.txt");
	
		

		try (InputStream inputStream = new FileInputStream("picture.png")) {
			byte []buf = new byte[8];
			inputStream.read(buf);
			String magicNumbers = new String();
			for (byte b : buf) {
				String oneByte = new String(Integer.toHexString(Byte.toUnsignedInt(b)));
				if (oneByte.length() == 1) {
					oneByte = "0" + oneByte;
				}
				magicNumbers += oneByte.toUpperCase();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
