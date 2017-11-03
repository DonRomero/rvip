package ru.kosmodromich;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService printerService = Executors.newFixedThreadPool(1);
        Printer printer = new Printer();
        printer.setDaemon(true);
        printerService.execute(new Printer());
        ExecutorService service = Executors.newCachedThreadPool();
        while(true) {
            try {
                Thread.sleep((new Random().nextInt() & Integer.MAX_VALUE) % 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            service.submit(new PageGenerator(printer));
        }
    }
}
