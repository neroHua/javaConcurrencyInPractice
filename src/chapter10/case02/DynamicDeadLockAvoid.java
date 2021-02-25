package chapter10.case02;

public class DynamicDeadLockAvoid {
	private final Object lock = new Object();
	
	public void transfer(User fromUser, User toUser, int money) throws InterruptedException {
		int fromUserHash = System.identityHashCode(fromUser);
		int toUserHash = System.identityHashCode(toUser);

		if (fromUserHash < toUserHash) {
			synchronized (fromUser) {
				synchronized (toUser) {
					Thread.currentThread().sleep(10);
					fromUser.setMoney(fromUser.getMoney() - money);
					toUser.setMoney(toUser.getMoney() + money);
				}
			}
		}
		else if (fromUserHash > toUserHash) {
			synchronized (toUser) {
				synchronized (fromUser) {
					Thread.currentThread().sleep(10);
					fromUser.setMoney(fromUser.getMoney() - money);
					toUser.setMoney(toUser.getMoney() + money);
				}
			}
		}
		else {
			synchronized (lock) {
				synchronized (fromUser) {
					synchronized (toUser) {
						Thread.currentThread().sleep(10);
						fromUser.setMoney(fromUser.getMoney() - money);
						toUser.setMoney(toUser.getMoney() + money);
					}
				}
			}
		}
	}

	public class Transfer extends Thread {

		private final User fromUser;
		private final User toUser;
		private final int money;
		
		public Transfer(User fromUser, User toUser, int money) {
			super();
			this.fromUser = fromUser;
			this.toUser = toUser;
			this.money = money;
		}

		@Override
		public void run() {
			try {
				transfer(fromUser, toUser, money);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}

	public class User {

		int money;
		
		public void setMoney(int money) {
			this.money = money;
		}

		public int getMoney() {
			return money;
		}
		
	}
	
	public static void main(String[] args) {
		DynamicDeadLockAvoid dynamicDeadLockAvoid = new DynamicDeadLockAvoid();
		User fromUser = dynamicDeadLockAvoid.new User();
		User toUser = dynamicDeadLockAvoid.new User();
		int money = 10;

		while (true) {
			Transfer transfer1 = dynamicDeadLockAvoid.new Transfer(fromUser, toUser, money);
			transfer1.start();

			Transfer transfer2 = dynamicDeadLockAvoid.new Transfer(toUser, fromUser, money);
			transfer2.start();
		}
	}
	
}
