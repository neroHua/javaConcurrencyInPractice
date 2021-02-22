package chapter06.case02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            Socket accept = serverSocket.accept();

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    handleRequest(accept) ;
                }

                private void handleRequest(Socket accept) {
                }

            });

            thread.start();
        }
    }
}
