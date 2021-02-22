package chapter06.case06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LifecycleWebServer {

    public static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);

        while (!executorService.isShutdown()) {
            Socket accept = serverSocket.accept();

            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    handleRequest(accept);
                }

            });

        }
    }

    private static void handleRequest(Socket connection) {
        Request req = readRequest(connection);

        if (isShutdownRequest(req)) {
            stop();
        }
        else {
            dispatchRequest(req);
        }
    }

    private static void dispatchRequest(Request req) {
    }

    private static void stop() {
    }

    private static boolean isShutdownRequest(Request req) {
        return false;
    }

    private static Request readRequest(Socket connection) {
        return null;
    }

    public static void shutdown() {
        executorService.shutdown();
    }

    private static class Request {
    }
}
