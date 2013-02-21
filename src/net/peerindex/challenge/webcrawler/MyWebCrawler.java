package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * Make HTTP requests as fast as possible using a stream of URL's as input, and storing
 * the HTTP response in a key value store.
 *
 * Failed HTTP requests do not need to be logged.
 *
 */
public class MyWebCrawler implements WebCrawler{

	private KeyValueStore _store;
	private Iterator<RSSItem> _urlstream;
	
	private int _numberofagents;
	private ArrayList<MyWebCrawlerAgent> _agentlist;
	
	public MyWebCrawler(int numberThreads){		
		_numberofagents = numberThreads;
	}
	
    public void setKeyValueStore(KeyValueStore store){
    	_store = store;
    }

    public void setURLStream(Iterator<RSSItem> iterator){
    	_urlstream = iterator;
    }

    public void initialise(){
    	_agentlist = new ArrayList<MyWebCrawlerAgent>();
    	
		for(int j = 0 ; j<_numberofagents ; j++)
			_agentlist.add(new MyWebCrawlerAgent(_store, _urlstream, " "+j));
		
    }

    public void execute(){
    	
		for(int j = 0 ; j<_numberofagents ; j++){
			try {
				_agentlist.get(j).startCrawling();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

    public void shutdown(){
    	
    	Iterator<Entry<String, String>> it = _store.entrySet().iterator();
    	
    	Entry<String, String> entry;
    	while (it.hasNext()) {
    		entry = it.next();
    		System.out.println(entry.getKey() + " : " + entry.getValue());
    	}
    	
    }

}
