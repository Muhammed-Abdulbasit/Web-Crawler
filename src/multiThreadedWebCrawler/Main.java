package multiThreadedWebCrawler;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args)
	{

		try {
			WebServer.startPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// open the web server on hosts default browser
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://localhost:8080"));
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
		// testing...
		ArrayList<CrawlerBot> bots = new ArrayList<>();
		bots.add(new CrawlerBot("https://abcnews.go.com", 1));
		bots.add(new CrawlerBot("https://www.wikipedia.org/", 2));
		bots.add(new CrawlerBot("https://www.nytimes.com", 3));
		bots.add(new CrawlerBot("https://www.bbc.com", 4));
		bots.add(new CrawlerBot("https://stackoverflow.com/questions", 5));
		bots.add(new CrawlerBot("https://www.imdb.com/chart/top", 6));
		
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
