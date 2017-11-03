package ru.kosmodromich;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Main {

    private static Object monitor = new Object();
    private static BlockingQueue<Page> printQueue = new ArrayBlockingQueue<Page>(100);

    public static void main(String[] args) {
        ExecutorService printerService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(3045)) {
            System.out.println("Запуск сервера");
            while (!serverSocket.isClosed()) {
                Socket client = null;
                while (client == null)
                    client = serverSocket.accept();

                printerService.execute(new Printer(client, printQueue, monitor));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
