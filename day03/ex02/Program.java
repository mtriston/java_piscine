import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Program {

	public static volatile int sumByThreads = 0;
	public static void main(String[] args) {

		int array[];
		int threadsCount = 0;
		int arraySize = 0;
		int span;

		if (args.length != 2) {
			System.err.println("Invalid number of arguments!");
			System.err.println("Example: java Program --arraySize=13 --threadsCount=3");
			System.exit(-1);
		}

		if (args[0].substring(0, 12).compareToIgnoreCase("--arraySize=") != 0 ||
			args[1].substring(0, 15).compareToIgnoreCase("--threadsCount=") != 0)
		{
			System.err.println("Invalid format of arguments!");
			System.err.println("Example: java Program --arraySize=13 --threadsCount=3");
			System.exit(-1);
		}

		try {
			arraySize = Integer.parseInt(args[0].substring(12));
			threadsCount = Integer.parseInt(args[1].substring(15));
		} catch (NumberFormatException e) {
			System.err.println("Invalid value of arguments!");
			System.err.println("Example: java Program --arraySize=13 --threadsCount=3");
			System.exit(-1);
		}

		if (threadsCount <= 0 || arraySize <= 0) {
			System.err.println("Invalid value of arguments!");
			System.exit(-1);
		}

		span = (int)Math.ceil((double)arraySize / threadsCount);

		array = new Random().ints(arraySize, -1000, 1000).toArray();

		int sum = 0;
		for (int element : array) {
			sum += element;
		}

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < threadsCount; ++i) {
			threads.add(new Thread(new Counter(i, span, array)));
			threads.get(i).start();
		}
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}

		System.out.printf("Sum: %d\n", sum);
		System.out.printf("Sum by threads: %d\n", sumByThreads);
	}
}