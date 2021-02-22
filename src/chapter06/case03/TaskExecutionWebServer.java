package chapter06.case03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {

    public static int THREAD_SIZE = 100;
    public static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);

        while (true) {
            Socket accept = serverSocket.accept();
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    handleRequest(accept);
                }

                private void handleRequest(Socket accept) {

                }

            });
        }
    }

}
