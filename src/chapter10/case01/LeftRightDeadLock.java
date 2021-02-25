package chapter10.case01;

public class LeftRightDeadLock {

	private final Object leftLock = new Object();

	private final Object rightLock = new Object();

	public void leftThanRight() throws InterruptedException {
		synchronized (leftLock) {
			for (int i = 0; i < 100; i++) {
				Thread.currentThread().sleep(10);
				System.out.println(i);
			}
			synchronized (rightLock) {
				for (int i = 0; i < 100; i++) {
					Thread.currentThread().sleep(10);
					System.out.println(i);
				}
			}
		}
	}

	public void rightThanLeft() throws InterruptedException {
		synchronized (rightLock) {
			for (int i = 0; i < 100; i++) {
				Thread.currentThread().sleep(10);
				System.out.println(i);
			}
			synchronized (leftLock) {
				for (int i = 0; i < 100; i++) {
					Thread.currentThread().sleep(10);
					System.out.println(i);
				}
			}
		}
	}
	
	public class LeftThanRight extends Thread {

		public final LeftRightDeadLock LeftRightDeadLock;
		
		public LeftThanRight(LeftRightDeadLock leftRightDeadLock) {
			this.LeftRightDeadLock = leftRightDeadLock;
		}

		@Override
		public void run() {
			try {
				LeftRightDeadLock.leftThanRight();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}

	public class RightThanLeft extends Thread {

		public final LeftRightDeadLock LeftRightDeadLock;
		
		public RightThanLeft(LeftRightDeadLock leftRightDeadLock) {
			this.LeftRightDeadLock = leftRightDeadLock;
		}

		@Override
		public void run() {
			try {
				LeftRightDeadLock.leftThanRight();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}
	
	public static void main(String[] args) {
		LeftRightDeadLock leftRightDeadLock	 = new LeftRightDeadLock();

		while (true) {
			LeftThanRight leftThanRight = leftRightDeadLock.new LeftThanRight(leftRightDeadLock);
			RightThanLeft rightThanLeft = leftRightDeadLock.new RightThanLeft(leftRightDeadLock);

			leftThanRight.start();
			rightThanLeft.start();
		}
	}
	
}
