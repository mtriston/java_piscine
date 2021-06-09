public class Pattern {

	private boolean flag;

	Pattern() {
		this.flag = true;
	}

	public synchronized void printEgg() {
		while (!flag) {
			try {
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println("Egg");
		flag = !flag;
		notify();
	}

	public synchronized void pringHen() {
		while (flag) {
			try {
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println("Hen");
		flag = !flag;
		notify();
	}
	
}
