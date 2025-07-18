import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * A ForkJoin RecursiveTask that continuously sends HTTP GET requests
 * to the specified URL and logs the response status and time taken.
 * The task runs indefinitely between each request.
 * Designed for IO-bound URL pinging and demonstration purposes.
 */
class Ping implements Callable<String> {
    private final String url;
    public Ping(String url){
        this.url = url;
    }

    /**
     * A bunch of online extracted user agents to fool the target server
     * making request appear from different clients through
     * User-Agent header passed within the request
     */
    private static final String[] userAgents = {
            "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36",
            "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36,gzip(gfe)",
            "Mozilla/5.0 (Linux; Android 14; SM-S928B/DS) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.6099.230 Mobile Safari/537.36",
            "Mozilla/5.0 (X11; CrOS x86_64 14541.0.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Linux; Android 9; AFTR) AppleWebKit/537.36 (KHTML, like Gecko) Silk/98.6.10 like Chrome/98.0.4758.136 Safari/537.36",
            "Mozilla/5.0 (iPhone17,4; CPU iPhone OS 18_2_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Resorts/4.7.5",
            "Mozilla/5.0 (iPhone17,5; CPU iPhone OS 18_3_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 FireKeepers/1.7.0"
    };
    private static final Random random = new Random();
    @Override
    public String call() {
        try {
            long start = System.currentTimeMillis();
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            String agent = userAgents[random.nextInt(userAgents.length)];
            connection.setRequestProperty("User-Agent", agent);
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            long end = System.currentTimeMillis();
            long lapsed = (end - start);

            return String.format("\nURl: %s\nStatus: %d\nResponse: %d %s\nTime: %d ms\n", url, responseCode, responseCode, responseMessage, lapsed);
        }catch(IOException e) {
            return String.format("URL %s\nError: %s\n\n", url, e.getMessage());
        }
    }
}
