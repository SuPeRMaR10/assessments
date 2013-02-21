package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MyRSSCrawler implements RSSCrawler{

	private Iterator<URL> _urlstream;
	private int _numberofagents;
	private ArrayList<MyRSSCrawlerAgent> _agentlist;

	public MyRSSCrawler(int numberThreads){		
		_numberofagents = numberThreads;
	}


	public void setURLStream(Iterator<URL> iterator){
		_urlstream = iterator;
	}

	public void initialise(){
		_agentlist = new ArrayList<MyRSSCrawlerAgent>();

		for(int j = 0 ; j<_numberofagents ; j++)
			_agentlist.add(new MyRSSCrawlerAgent(_urlstream, "Agent "+j));
	}

	public void execute(){

		for(int j = 0 ; j<_numberofagents ; j++){
			_agentlist.get(j).startCrawling();
		}
	}


	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}
	
	
	
	public static void main(String[] args) {
		Iterator<URL> urlstream = new URLStream(new File(Config.getProperty("RSSFeeds_Input_File")));
		RSSCrawler rsscrawler = new MyRSSCrawler(Integer.parseInt(Config.getProperty("RSSCrawler_num_threads")));
		
		rsscrawler.setURLStream(urlstream);
		
		rsscrawler.initialise();
		rsscrawler.execute();
	}
}
