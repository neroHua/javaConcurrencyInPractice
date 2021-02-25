package chapter10.case03;

import java.util.Random;

public class DemonstrateDeadlock {

    private static final Random rnd = new Random();
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000000;

    public static void main(String[] args) {
        final Account[] accounts = new Account[NUM_ACCOUNTS];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(rnd.nextInt());
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread(accounts).start();
        }
    }

    public static class Account {

        int money;

        public Account(int money) {
            this.money = money;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

    }

    static class TransferThread extends Thread {

        final Account[] accounts;

        public TransferThread(Account[] accounts) {
            this.accounts = accounts;
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
                int toAcct = rnd.nextInt(NUM_ACCOUNTS);
                DollarAmount amount = new DollarAmount(rnd.nextInt(1000));

                try {
                    transferMoney(accounts[fromAcct], accounts[toAcct], amount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void transferMoney(Account fromAccount, Account toAccount, DollarAmount amount) throws InterruptedException {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    Thread.currentThread().sleep(10);
                    fromAccount.setMoney(fromAccount.getMoney() - amount.getMoney());
                    toAccount.setMoney(toAccount.getMoney() + amount.getMoney());
                }
            }
        }

    }

    public static class DollarAmount {

        int money;

        public DollarAmount(int money) {
            this.money = money;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

    }

}
