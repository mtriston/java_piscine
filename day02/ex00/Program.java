import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Program {

	static private final String STOP_WORD = "42";
	static private final String SIGNATURE_FILE = "signatures.txt";
	static private final String RESULT_FILE = "result.txt";
	public static void main(String[] args) {
		SignatureChecker signatureChecker = new SignatureChecker(SIGNATURE_FILE);

		try (OutputStream outputStream = new FileOutputStream(RESULT_FILE)) {
		
			Scanner scanner = new Scanner(System.in);

			String filePath = scanner.next();
			while (!filePath.equals(STOP_WORD)) {
				String signature = signatureChecker.getMagicNumber(filePath);
				if (signatureChecker.isFormatExist(signature)) {
					System.out.println("PROCESSED");
					outputStream.write(signatureChecker.getFormatByMagic(signature).concat("\n").getBytes());
				} else {
					System.out.println("UNDEFINED");
				}
				filePath = scanner.next();
			}
			scanner.close();
		} catch (IOException e) {
			System.err.printf("Invalid signature file: %s\n", SIGNATURE_FILE);
			System.exit(-1);
		}
	}
}
