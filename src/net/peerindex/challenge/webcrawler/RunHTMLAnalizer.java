package net.peerindex.challenge.webcrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class RunHTMLAnalizer {
	private Hashtable<String, Integer> _wordcounter;

	public RunHTMLAnalizer(){
		_wordcounter = new Hashtable<String, Integer>();
	}

	public boolean isName(String st){
		return st.charAt(0)==Character.toUpperCase(st.charAt(0)) && Character.isLetter(st.charAt(0));
	}
	
	public void run(){
		File folder = new File(Config.getProperty("webcrawler_output_file_path"));
		File[] listOfFiles = folder.listFiles();
		String filecontent, token;
		StringTokenizer st;
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        System.out.println(file.getName());
				try {
					filecontent = ArticleExtractor.INSTANCE.getText(new FileReader(file));
					st = new StringTokenizer(filecontent);
					 
					System.out.println("---- Split by space ------");
					while (st.hasMoreElements()) {
						token = (String) st.nextElement();
						//System.out.println(token);
						if(isName(token)){
							System.out.println(token);
							if(_wordcounter.containsKey(token))
								_wordcounter.put(token,_wordcounter.get(token)+1);
							else
								_wordcounter.put(token,1);
						}
					}
					
				} catch (BoilerpipeProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
	}

	public void shutdown(){
		for (Entry<String, Integer> entry : _wordcounter.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
