import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class SignatureChecker {
	
	private final Map<String, String> signatures;

	public SignatureChecker(String signatureFile) {
		this.signatures = new TreeMap<String, String>();
		this.parseSignatureFile(signatureFile);
	}

	public String getMagicNumber(String file) {
		String magicNumbers = new String();
		byte []buf = new byte[8];
		try (InputStream inputStream = new FileInputStream(file)) {
			inputStream.read(buf);
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
		return magicNumbers;
	}

	public boolean isFormatExist(String magic) {
		for (Map.Entry<String, String> entry : signatures.entrySet()) {

			String tmpMagic = magic;
			String tmpKey = entry.getKey();

			if (magic.length() > tmpKey.length()) {
				tmpMagic = magic.substring(0, tmpKey.length());
			}
			if (tmpMagic.equals(tmpKey)) {
				return true;
			}
		}
		return false;
	}

	public String getFormatByMagic(String magic) {
		for (Map.Entry<String, String> entry : signatures.entrySet()) {
			String tmpMagic = magic;
			String tmpKey = entry.getKey();
			
			if (magic.length() > tmpKey.length()) {
				tmpMagic = magic.substring(0, tmpKey.length());
			}
			if (tmpMagic.equals(tmpKey)) {
				return entry.getValue();
			}
		}
		return "";
	}

	private void parseSignatureFile(String file) {
		try (InputStream inputStream = new FileInputStream(file)) {
			byte []buf = new byte[inputStream.available()];
			inputStream.read(buf);
			String text = new String(buf);
			String []lines = text.split("\n");
			for (String line : lines) {
				String []str = line.split(", ");
				if (str.length != 2) {
					System.err.printf("Invalid %s\n", file);
					System.exit(-1);
				}
				str[1] = str[1].replaceAll("\\s", "");
				this.signatures.put(str[1], str[0]);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
