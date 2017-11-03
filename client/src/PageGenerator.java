import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class PageGenerator implements Runnable {

    static Socket socket;
    private static long id = 0;
    private Random random = new Random();

    public PageGenerator() {
        try {
            socket = new Socket("localhost", 3045);
            System.out.println("Client connected to socket");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Page createPage() {
        Page page = new Page();
        page.setId(id++);
        page.setSize(random.nextLong());
        return page;
    }

    private void sendPage(ObjectOutputStream oos, Page page) throws IOException{
        HashMap<String, Long> obj = new HashMap<>();
        obj.put("id", page.getId());
        obj.put("size", page.getSize());
        oos.writeObject(obj);
        oos.flush();
        System.out.println("Страница " + page.getId() + " отправлена на печать");
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            Page page = createPage();
        System.out.println("Отправляю на печать страницу: " + page.getId());
        try {
            Thread.sleep((new Random().nextInt() & Integer.MAX_VALUE)%10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        sendPage(oos, page);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
