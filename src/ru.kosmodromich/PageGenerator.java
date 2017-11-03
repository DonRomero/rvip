package ru.kosmodromich;

import java.util.Random;

public class PageGenerator implements Runnable{

    private static long id = 0;
    private Random random = new Random();
    private Printer printer;

    public PageGenerator(Printer printer) {
        this.printer = printer;
    }

    private Page createPage() {
        Page page = new Page();
        page.setId(id++);
        page.setSize(random.nextLong());
        return page;
    }

    private void sendPage(Page page) {
        printer.addPage(page);
        System.out.println("Страница " + page.getId() + " отправлена на печать");
    }

    @Override
    public void run() {
        Page page = createPage();
        System.out.println("Отправляю на печать страницу: " + page.getId());
        try {
            Thread.sleep((new Random().nextInt() & Integer.MAX_VALUE)%10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        sendPage(page);
    }
}
