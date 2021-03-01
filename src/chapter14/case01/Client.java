package chapter14.case01;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        final long SLEEP_GRANULARITY = 100;
        GrumpyBoundedBuffer<String> buffer = new GrumpyBoundedBuffer<String>(10);

        // polled
        // 轮询
        while (true) {
            try {
                String item = buffer.take();
                break;
            } catch (GrumpyBoundedBuffer.BufferEmptyException e) {
                Thread.sleep(SLEEP_GRANULARITY);
            }
        }
    }

}
