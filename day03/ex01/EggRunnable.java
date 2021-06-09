public class EggRunnable implements Runnable {
	
	private final int count;
	private final Pattern pattern;

	EggRunnable(Pattern pattern, int count) {
		this.pattern = pattern;
		this.count = count;
	}

	@Override
	public synchronized void run() {
		for (int i = 0; i < count; ++i) {
			pattern.printEgg();
		}
	}
}
