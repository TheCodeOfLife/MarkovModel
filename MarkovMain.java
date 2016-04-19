
public class MarkovMain {

	public static void main(String[] args){
        IModel model = new WordMarkovModel(); // this is the only change!
        SimpleViewer view = new SimpleViewer("COMP 550 Markov Generation", "k count>");
        view.setModel(model);
    }
	
}
