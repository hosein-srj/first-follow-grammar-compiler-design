import java.util.ArrayList;


public class Rule{

	ArrayList<String> sets = new ArrayList<>();
	char ch;
	boolean firstFlag;
	boolean followFlag;
	public Rule(char ch ){
		this.ch = ch;
		firstFlag=false;
		followFlag=false;
	}
	public void addSet(String st){
		sets.add(st);
	}

}