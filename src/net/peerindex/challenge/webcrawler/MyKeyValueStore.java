package net.peerindex.challenge.webcrawler;

import java.util.AbstractList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

public class MyKeyValueStore implements KeyValueStore{

	private Hashtable<String,String> _hashtable;
	
	public MyKeyValueStore(){
		_hashtable = new Hashtable<String, String>();
	}
	
	@Override
	public boolean contains(String key) throws IllegalArgumentException{
		if(key==null)
			throw new IllegalArgumentException();
		else
			return _hashtable.containsKey(key);
	}

	@Override
	public String get(String key){
		if(key==null)
			throw new IllegalArgumentException();
		else
			return _hashtable.get(key);
	}

	@Override
	public boolean put(String key, String value) {
		if(key==null || value==null)
			throw new IllegalArgumentException();
		else{
			return null == _hashtable.put(key,value);
		}
	}

	@Override
	public boolean delete(String key) {
		if(key==null)
			throw new IllegalArgumentException();
		else{
			return null != _hashtable.remove(key);
		}
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return _hashtable.entrySet();
	}
	

}
