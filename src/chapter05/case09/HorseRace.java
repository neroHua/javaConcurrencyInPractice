package chapter05.case09;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * horse race
 * 赛马
 *
 * m horse n round
 * m个马 n个回合
 *
 * a horse run a random step in one round
 * 一个马跑一个随机的步数在一回合中
 *
 * print every horse's score in this round when a round finished
 * 打印每一个马在本回合的成绩,当本回合结束时
 *
 * print every horse's total score when a round finished
 * 打印每个马的总成绩,当本回合结束时
 *
 * print winner horse when a round finished
 * 打印赢的马,当本回合结束时
 *
 *
 */
public class HorseRace implements Runnable {

    private Horse[] horseArray;

    private CyclicBarrier cyclicBarrier;

    private int raceCount;

    public HorseRace(int raceCount) {
        this.raceCount = raceCount;
    }

    public void initHorse(int cpuCount, CyclicBarrier cyclicBarrier) {
        this.horseArray = new Horse[cpuCount];
        for (int i = 0; i < cpuCount; i++) {
            this.horseArray[i] = new Horse(i, 10, raceCount, cyclicBarrier);
        }

        this.cyclicBarrier = cyclicBarrier;
    }

    public void start() {
        for(int i = 0; i < horseArray.length; i++) {
            new Thread(horseArray[i]).start();
        }
    }

    @Override
    public void run() {
        System.out.println("************************all horse finish******************************");
        System.out.println("************************one round finish******************************");
        showLength();
        showWinner();
        System.out.println("************************one round finish******************************");
    }

    private void showLength() {
        for (int i = 0; i < horseArray.length; i++) {
            horseArray[i].showLength();
        }
    }

    public void showWinner() {
        int maxLength = 0;
        Horse maxHorse = null;

        for (int i = 0; i < horseArray.length; i++) {
            if (horseArray[i].getLength() > maxLength) {
                maxLength = horseArray[i].getLength();
                maxHorse = horseArray[i];
            }
        }

        System.out.println("winner horse's id is \t" + maxHorse.getId());
    }

    public static class Horse implements Runnable {

        private int id;

        private Random random;
        private int lengthRange;
        private int length;

        private int raceCount;

        private CyclicBarrier cyclicBarrier;

        public Horse(int id, int lengthRange, int raceCount, CyclicBarrier cyclicBarrier) {
            this.id = id;

            this.random = new Random();
            this.lengthRange = lengthRange;
            this.length = 0;
            this.raceCount = raceCount;

            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            for (int i = 0; i < raceCount; i++) {
                int roundLength = random.nextInt(lengthRange);
                length += roundLength;
                System.out.println("horse \t" + id + "\tmove\t" + roundLength + "\tin round\t" + i);
                try {
                    System.out.println("horse \t" + id + "\tfinish round\t" + i + "\t start waiting other horse");
                    cyclicBarrier.await();
                    System.out.println("horse \t" + id + "\tfinish round\t" + i + "\t end waiting other horse");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void showLength() {
            System.out.print("horse\t" + id + "\ttotalLength\t" + length + "\t");
            for (int i = 0; i < length; i++) {
                System.out.print("*");
            }
            System.out.println();
        }

        public int getLength() {
            return this.length;
        }

        public int getId() {
            return this.id;
        }

    }

    public static void main(String[] args) {
        final int cpuCount = Runtime.getRuntime().availableProcessors();
        final int raceCount = 5;

        HorseRace horseRace = new HorseRace(raceCount);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(cpuCount, (Runnable) horseRace);

        horseRace.initHorse(cpuCount, cyclicBarrier);

        horseRace.start();
    }

}

