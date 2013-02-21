package net.peerindex.challenge.webcrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class MyRSSCrawlerAgentXML implements Runnable{

	private String _path;
	private Thread _t;
	private Iterator<URL> _urlstream;

	public MyRSSCrawlerAgentXML(Iterator<URL> urlstream, String outputpath, String _agentname){
		_urlstream = urlstream;
		_path = outputpath;
		_t = new Thread(this, _agentname);
	}

	public void run(){
		URL url = null;
		
		while(_urlstream.hasNext()){
			url = _urlstream.next();

			Element company = new Element("company");
			Document doc = new Document(company);
			doc.setRootElement(company);

			XmlReader xmlReader = null;
			try {

				System.out.println(_t.getName()+"-> Downloading RSS Feeds from " + url.toString());
				xmlReader = new XmlReader(url);
				SyndFeed feeder = new SyndFeedInput().build(xmlReader);

				System.out.println(_t.getName()+"-> Processing RSS Feeds from " + url.toString());
				Iterator iterator = feeder.getEntries().iterator();
				while(iterator.hasNext()) 
					new RSSItem((SyndEntry) iterator.next(), url.toString()).writeToDocument(doc);

				XMLOutputter xmlOutput = new XMLOutputter();
				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				String[] arr = url.getFile().split("/");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:MM:SS");
				xmlOutput.output(doc, new FileWriter(_path + "/"+ dateFormat.format(new Date()) + url.getHost() + "-" + arr[arr.length-1] + ".xml"));


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
