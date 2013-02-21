package net.peerindex.challenge.webcrawler;

import java.util.Iterator;

/**
 * Make HTTP requests as fast as possible using a stream of URL's as input, and storing
 * the HTTP response in a key value store.
 *
 * Failed HTTP requests do not need to be logged.
 *
 */
public interface WebCrawler {

    public void setKeyValueStore(KeyValueStore store);

    public void setURLStream(Iterator<RSSItem> iterator);

    public void initialise();

    public void execute();

    public void shutdown();

}
