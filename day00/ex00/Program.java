public class Program {

	private static final int MIN_NUMBER = 100000;
	private static final int MAX_NUMBER = 999999;

	public static int sumDigits(int n) {
		return n / 100000 + n / 10000 % 10 + n / 1000 % 10 + n / 100 % 10 + n / 10 % 10 + n % 10;
	}

	public static void main(String[] args) {
		int number = 479598;
		if (number >= MIN_NUMBER && number <= MAX_NUMBER) {
			System.out.println(sumDigits(number));
		}
	}
}
