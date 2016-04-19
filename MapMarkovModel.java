import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 * 	ASSIGNMENT: Markov Modeling
 * 	AUTHOR:		Luke Tannenbaum
 * 	DUE:		4/18/16
 * 	CLASS:		Comp 550
 * 
 */
public class MapMarkovModel extends AbstractModel{

	private String input;
	private Random seed;
	private int numToPrint = 1600;

	private HashMap<String, ArrayList<Character>> markMap;

	public MapMarkovModel(){
		seed = new Random(3609);
		markMap = new HashMap<String, ArrayList<Character>>();
	}

	public void initialize(Scanner scan) {
		double startTime = System.currentTimeMillis();
		int count = read(scan);
		double endTime = System.currentTimeMillis();
		double ellapsed = (endTime-startTime);
		super.messageViews("#read: "+count+" chars in: "+ellapsed+" secs");
	}

	public void process(Object ob) {
		String toString = (String) ob;
		String[] ints = toString.split("\\s+");
		int i = Integer.parseInt(ints[0]);
		int letters = numToPrint;
		if(ints.length > 1){
			letters = Integer.parseInt(ints[1]);
		}
		//goodMarkov(i,letters);
		double startTime = System.currentTimeMillis();
		brute(i, letters);
		double finishTime = System.currentTimeMillis();
		double ellapsed = (finishTime-startTime);
		this.messageViews("Time to gen:"+ellapsed);
	}
	
	public void brute(int k, int numLetters) {

        // pick random k-character substring as initial seed
        int start = seed.nextInt(input.length() - k + 1);
        String mySeed = input.substring(start, start + k);

        // copy first k characters to back to simulate wrap-around
        String wrapAroundString = input + input.substring(0,k);

        StringBuilder build = new StringBuilder();
        ArrayList<Character> list = new ArrayList<Character>();

        for (int i = 0; i < numLetters; i++) {
            list.clear();
            int pos = 0;
            while ((pos = wrapAroundString.indexOf(mySeed, pos)) != -1 && pos < input.length()) {
                char ch = wrapAroundString.charAt(pos + k);
                list.add(ch);
                pos++;
            }
            int pick = seed.nextInt(list.size());
            char ch = list.get(pick);
            build.append(ch);
            mySeed = mySeed.substring(1) + ch;
        }
    }

	public void goodMarkov(int i, int letters){
		if(markMap.size()==0) markMap=makeMap(i);
		else{
			List<String> keys = new ArrayList<String>(markMap.keySet());
			String firstKey = keys.get(0);
			if(firstKey.length()!=i){
				markMap=makeMap(i);
			}
		}
		
		int random = seed.nextInt(input.length()-i+1);
		String myString = input.substring(random, random+i);
		StringBuilder builder = new StringBuilder();
		
		double startTime = System.currentTimeMillis();
		for(int j=0; j<letters; j++){
			ArrayList<Character> chars = markMap.get(myString);
			int toPick = seed.nextInt(chars.size());
			char nextChar = chars.get(toPick);
			builder.append(nextChar);
			myString = myString.substring(1)+nextChar;
		}
		double finishTime = System.currentTimeMillis();
		double ellapsed = (finishTime-startTime);
		builder.append("\n\n");
		this.notifyViews(builder.toString());
		this.messageViews("Time to gen:"+ellapsed);
		
	}

	public HashMap<String, ArrayList<Character>> makeMap(int k){
		HashMap<String, ArrayList<Character>> map = new HashMap<String, ArrayList<Character>>();
		String wraparound = input + input.substring(0,k);
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i=0;i<input.length();i++){
			String chars = wraparound.substring(i,i+k);
			if(map.containsKey(chars)) list = map.get(chars);
			else{
				list = new ArrayList<Character>();
			}
			char nextChar = wraparound.charAt(i+k);
			list.add(nextChar);
			map.put(chars, list);
		}
		return map;
	}
	
	int read(Scanner s){
		input = s.useDelimiter("\\Z").next();
		s.close();
		return input.length();
	}

}
