import java.util.Scanner;

public class Program {
	
	private static final String STOP_WORD = "42";
	private static final int MAX_WEEKS = 18;
	private static final int COUNT_OF_MARKS = 5;
	private static final int MIN_MARK = 1;
	private static final int MAX_MARK = 9;

	public static boolean isValidWeek(String str, int n) {
		return str.equals("Week " + n);
	}

	public static boolean isValidMark(int n) {
		return n >= MIN_MARK && n <= MAX_MARK;
	}
	
	public static long addToStore(long store, int n) {
		return store * 10 + n;
	}
	
	public static int scanAndGetMinMark(Scanner scanner) {
		int min = 0;
		int current = 0;
		for (int i = 0; i < COUNT_OF_MARKS; ++i) {
			if (!scanner.hasNextInt()) {
				System.err.println("Illegal Argument");
				System.exit(-1);
			}
			current = scanner.nextInt();
			if (!isValidMark(current)) {
				System.err.println("Illegal Argument");
				System.exit(-1);
			}
			min = current < min ? current : min;
		}
		scanner.nextLine();
		return min;
	}

	public static void printResult(long store, int count) {
		for (int i = 1; i <= count; ++i) {
			System.out.printf("Week %d\n", i);
		}
	}

	public static void main(String[] args) {
		int count = 1;
		long store = 0;
		Scanner scanner = new Scanner(System.in);
		String numOfWeek = scanner.nextLine();

		while (!numOfWeek.equals(STOP_WORD)) {
			if (!isValidWeek(numOfWeek, count)) {
				System.err.println("Illegal Week Argument");
				System.exit(-1);
			}
			store = addToStore(store, scanAndGetMinMark(scanner));
			numOfWeek = scanner.nextLine();
			count++;
		}
		printResult(store, count);
	}
}
