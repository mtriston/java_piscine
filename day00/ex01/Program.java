import java.util.Scanner;

public class Program {

	private static final int MIN_NUMBER = 2;

	public static int getSqrt(int n) {
		for (int i = 1; i <= n; ++i) {
			if (i * i >= n) {
				return i;
			}
		}
		return 0;
	}

	public static boolean isValid(int n) {
		return n >= MIN_NUMBER;
	}

	public static void printIsPrime(int n) {
		int count = 0;
		for (int i = MIN_NUMBER; i <= getSqrt(n); ++i) {
			++count;
			if (n % i == 0) {
				System.out.printf("false %d\n", count);
				return ;
			}
		}
		System.out.printf("true %d\n", count);
	}

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		if (!scanner.hasNextInt()) {
			System.err.println("Illegal Argument");
			System.exit(-1);
		}
		int n = scanner.nextInt();
		if (!isValid(n)) {
			System.err.println("Illegal Argument");
			System.exit(-1);
		} else {
			printIsPrime(n);
		}
		scanner.close();
	}
}
