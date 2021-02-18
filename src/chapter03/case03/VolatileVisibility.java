package chapter03.case03;

/**
 * volatile
 * 易变的，不稳定的
 *
 * when a core write a value to volatile variable, it will use a lock in asm. this lock may implemented in different way base on the hardware architecture. such as: MESI
 * 当一个核心向一个被volatile修饰的变量写入一个值时，在汇编层面上，他会使用锁。这个锁的实现方式取决硬件的结构。例如：MESI
 *
 * it's little hard to see the asm code complied by JIT in windows.
 * 在windows中想看到汇编代码是有些困难的。
 *
 * here is a good tip to see asm code : https://stackoverflow.com/questions/1503479/how-to-see-jit-compiled-code-in-jvm/24524285#24524285?newreg=de4f506929a54c10b77d7e9e1d1bb94e
 * 这里有个看到汇编代码的好办法: https://stackoverflow.com/questions/1503479/how-to-see-jit-compiled-code-in-jvm/24524285#24524285?newreg=de4f506929a54c10b77d7e9e1d1bb94e
 *
 * The most common use for volatile variables is as a completion, interruption, or status flag.
 * 最适合使用volatile修饰的变量的场景为：做为一个标志，过程结束，中断，状态
 *
 */
public class VolatileVisibility {

    volatile boolean asleep = false;

    public class ReaderThread extends Thread {

        @Override
        public void run() {
            System.out.println("aaaaaa");
            while (!asleep) {
                countSomeSheep();
            }
        }

        private void countSomeSheep() {
            System.out.printf("countSomeSheep");
        }
    }

    public void testVolatile() {
        ReaderThread readerThread = this.new ReaderThread();
        readerThread.start();

        this.asleep = true;
    }

    public static void main(String[] args) {
        VolatileVisibility volatileVisibility = new VolatileVisibility();
        volatileVisibility.testVolatile();
    }

}
