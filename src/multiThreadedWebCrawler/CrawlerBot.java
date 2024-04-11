package multiThreadedWebCrawler;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class CrawlerBot implements Runnable{
	private static final int MAX_DEPTH = 3;
	private Thread thread;
	private String first_link;
	private ArrayList<String> visitedLinks = new ArrayList<String>();
	private int ID;
	
	public CrawlerBot(String link, int num) {
		System.out.println("CrawlerBot created");
		first_link = link;
		ID = num;
		
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		crawl(1, first_link);
		System.out.println("Bot ID:" + ID + " Finished crawling");
	}
	
	private void crawl(int level, String url) {
		if(level <= MAX_DEPTH) {
			Document doc = request(url);

			if(doc != null) {
				sendVisitedLink(url);
				for(Element link : doc.select("a[href]")) {
					String next_link = link.absUrl("href");
					if(visitedLinks.contains(next_link) == false) {
						crawl(level++, next_link);
					}
				}
			}
		}
	}
	
	public void sendVisitedLink(String link) {
	    try {
		URL url = new URL("http://localhost:8080");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/plain");
	
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		
		writer.write(link);
		writer.flush();
		writer.close();
		os.close();
	
		int responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			System.out.println("Bot ID:" + ID + " Failed to send link to server");
		}
	
		conn.disconnect();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	
	private Document request(String url) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			if(con.response().statusCode() == 200) {
				System.out.println("Bot ID:" + ID + " Received Webpage at " + url);
				visitedLinks.add(url);
				
				return doc;
			}
			return null;
		}
		catch(IOException e) {
			return null;
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	public Thread getThread() {
		return thread;
	}
}
