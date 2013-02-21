package net.peerindex.challenge.webcrawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

	public static String getProperty(String property) {
		Properties configFile = new Properties();
		try {
			configFile.load(new FileInputStream("config.properties"));
			return configFile.getProperty(property);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
}
