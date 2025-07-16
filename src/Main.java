
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        /**
         * Target URL is hardcoded within the program to save that little space
         * rather than invoking a new Input stream to get bytes from user
         * Spiders = Number of taget nodes to be sent
         */
        final String url = "https://google.com";
        final int spiders = 100;

        ForkJoinPool pool = new ForkJoinPool(10);
        List<Ping> list = new ArrayList<>();

        for(int i=0; i<spiders; i++) {
            list.add(new Ping(url));
        }

        List<ForkJoinTask<String>> futures = new ArrayList<>();
        for(Ping p : list){
            futures.add(pool.submit(p));
        }

        for (ForkJoinTask<String> future : futures) {
            System.out.println(future.join());
        }
        //Graceful shutdown the threadpool
        pool.shutdown();
    }
}