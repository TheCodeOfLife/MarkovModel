import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


/*
 * 	ASSIGNMENT: Markov Modeling
 * 	AUTHOR:		Luke Tannenbaum
 * 	DUE:		4/18/16
 * 	CLASS:		Comp 550
 * 
 */
public class WordMarkovModel extends AbstractModel {
	private String myString;
    private Random seed;
    private String lastString;
    private String[] words;
    
    private TreeMap<WordNgram, ArrayList<WordNgram>> markMap;
    
    public WordMarkovModel(){
    	seed = new Random(3609);
    	markMap = new TreeMap<WordNgram, ArrayList<WordNgram>>();
    }

    public void initialize(Scanner s) {
    	double startTime = System.currentTimeMillis();
    	readChars(s);
    	words = myString.split("\\s+");
    	int wordCount = words.length;
    	double endTime = System.currentTimeMillis();
    	double ellapsed = (endTime-startTime)/1000;
    	super.messageViews("#read: "+wordCount+"words in"+ellapsed+"sec");
	}

    public void process(Object o) {
		String tempString = (String)o;
		String[] numbers = tempString.split("\\s+");
		int i = Integer.parseInt(numbers[0]);
		int wordCount = 100;
		if(numbers.length>1){
			wordCount = Integer.parseInt(numbers[1]);
		}
		goodMarkov(i, wordCount);
		
	} 
    
    public void goodMarkov(int i, int letters){
		if(markMap.size()==0) markMap=makeMap(i);
		else{
			List<WordNgram> keys = new ArrayList<WordNgram>(markMap.keySet());
			WordNgram firstKey = keys.get(0);
			if(firstKey.howManyWords()!=i||lastString!=myString){
				markMap=makeMap(i);
			}
		}
		
		int start = seed.nextInt(words.length-i+1);
		WordNgram string = new WordNgram(words, start, i);
		StringBuilder builder = new StringBuilder();
		
		double startTime = System.currentTimeMillis();
		for(int j=0; j<letters; j++){
			ArrayList<WordNgram> nextNgrams = markMap.get(string);
			int toPick = seed.nextInt(nextNgrams.size());
			WordNgram next = nextNgrams.get(toPick);
			String nextToString = next.lastString();
			builder.append(nextToString + " ");
			string=next;
		}
		double finishTime = System.currentTimeMillis();
		double ellapsed = (finishTime-startTime)/1000.0;
		this.messageViews("Time to gen:"+ellapsed);
		builder.append("\n\n\n");
		this.notifyViews(builder.toString());
		
	} 
    
    public TreeMap<WordNgram, ArrayList<WordNgram>> makeMap(int k){
    	ArrayList<WordNgram> gramList = new ArrayList<WordNgram>();

    	TreeMap<WordNgram, ArrayList<WordNgram>> map = new TreeMap<WordNgram, ArrayList<WordNgram>>();
    	String[] wrapAround = new String[words.length+k];
    	
    	for(int i = 0; i<words.length; i++){
    		wrapAround[i] = words[i];
    	}
    	for(int j = 0; j < k; j++){
    		wrapAround[words.length+j] = words[j];
    	}
    	    	
    	for (int i = 0; i < words.length; i++) {
    		
    		WordNgram kWords = new WordNgram(wrapAround, i, k);
    		if(map.containsKey(kWords)){ gramList = map.get(kWords); }
    		else{ gramList = new ArrayList<WordNgram>(); }
    		WordNgram next = new WordNgram(wrapAround, i+1, k);
    		gramList.add(next);
    		
    		map.put(kWords, gramList);
    	}
    		
    	return map; 
    }
    
    private int readChars(Scanner s){
    	lastString = myString;
    	myString = s.useDelimiter("\\Z").next();
    	s.close();
    	return myString.length();
    }
   
}
