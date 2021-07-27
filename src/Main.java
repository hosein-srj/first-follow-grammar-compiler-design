import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main{


	//ArrayList<Rule> rules = new ArrayList<Rule>();
	public static void main(String[] args) throws IOException{

		Map <Character , ArrayList<String>> rules = new HashMap<>();
		Map <Character , ArrayList<Character>> first = new HashMap<>();
		Map <Character , ArrayList<Character>> follow = new HashMap<>();
		ArrayList<Character> chars = new ArrayList<>();
		ArrayList<Character> terminals = new ArrayList<>();





		File file = new File("test.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));
		for(char v='A' ; v<='Z' ;v++){
			first.put(v, new ArrayList<Character>());
			follow.put(v, new ArrayList<Character>());
		}


		readRulesFromFile(chars,rules,br);
		br.close();

		printRules(chars,rules);

		first = camputeFirst(chars,rules,first);
		removeDubplicate(chars,first);
		printFirst(chars,first);


		follow = computeFollow(chars,rules,follow,first);
		removeDubplicate(chars,follow);
		removeEpsilon(chars,follow);
		printFollow(chars,follow);

		computerTerminls(chars,terminals,first,follow);
		prntTerminal(terminals);
/*
		Map<Character, ArrayList<String>> LL1 = new HashMap<>();
		for(Character c : chars){
			LL1.put(c, new ArrayList<String>());
		}
		Map <Character , ArrayList<Character>> f = new HashMap<>();
		for(char v='A' ; v<='Z' ;v++){
			f.put(v, new ArrayList<Character>());
		}
		f = camputeFirst(chars, rules, f,LL1);
		System.out.println();
		for(Character c : chars){
			for(String s : LL1.get(c)){
				System.out.print(s + "\t");
			}
			System.out.println();
		}*/

	}









/*
	private static Map<Character, ArrayList<Character>> camputeFirst(ArrayList<Character> chars,
			Map<Character, ArrayList<String>> rules, Map<Character, ArrayList<Character>> f,
			Map<Character, ArrayList<String>> LL1) {
		// TODO Auto-generated method stub
		for(Character ch : chars){
			findFirstOfRule(ch,f,rules,LL1);
		}

		return  f;
	}





	private static void findFirstOfRule(Character ch, Map<Character, ArrayList<Character>> first,
			Map<Character, ArrayList<String>> rules, Map<Character, ArrayList<String>> LL1) {
		// TODO Auto-generated method stub
		if( first.get(ch).size()>0)
			return;
		int x=0;
		for( String st : rules.get(ch)){
			x=0;
			for(int i=0 ; i <st.length() ; i++){
				ArrayList<Character> t = first.get(ch);
				if((st.charAt(i)>='a' && st.charAt(i)<='z' )||(st.charAt(i)>=35 && st.charAt(i)<=46 )){
					t.add(st.charAt(i));
					first.replace(ch, t);
					String pp=ch+" -> "+st;
					LL1.get(ch).add(pp);
					break;
				}
				else if( st.charAt(i)>='A' && st.charAt(i)<='Z' ){
					findFirstOfRule(st.charAt(i), first, rules);
					if(epsilonInFirsNot(first.get(st.charAt(i)))){
						t.addAll(first.get(st.charAt(i)));
						break;
					}
					else{
						for(Character f : first.get(st.charAt(i))){
							if(f!='e')
								t.add(f);
						}
					}
					String pp=st.charAt(i)+" -> "+st;
					LL1.get(pp);
					first.put(ch, t);
				}
				x++;
			}
			if(x==st.length()){
				ArrayList<Character> t2 = first.get(ch);
				t2.add('e');
				first.put(ch, t2);
			}

		}

	}




*/

	private static void prntTerminal(ArrayList<Character> terminals) {
		// TODO Auto-generated method stub
		System.out.println("\nTerminals: ");
		for(Character ch : terminals)
			System.out.print(ch + " ");
	}

	private static void computerTerminls(ArrayList<Character> chars, ArrayList<Character> terminals,Map<Character, ArrayList<Character>> first, Map<Character, ArrayList<Character>> follow) {
		// TODO Auto-generated method stub
		for(Character ch : chars){
			terminals.addAll(first.get(ch));
			terminals.addAll(follow.get(ch));
		}
		Collections.sort(terminals);
		removeDubplicate(terminals);
	}

	private static void removeDubplicate(ArrayList<Character> terminals) {
		// TODO Auto-generated method stub
		for(int i=0 ; i<terminals.size()-1 ; i++){
			if(terminals.get(i)==terminals.get(i+1) || terminals.get(i)=='e'){
				terminals.remove(i);
				i--;
			}
		}
	}






	private static void readRulesFromFile(ArrayList<Character> chars, Map<Character, ArrayList<String>> rules, BufferedReader br) throws IOException {
		// TODO Auto-generated method stub
		String st;
		while ((st = br.readLine()) != null){
			char ch = st.charAt(0);
			try{
				rules.get(ch).get(0);
			}catch(NullPointerException e){
				chars.add(ch);
			}

			st = st.substring(5);
			if (rules.get(ch)==null){
				rules.put(ch, new ArrayList<>());
				ArrayList<String> tmp = rules.get(ch);
				tmp.add(st);
				rules.replace(ch, tmp);
			}
			else{
				ArrayList<String> tmp = rules.get(ch);
				tmp.add(st);
				rules.replace(ch, tmp);
			}
		}
	}



	private static void printFollow(ArrayList<Character> chars, Map<Character, ArrayList<Character>> follow) {
		// TODO Auto-generated method stub
		System.out.println("\nFollow: ");
		for(char g : chars){
			System.out.print(g + ": { ");
			for(int i=0 ; i< follow.get(g).size() ; i++){
				if(i!=follow.get(g).size()-1)
					System.out.print(follow.get(g).get(i) + " , ");
				else
					System.out.print(follow.get(g).get(i) + " }\n");
			}
		}
	}

	private static void printFirst(ArrayList<Character> chars, Map<Character, ArrayList<Character>> first) {
		// TODO Auto-generated method stub
		System.out.println("\nFirsts: ");
		for(char g : chars){
			System.out.print(g + ": { ");
			for(int i=0 ; i< first.get(g).size() ; i++){
				if(i!=first.get(g).size()-1)
					System.out.print(first.get(g).get(i) + " , ");
				else
					System.out.print(first.get(g).get(i) + " }\n");
			}
		}
	}

	private static void printRules(ArrayList<Character> chars, Map<Character, ArrayList<String>> rules) {
		// TODO Auto-generated method stub
		for(char g : chars){
			for(String j: rules.get(g)){
				System.out.println(g + " -> "+ j );
			}

		}
	}

	private static void removeDubplicate(ArrayList<Character> chars, Map<Character, ArrayList<Character>> first) {
		// TODO Auto-generated method stub
		for(Character ch : chars){
			Collections.sort(first.get(ch));
			for(int i=0;i< first.get(ch).size()-1 ; i++){
				if(first.get(ch).get(i)==first.get(ch).get(i+1)){
					first.get(ch).remove(i);
					i--;
				}
			}
		}
	}


	private static void removeEpsilon(ArrayList<Character> chars, Map<Character, ArrayList<Character>> first) {
		// TODO Auto-generated method stub
		for(Character ch : chars){
			Collections.sort(first.get(ch));
			for(int i=0;i< first.get(ch).size() ; i++){
				if(first.get(ch).get(i)=='e'){
					first.get(ch).remove(i);
					i--;
				}
			}
		}
	}


	public static Map<Character, ArrayList<Character>> computeFollow(ArrayList<Character> chars,Map<Character, ArrayList<String>> rules, Map<Character, ArrayList<Character>> follow, Map<Character, ArrayList<Character>> first) {
		// TODO Auto-generated method stub
		follow.get(chars.get(0)).add('$');
		for(Character ch : chars){

			findFollowOfRule(ch,chars,rules,follow,first);

			for(String st : rules.get(ch)){
				for(int i=0 ; i< st.length() ; i++){
					if(st.charAt(i)>='A' && st.charAt(i)<='Z'){

					}
				}
			}
		}

		return follow;
	}

	private static void findFollowOfRule(Character ch, ArrayList<Character> chars, Map<Character, ArrayList<String>> rules,Map<Character, ArrayList<Character>> follow, Map<Character, ArrayList<Character>> first) {
		// TODO Auto-generated method stub
		/*if(follow.get(ch).size()>0)
			return;*/
		for(Character chs : chars){
			for(String st : rules.get(chs)){
				for(int i=0 ; i< st.length() ; i++){
					if(st.charAt(i)==ch){
						if(i==st.length()-1 && ch!=chs){
							findFollowOfRule(chs, chars, rules, follow, first);
							follow.get(ch).addAll( follow.get(chs) );
						}
						else if(i!=st.length()-1){
							if((st.charAt(i+1)>='a' && st.charAt(i+1)<='z' )||(st.charAt(i+1)>=35 && st.charAt(i+1)<=46 )){
								follow.get(ch).add(st.charAt(i+1));
								continue;
							}
							if(st.charAt(i+1)>='A' && st.charAt(i+1)<='Z' ){
								follow.get(ch).addAll( first.get(st.charAt(i+1)) );
								for(int j=i+1 ; j<st.length() && firstOfCharEpsilon(first,st.charAt(j)) ; j++){
									if(j==st.length()-1){
										findFollowOfRule(chs, chars, rules, follow, first);
										follow.get(ch).addAll( follow.get(chs) );
									}
									else{
										follow.get(ch).addAll( first.get(st.charAt(j+1)) );
									}

								}
							}

						}
					}
				}
			}
		}
	}



	private static boolean firstOfCharEpsilon(Map<Character, ArrayList<Character>> first, char charAt) {
		// TODO Auto-generated method stub
		for(Character ch : first.get(charAt)){
			if(ch=='e')
				return true;
		}
		return false;
	}



	public static Map<Character, ArrayList<Character>> camputeFirst(ArrayList<Character> chars, Map<Character, ArrayList<String>> rules, Map<Character, ArrayList<Character>> first) {
		// TODO Auto-generated method stub
		for(Character ch : chars){
			findFirstOfRule(ch,first,rules);
		}

		return  first;
	}

	public static void findFirstOfRule(Character ch, Map<Character, ArrayList<Character>> first, Map<Character, ArrayList<String>> rules) {
		// TODO Auto-generated method stub
		if( first.get(ch).size()>0)
			return;
		int x=0;
		for( String st : rules.get(ch)){
			x=0;
			for(int i=0 ; i <st.length() ; i++){
				ArrayList<Character> t = first.get(ch);
				if((st.charAt(i)>='a' && st.charAt(i)<='z' )||(st.charAt(i)>=35 && st.charAt(i)<=46 )){
					t.add(st.charAt(i));
					first.replace(ch, t);
					break;
				}
				else if( st.charAt(i)>='A' && st.charAt(i)<='Z' ){
					findFirstOfRule(st.charAt(i), first, rules);
					if(epsilonInFirsNot(first.get(st.charAt(i)))){
						t.addAll(first.get(st.charAt(i)));
						break;
					}
					else{
						for(Character f : first.get(st.charAt(i))){
							if(f!='e')
								t.add(f);
						}
					}
					first.put(ch, t);
				}
				x++;
			}
			if(x==st.length()){
				ArrayList<Character> t2 = first.get(ch);
				t2.add('e');
				first.put(ch, t2);
			}

		}

	}

	private static boolean epsilonInFirsNot(ArrayList<Character> arrayList) {
		// TODO Auto-generated method stub
		for (Character ch : arrayList){
			if(ch=='e')
				return false;
		}
		return true;
	}
}