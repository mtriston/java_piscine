public class VoiceRunnable implements Runnable {
	
	private final String name;
	private final int count;

	VoiceRunnable(String name, int count) {
		this.name = name;
		this.count = count;
	}

	@Override
	public void run() {
		for (int i = 0; i < count; ++i) {
			System.out.println(name);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				return;
			}
		}
		return;
	}
}
