package multiThreadedWebCrawler;

public class CrawlerController {
    private static volatile boolean paused = false;
    private static final Object pauseLock = new Object();

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public static void checkPaused() throws InterruptedException {
        synchronized (pauseLock) {
            while (paused) {
                pauseLock.wait();
            }
        }
    }
}
