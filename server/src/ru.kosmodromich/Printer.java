package ru.kosmodromich;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Printer implements Runnable {

    private BlockingQueue<Page> printQueue;
    public static Socket clientDialog;
    Object monitor;

    public Printer(Socket client, BlockingQueue<Page> printQueue, Object monitor) {
        Printer.clientDialog = client;
        this.printQueue = printQueue;
        this.monitor = monitor;
    }

    synchronized void addPage(Page page) {
        printQueue.offer(page);
    }

    private synchronized void printPage(Page page) {
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
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientDialog.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientDialog.getInputStream());

            try {
                HashMap<String, Long> obj = (HashMap<String, Long>) in.readObject();
                Page page = new Page();
                page.setId(obj.get("id"));
                page.setSize(obj.get("size"));
                addPage(page);
            } catch (ClassCastException ex) {
                ex.printStackTrace();
            }
            if (printQueue.size() > 0) {
//                try {
                    synchronized (monitor) {
                        printPage(printQueue.element());
//                        Thread.sleep(1000);
                        printQueue.remove();
                    }
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
                out.flush();
            }

            in.close();
            out.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
