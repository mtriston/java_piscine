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
		}

		Thread eggThread = new Thread(new VoiceRunnable("Egg", count));
		Thread henThread = new Thread(new VoiceRunnable("Hen", count));
		Runnable humanVoice = new VoiceRunnable("Human", count);
		eggThread.start();
		henThread.start();
		try {
			eggThread.join();
			henThread.join();
		} catch (InterruptedException e) {

		}
		humanVoice.run();
	}
}