package net.peerindex.challenge.webcrawler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;


public class MyWebCrawlerAgent implements Runnable {
	private KeyValueStore _kvstore;
	private Thread _t;
	private String _agentname; 
	private Iterator<RSSItem> _iter;
	
	public MyWebCrawlerAgent(KeyValueStore kvstore, Iterator<RSSItem> _urlstream, String agentname){
		_iter = _urlstream;
		_kvstore = kvstore;
		_agentname = agentname;
	    _t = new Thread(this, _agentname);
	    System.out.println("Agent " + _agentname + " was created ");
	}
	
	public void saveUrl(File file, URL url) throws IOException
    {
    	BufferedInputStream in = null;
    	FileOutputStream fout = null;
    	try
    	{
    		in = new BufferedInputStream(url.openStream());
    		fout = new FileOutputStream(file);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1)
    		{
    			fout.write(data, 0, count);
    		}
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    }
	
	public void run() {
		RSSItem url;
		int i=0;
		
		while (_iter.hasNext()) {
			
			url = _iter.next();
			if(!_kvstore.contains(url.toString())){
				try {
					System.out.println("\nAgent " + _agentname + " --> " + url.getTitle() + ":" + url.getLink());
					_kvstore.put(url.toString(), "");
					this.saveUrl(new File(Config.getProperty("webcrawler_output_file_path") + "/" + Config.getProperty("webcrawler_output_filename_part") + "_" + _agentname + "_" + String.format("%05d",i++)+".html"), new URL(url.getLink()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	    System.out.println("Agent " + _agentname + " is dead ");
	}
	
	public void startCrawling() throws InterruptedException{
		_t.start();
	}
}
