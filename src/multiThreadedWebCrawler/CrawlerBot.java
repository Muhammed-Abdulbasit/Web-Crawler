package multiThreadedWebCrawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CrawlerBot implements Runnable {
    private static final int MAX_DEPTH = 3;
    private Thread thread;
    private String first_link;
    private ArrayList<String> visitedLinks = new ArrayList<>();
    private int ID;

    public CrawlerBot(String link, int num) {
        System.out.println("CrawlerBot created, ID: " + num);
        first_link = link;
        ID = num;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        crawl(1, first_link);
        System.out.println("Bot ID: " + ID + " Finished crawling");
    }

    private void crawl(int level, String url) {
        if (level <= MAX_DEPTH) {
            Document doc = request(url);

            if (doc != null) {
                sendVisitedLink(url); // This function sends the URL back to the server for display
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if (!visitedLinks.contains(next_link)) {
                        visitedLinks.add(next_link);
                        try {
                            CrawlerController.checkPaused(); // Check if the crawler has been paused
                            crawl(level + 1, next_link);  // Increment level properly
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("Bot ID: " + ID + " was interrupted.");
                        }
                    }
                }
            }
        }
    }

    private void sendVisitedLink(String link) {
        try {
            URL url = new URL("http://localhost:8080");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/plain");

            OutputStream os = conn.getOutputStream();
            os.write(link.getBytes());
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Bot ID:" + ID + " Failed to send link to server, HTTP response code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            System.out.println("Bot ID:" + ID + " Error sending link to server: " + e.getMessage());
        }
    }

    private Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                System.out.println("Bot ID: " + ID + " Received Webpage at " + url);
                return doc;
            }
        } catch (IOException e) {
            System.out.println("Bot ID: " + ID + " Error fetching the page: " + url);
        } catch (IllegalArgumentException e) {
            System.out.println("Bot ID: " + ID + " Provided URL is invalid: " + url);
        }
        return null;
    }

    public Thread getThread() {
        return thread;
    }
}
