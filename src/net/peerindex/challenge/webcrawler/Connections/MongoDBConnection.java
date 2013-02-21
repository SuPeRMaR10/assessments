package net.peerindex.challenge.webcrawler.Connections;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.Arrays;

import net.peerindex.challenge.webcrawler.Config;
import net.peerindex.challenge.webcrawler.RSSItem;


public class MongoDBConnection {

	private static Mongo m;
	
	private static  Mongo MongoDBGetConnection() throws UnknownHostException{
		// to connect to a replica set, supply a seed list of members
		if(m == null)
			m = new Mongo(Arrays.asList(new ServerAddress(Config.getProperty("MongoDB_Host_IP"), Integer.parseInt(Config.getProperty("MongoDB_Host_Port")))
																								//,new ServerAddress("localhost", 27018),
																								//,new ServerAddress("localhost", 27019)
																								));
		return m;
	}
	
	
	
	public static Datastore getDatastore(Class entityclass, String database) throws UnknownHostException {
		
        // Create a Morphia object and map our model classes
        Morphia morphia = new Morphia();
        morphia.map(entityclass);
        
        // Create a data store
        return morphia.createDatastore(MongoDBGetConnection(), database);
	}

}
