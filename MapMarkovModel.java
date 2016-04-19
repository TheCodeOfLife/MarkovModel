import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MapMarkovModel extends AbstractModel{

	private String input;
	private Random seed;
	private int numToPrint = 100;

	private HashMap<String, ArrayList<Character>> markMap;

	public MapMarkovModel(){
		seed = new Random(3609);
		markMap = new HashMap<String, ArrayList<Character>>();
	}

	public void initialize(Scanner scan) {
		double startTime = System.currentTimeMillis();
		int count = read(scan);
		double endTime = System.currentTimeMillis();
		double ellapsed = (endTime-startTime)/1000.0;
		super.messageViews("#read: "+count+" chars in: "+ellapsed+" secs");
	}

	public void process(Object ob) {
		String toString = (String) ob;
		String[] ints = toString.split("\\s+");
		int i = Integer.parseInt(ints[0]);
		int letters = 500;
		if(ints.length > 1){
			letters = Integer.parseInt(ints[1]);
		}
		goodMarkov(i,letters);
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
		double ellapsed = (finishTime-startTime)/1000.0;
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
