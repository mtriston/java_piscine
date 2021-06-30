public class Counter implements Runnable {

	private final int index;
	private final int start;
	private final int end;
	private final int[] array;

	Counter(int index, int span, int[] array) {
		this.index = index;
		this.start = index * span;

		if (start + span > array.length) {
			this.end = array.length;
		} else {
			this.end = start + span;
		}
		
		this.array = array;
	}

	@Override
	public void run() {
		int sum = 0;
		for (int i = start; i < end; ++i) {
			sum += array[i];
		}
		System.out.printf("Thread %d: from %d to %d sum is %d\n", index, start, end, sum);
		Program.sumByThreads += sum;
	}
	
}
