public class Program {

	private static int count;
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Invalid count of arguments!");
			System.exit(-1);
		}

		try {
			count = Integer.parseInt(args[0].substring(8));
		} catch (Exception e) {
			System.err.println("Invalid format of argument! Expected '--count=N'");
			System.exit(-1);
		}

		if (count <= 0) {
			System.err.println("Expected positive N");
			System.exit(-1);
		}

		Pattern pattern = new Pattern();
		Thread eggThread = new Thread(new EggRunnable(pattern, count));
		Thread henThread = new Thread(new HenRunnable(pattern, count));
		eggThread.start();
		henThread.start();
	}
}