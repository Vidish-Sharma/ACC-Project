package searchEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import searchEngine.SET;
import searchEngine.TST;
import searchEngine.Search_Engine;
import textprocessing.In;
import textprocessing.StdOut;

public class Engine {

	static Search_Engine engine = new Search_Engine();
	static In in;
	static TST<Integer> tst = new TST<Integer>();
	static ArrayList<String> TST_List = new ArrayList();
	static SET<String> set_dictionary = new SET<String>();
	
	public static void build_db() {		
		boolean success;
		String web_address = null;
		
		ArrayList<String> list = new ArrayList();    
		System.out.println("Enter your choice for crawling the website:");
		System.out.println("For crawling www.google.com, type 1.");
		System.out.println("For crawling www.w3.org, type 2.");
		System.out.println("For crawling any other website, type 3.");
		System.out.println("For Database entries, type 4,");
		System.out.println();
		
		Scanner key_input = new Scanner(System.in);
		int web = key_input.nextInt();
		System.out.println();
		
		switch(web) {
		case 1:
			web_address = "www.google.com";
			break;
		case 2:
			web_address = "www.w3.org";
			break;
		case 3:
			System.out.println("Enter the URL that you want to crawl:");
			Scanner key_input2 = new Scanner(System.in);
			String self_address = key_input2.next();
			web_address = self_address.trim();
			break;
		case 4:
			if (tst.size() != 0) {
				for (String result : tst.keys()) {
					StdOut.println(result + " " + tst.get(result));
				}
			}
			else {
				StdOut.println("Database is empty.");
				return;
			}			
		}
		https://www.w3.org/standards/	
		try {
			success = engine.listFilesForFolder("https://" + web_address + "/");			
			if (success == true) {
				System.out.println("Connecting to the WebUrl");				
				System.out.println("Building the DataBase....");
			} else {
				System.out.println("URL not working, Please confirm the entered URL.");
				return;
			}
			
			int i = 0;
			while (i < 10) {
				engine.listFilesForFolder(engine.findlinks().get(i));
				engine.findlinks().remove(i);	
				i++;
			}
			System.out.println();
			
		    List<String> filenames = engine.findfile();
		    while (!filenames.isEmpty()) {			   
			    try {
			  	    File fileSort = new File(filenames.get(0));			   
				    Scanner scnrSort = new Scanner(fileSort);
			    } catch (Exception e1) {filenames.remove(0);break;}
			   
			    File fileSort = new File(filenames.get(0));
			    Scanner scnrSort = new Scanner(fileSort);
			    
			    while(scnrSort.hasNextLine()){
			    	TST_List.add(scnrSort.nextLine());			   
			    }
			    scnrSort.close();						
			    filenames.remove(0);		   		   
			   	
		    }
		    for (int i1 = 0; i1 < TST_List.size(); i1++) {
			    String[] getChar = TST_List.get(i1).split(" ");
			    for (int i2=0; i2< getChar.length; i2++) {			   
				    try {
				    	tst.put(getChar[i2], i2);
				    } catch (Exception e) {e.toString();}
			    }		   
	        }

		    In dict = new In("/Users/vidishsharma/eclipse-workspace/search_Engine/src/searchEngine/words.utf-8.txt");
	        while (!dict.isEmpty()) {
	            String word = dict.readString();
	            set_dictionary.add(word);
	        }
	   } catch (IOException e) {e.toString();}
		System.out.println("Database successfully created.");
		System.out.println();
	}
	
	public static void serach_word(Search_Engine engine2) {		        		
		long start_timer = 0;
		long end_timer = 0;
		start_timer = System.nanoTime();	
		
		if (tst.size() == 0) {
			System.out.println("DataBase is empty, Please create database before searching.");
			System.out.println();
		} else {
			System.out.println("Type any Keyword to search.");
			System.out.println("For searching all the Websites, type Websites.");
			System.out.println("For searching all the Emails, type Emails.");
			System.out.println("For searching all the Phone Numbers, type Phones.");
			System.out.println();
			
			Scanner key_input = new Scanner(System.in);
			String key = key_input.next();
			System.out.println();
			System.out.println("Your Searched Keyword is : " + key);	
			
			if (tst.get(key.toLowerCase()) == null && key.toLowerCase() != set_dictionary.ceiling(key) && 
					!key.toLowerCase().matches("websites") && 
					!key.toLowerCase().matches("emails") && 
					!key.toLowerCase().matches("phones")) {
				
				Spell_Check spell = new Spell_Check();
				spell.checker(set_dictionary, key, tst);
			} else {
				for (String result : tst.keys()) {					
					if (key.toLowerCase().matches("websites")) {
						if (result.matches("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$")) {
							StdOut.println("Website " + result + " found at " + tst.get(result));
						}
					}
					else if (key.toLowerCase().matches("emails")) {
						if (result.matches("^(.+)@(.+)$")) {
							StdOut.println("Email " + result + " found at " + tst.get(result));
						}
					}
					else if (key.toLowerCase().matches("phones")) {
						if (result.matches("(\\\\d){3}-(\\\\d){3}-(\\\\d){4}")) {
							StdOut.println("Phone# " + result + " found at " + tst.get(result));
						}
					}
					else if (result.matches(key.toLowerCase())) {
						StdOut.println("Search " + result + " found at position " + tst.get(result));
					}					
		        }				
			}					
			end_timer = System.nanoTime();
			StdOut.println("Average Time Taken for search in nano seconds: " + (end_timer - start_timer));
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int choice = 1;	
		
		System.out.println("Welcome to our Search Engine!!");
		System.out.println();
		
		while (choice == 1) {
			System.out.println("Please enter your choice:");
			System.out.println("Press 1 for Creating the DataBase:");
			System.out.println("Press 2 for Searching the Database:");
			System.out.println("Press 3 for Exiting the Search Engine:");
			System.out.println("");
			
			int number = input.nextInt();
			switch (number) {
			case 1:
				System.out.println();
				System.out.println("Creating the Database now:");
				System.out.println();
				build_db();
				break;
			case 2:
				System.out.println();
				System.out.println("Searching the Database now:");
				System.out.println();
				serach_word(engine);
				break;
			case 3:
				System.out.println();
				System.out.println("Program Ends.");
				System.out.println();
				choice = 0;
			}			
		}
	}
}
