package net.peerindex.challenge.webcrawler;

import java.net.URL;
import java.util.Iterator;

/**
 * Make HTTP requests as fast as possible using a stream of URL's as input, and storing
 * the HTTP response in a key value store.
 *
 * Failed HTTP requests do not need to be logged.
 *
 */
public interface RSSCrawler {

    public void setURLStream(Iterator<URL> iterator);

    public void initialise();

    public void execute();

    public void shutdown();

}
