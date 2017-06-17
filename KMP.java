

/* 

Rahnuma Islam Nishat - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP 
{
   private static String pattern;
   

   private static int[][] dfa;
   
   //constructs the DFA for KMP substring search
   public KMP(String pattern)
   {
   		this.pattern = pattern;
   		int len = pattern.length();
   		dfa = new int[256][len];

   		dfa[pattern.charAt(0)][0] = 1;
   		//for each state j, dfa[pat.charAt(j)][j] = j+1
   		int i = 0;
   		for(int j = 1; j < len; j++){
   			//for each state j and char c != pat.charAt(j)
   			for(int c = 0; c < 256; c++){  
   				dfa[c][j] = dfa[c][i];
   			}
   			//then update i = dfa[pat.charAt(j)][i]
   			dfa[pattern.charAt(j)][j] = j+1;
   			i = dfa[pattern.charAt(j)][i];
   		}
   }
   
   public static int search(String txt)
   {  
   	   int i = 0;
   	   int j = 0;
   		for(i = 0; i < txt.length() && j < pattern.length(); i++){
   			j = dfa[txt.charAt(i)][j];
   		}
   		if(j == pattern.length()){
   			return i - pattern.length();
   		}
   		return txt.length();
   }

        
  	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text+=s.next()+" ";
			}
			
			for(int i=1; i<args.length ;i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i]+ " was not found.");
				else System.out.println("The string \""+args[i]+ "\" was found at index "+index + ".");
			}
			
			//System.out.println(text);
			
		}else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
		
		
	}
}




