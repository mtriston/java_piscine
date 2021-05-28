import java.util.Scanner;

public class Program {

	private static final int MIN_NUMBER = 2;
	private static final int STOP_NUMBER = 42;

	public static int getSqrt(int n) {
		for (int i = 1; i <= n; ++i) {
			if (i * i >= n) {
				return i;
			}
		}
		return 0;
	}
	
	public static int getSumDigits(int n) {
		int sum = 0;
		while (n > 0) {
			sum += n % 10;
			n /= 10;
		}
		return sum;	
	}

	public static boolean isPrime(int n) {
		if (n < MIN_NUMBER) {
			return false;
		}
		for (int i = MIN_NUMBER; i <= getSqrt(n) && i != n; ++i) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int requests = 0;
		int n;

		if (!scanner.hasNextInt()) {
			System.err.println("Illegal Argument");
			System.exit(-1);
		}
		n = scanner.nextInt();

		while (n != STOP_NUMBER) {
			if (isPrime(getSumDigits(n))) {
				++requests;
			}
			if (!scanner.hasNextInt()) {
				System.err.println("Illegal Argument");
				System.exit(-1);
			}
			n = scanner.nextInt();
		}
		System.out.printf("Count of coffee-request - %d\n", requests);
		scanner.close();
	}
}
