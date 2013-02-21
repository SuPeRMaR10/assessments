package net.peerindex.challenge.webcrawler;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import net.peerindex.challenge.webcrawler.Connections.MongoDBConnection;

import org.bson.types.ObjectId;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Transient;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.sun.syndication.feed.synd.SyndEntry;

@Entity
public class RSSItem {

	@Id
	private ObjectId id = new ObjectId();

	@Property
	private String _title;
	@Property
	private String _link;
	@Property
	private Date _pubdate;
	@Property
	private String _source;
	@Indexed 
	private Status _status;
	@Transient
	private static Datastore _ds;
	
	public static enum Status {RSS, HTML, NEWS, NER, OK};
	
	
	public RSSItem(SyndEntry rssitem, String source){
		_title = rssitem.getTitle();
		_link = rssitem.getLink();
		_pubdate = rssitem.getPublishedDate();
		_source = source;
		_status = Status.RSS;
		
		initializePersistence();
	}
	
	public RSSItem(String title, String link, Date pubdate, String source, Status status){
		_title = title;
		_link = link;
		_pubdate = pubdate;
		_source = source;
		
		initializePersistence();
	}

	public RSSItem(Element elem){
		_title = elem.getChildText("title");
		_link = elem.getChildText("link");
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
		try {
			_pubdate = df.parse(elem.getChildText("pubDate"));
		} catch (ParseException e) {
			_pubdate = new Date();
		}
		_source = elem.getAttributeValue("source");
		
		initializePersistence();
	}

	private void initializePersistence(){
		try {
			_ds = MongoDBConnection.getDatastore(RSSItem.class,Config.getProperty("MongoDB_DB_Name"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToDocument(Document doc){
		Element staff = new Element("news_item");

		staff.setAttribute(new Attribute("id", "1"));
		staff.addContent(new Element("title").setText(_title));
		staff.addContent(new Element("link").setText(_link));
		staff.addContent(new Element("pubDate").setText(""+_pubdate));
		staff.addContent(new Element("source").setText(_source));

		doc.getRootElement().addContent(staff);
	}

	public String getTitle(){
		return _title;
	}

	public String getLink(){
		return _link;
	}

	public Date getPublishedDate(){
		return _pubdate;
	}

	public String getSource(){
		return _source;
	}

	public void save(){
        // Insert the user into the database	
		_ds.save(this);
	}

}
