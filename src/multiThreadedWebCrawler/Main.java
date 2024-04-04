package multiThreadedWebCrawler;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<CrawlerBot> bots = new ArrayList<>();
		bots.add(new CrawlerBot("https://abcnews.go.com", 1));
		bots.add(new CrawlerBot("https://www.npr.org", 2));
		bots.add(new CrawlerBot("https://www.nytimes.com", 3));
		
		for(CrawlerBot w : bots) {
			try {
				w.getThread().join();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
