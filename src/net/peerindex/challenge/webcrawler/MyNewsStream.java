package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class MyNewsStream implements Iterator<RSSItem> {
	private int _currentfile = 0;
	private int j =0;
	private List<File> _listOfFiles = new ArrayList<File>();
	private Iterator<Element> _elementiterator;


	public MyNewsStream(String path) {
		File[] filearr = new File(path).listFiles();

		System.out.println("Input folder: "+path);
		for(int i = 0 ; i<filearr.length-1 ; i++){
			File file = filearr[i];
			if(file.isFile() && !file.isDirectory() && !file.isHidden())
				_listOfFiles.add(file);

		}

		_elementiterator = giveIteratorFile(_listOfFiles.get(_currentfile));
	}


	@Override
	public boolean hasNext() {
		return _currentfile+1 < _listOfFiles.size() || _elementiterator.hasNext();
	}

	public RSSItem next() {
		try{
			return new RSSItem(_elementiterator.next());
		}catch(NoSuchElementException ex){
			if(_currentfile<_listOfFiles.size())
				_elementiterator = giveIteratorFile(_listOfFiles.get(_currentfile++));
		}
		System.out.println(_currentfile+1+":"+j);
		return next();
	}



	private Iterator<Element> giveIteratorFile(File file){
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(file);
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Element root = doc.getRootElement();
		List<Element> children = root.getChildren();
		return children.iterator();
	}


	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
