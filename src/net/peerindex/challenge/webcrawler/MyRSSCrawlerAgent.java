package net.peerindex.challenge.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import com.google.code.morphia.Datastore;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class MyRSSCrawlerAgent implements Runnable{

	private Thread _t;
	private Iterator<URL> _urlstream;

	public MyRSSCrawlerAgent(Iterator<URL> urlstream, String _agentname){
		_urlstream = urlstream;
		_t = new Thread(this, _agentname);
	}

	public void run(){
		URL url = null;
		
		while(_urlstream.hasNext()){
			url = _urlstream.next();

			XmlReader xmlReader = null;
			try {

				System.out.println(_t.getName()+"-> Downloading RSS Feeds from " + url.toString());
				xmlReader = new XmlReader(url);
				SyndFeed feeder = new SyndFeedInput().build(xmlReader);

				System.out.println(_t.getName()+"-> Processing RSS Feeds from " + url.toString());
				Iterator iterator = feeder.getEntries().iterator();
				while(iterator.hasNext()) 
					new RSSItem((SyndEntry) iterator.next(), url.toString()).save();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FeedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (xmlReader != null)
					try {
						xmlReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		System.out.println(_t.getName()+" is dead");
	}

	public void startCrawling() {
		_t.start();		
	}
}
