package chapter13.case02;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class PolledAndTimeoutLock {

    Random rnd = new Random();

    public boolean transferMoney(Account fromAcct, Account toAcct, DollarAmount amount, long timeout, TimeUnit unit) throws InsufficientFundsException, InterruptedException {
        long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
        long randMod = getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);

        // polled locking
        // 轮训
        while (true) {
            if (fromAcct.lock.tryLock()) {
                try {
                    if (toAcct.lock.tryLock()) {
                        try {
                            if (fromAcct.getBalance().compareTo(amount) < 0) {
                                throw new InsufficientFundsException();
                            }
                            else {
                                fromAcct.debit(amount);
                                toAcct.credit(amount);
                                return true;
                            }
                        }
                        finally {
                            toAcct.lock.unlock();
                        }
                    }
                }
                finally {
                    fromAcct.lock.unlock();
                }
            }

            // timeout
            // 超时
            if (System.nanoTime() < stopTime) {
                return false;
            }

            // polled locking -- sleeping
            // 轮训 -- 休眠
            NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
        }
    }

    private long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return rnd.nextLong() & unit.toNanos(timeout) << 10;
    }

    private long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return unit.toNanos(timeout) & 7;
    }


    private class Account {

        Lock lock;
        DollarAmount dollarAmount;

        public Account(Lock lock, DollarAmount dollarAmount) {
            this.lock = lock;
            this.dollarAmount = dollarAmount;
        }

        public Lock getLock() {
            return lock;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }

        public DollarAmount getDollarAmount() {
            return dollarAmount;
        }

        public void setDollarAmount(DollarAmount dollarAmount) {
            this.dollarAmount = dollarAmount;
        }

        public Comparable<DollarAmount> getBalance() {
            return null;
        }

        public void debit(DollarAmount amount) {
            this.dollarAmount.setBigDecimal(this.dollarAmount.getBigDecimal().subtract(amount.getBigDecimal()));
        }

        public void credit(DollarAmount amount) {
            this.dollarAmount.setBigDecimal(this.dollarAmount.getBigDecimal().add(amount.getBigDecimal()));
        }

    }

    private class DollarAmount {

        BigDecimal bigDecimal;

        public DollarAmount(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }

    }

    private class InsufficientFundsException extends Exception {
    }

}
