
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
        final int spiders = 5;

        /**
         * Uses virtual threads to spawn over an overwhelming number of threads
         * to take down a target url
         */
        ExecutorService vt = Executors.newVirtualThreadPerTaskExecutor();
            List<Callable<String>> tasks = new ArrayList<>();
            for (int i = 0; i < spiders; i++) {
                tasks.add(new Ping(url));
            }

            // Submit all tasks and collect futures
            List<Future<String>> futures = new ArrayList<>();
            for (Callable<String> task : tasks) {
                futures.add(vt.submit(task));
            }

            for (Future<String> future : futures) {
                try {
                    System.out.println(future.get(10, TimeUnit.SECONDS));
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.err.println("Task failed: " + e.getMessage());
                }
            }
    }
}