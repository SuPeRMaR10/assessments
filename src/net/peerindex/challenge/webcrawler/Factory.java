package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new MyKeyValueStore();
    }

    public static WebCrawler createWebCrawler() {
        return new MyWebCrawler(Integer.parseInt(Config.getProperty("webcrawler_num_threads")));
    }

    public static Iterator<URL> createURLIterator() {
        return new URLStream(Config.getProperty("webcrawler_input_file_path"));
    }
    
    public static RSSCrawler createRSSCrawler(){
    	return new MyRSSCrawler(Integer.parseInt(Config.getProperty("RSSCrawler_num_threads")));
    }

	public static Iterator<RSSItem> createNewsIterator() {
		return new MyNewsStream(Config.getProperty("RSSFeeds_Output_Folder"));
	}

}
