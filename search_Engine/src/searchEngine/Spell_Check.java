package searchEngine;

import java.util.Iterator;

import searchEngine.SET;
import textprocessing.In;
import textprocessing.StdIn;
import textprocessing.StdOut;

public class Spell_Check {

	public static void checker(SET<String> dictionary, String key, TST<Integer> st) {    			
		Iterator<String> it = dictionary.iterator();
		
		while(it.hasNext()) {
			String word = it.next();
			int n = word.length();
		    int m = key.length();
		    int dp[][] = new int[n+1][m+1];
		    
		    for (int i = 0; i <= n; i++) {
		        for (int j = 0; j <= m; j++) {
		            if (i == 0)
		                dp[i][j] = j;      
		            else if (j == 0)
		                dp[i][j] = i; 
		            else if (word.charAt(i-1) == key.charAt(j-1))
		                dp[i][j] = dp[i-1][j-1];                
		            else if (i>1 && j>1  && word.charAt(i-1) == key.charAt(j-2) 
		                    && word.charAt(i-2) == key.charAt(j-1))
		                dp[i][j] = 1 + Math.min(Math.min(dp[i-2][j-2], dp[i-1][j]), Math.min(dp[i][j-1], dp[i-1][j-1]));
		            else
		                dp[i][j] = 1 + Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1])); 
		        }	      
		    }
		} 
		
		if (dictionary.ceiling(key) != null) {
        	StdOut.println("Did you mean: " + dictionary.ceiling(key));	            	
        	StdOut.println(st.get(dictionary.ceiling(key).toLowerCase()));
        	for (String result : st.keys()) {
				if (result.matches(dictionary.ceiling(key).toLowerCase())) {
					StdOut.println("Showing Results for " + dictionary.ceiling(key));
					StdOut.println("Search " + result + " found at position " + st.get(result));
				}
	        }
        } 
    } 
}
