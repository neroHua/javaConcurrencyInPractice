package chapter05.case01;

import common.NotThreadSafe;

import java.util.Vector;

public class CompoundActionOnVector {

    private Vector<String> vector;

    public CompoundActionOnVector() {
        Vector<String> vector = new Vector<String>(8);
        vector.add("1");
        vector.add("2");
        vector.add("3");
        vector.add("4");
        vector.add("5");
        vector.add("6");
        vector.add("7");
        vector.add("8");

        this.vector = vector;
    }

    @NotThreadSafe
    public String getLast() {
        int lastIndex = vector.size() - 1;
        return vector.get(lastIndex);
    }

    @NotThreadSafe
    public void deleteLast() {
        int lastIndex = vector.size() - 1;
        vector.remove(lastIndex);
    }

    @NotThreadSafe
    public void visit() {
        for (String i : vector) {
            System.out.println(i);
        }
    }

    public class ReadThread extends Thread {

        @Override
        public void run() {
            getLast();
        }

    }

    public class WriteThread extends Thread {

        @Override
        public void run() {
            deleteLast();
        }

    }

    public static void main(String[] args) {
        while (true) {
            CompoundActionOnVector compoundActionOnVector = new CompoundActionOnVector();
            CompoundActionOnVector.ReadThread readThread = compoundActionOnVector.new ReadThread();
            CompoundActionOnVector.WriteThread writeThread = compoundActionOnVector.new WriteThread();

            readThread.start();
            writeThread.start();
        }
    }

}
