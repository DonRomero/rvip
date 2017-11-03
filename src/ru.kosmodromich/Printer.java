package ru.kosmodromich;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Printer extends Thread{

    public ConcurrentLinkedQueue<Page> printQueue = new ConcurrentLinkedQueue<Page>();

    void addPage(Page page) {
        printQueue.offer(page);
    }

    private void printPage(Page page) {
        System.out.println("Печатаю страницу страницу: " + page.getId());
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Страница " + page.getId() + " распечатана");
    }

    @Override
    public void run() {
        while(true) {
            if(printQueue.size() > 0) {
                printPage(printQueue.element());
                printQueue.remove();
            }
        }
    }
}
