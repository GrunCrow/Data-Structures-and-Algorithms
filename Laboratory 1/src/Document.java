import java.util.Scanner;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

public class Document {
	public static void loadDocument(String name, Scanner scan) {
		
		//if(correctLink(name))
		//if(!name.isEmpty()) {
		String contents = " ";
			
		File file = new File(name+".txt");
		
		if(file.exists() && !file.isDirectory()) {	
			try (Scanner sc = new Scanner(file)) {
				
				while(sc.hasNext() && !contents.equals("eod")) {
					
					contents = sc.next();
					//System.out.println(contents);
					
					//check if string strts with link=
					//divide string and take the part after link= and check if its a correct, if correct -> print
					//create an array, first will be link, second the id
					
					boolean result=false;
					if(result = contents.contains("=")) {
						String[] res = contents.split("[=]", 2); //if contains an = -> split
						if(res[0].toUpperCase().equals("LINK")) //check if it starts by link (first string of the split)
							if(!res[1].equals(null) && correctLink(res[1]))
								System.out.println(res[1]);
					}
					
					
					//if (res[1].toUpperCase()=="LINK") {
					//	System.out.println(res[2]);						
					//}
				}
			}catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		}
		else {
			String marker = "link=";
			String endMarker = "eod";
			String line = scan.nextLine().toLowerCase();
			while (!line.equalsIgnoreCase(endMarker)) {
				String arr[] = line.split(" ");
				for (String word : arr) {
					if(word.startsWith(marker)) {
						String link = word.substring(marker.length());
					if(correctLink(link))
						System.out.println(link);
				}
				}
				line = scan.nextLine().toLowerCase(); // next loop
			}	
		}
	}
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static boolean correctLink(String link) {
		boolean result = false;
		//boolean result = link.matches("[a-zA-Z]+");
		if(!link.isEmpty()) {
			//check if first character is _
			char firstChar = link.charAt(0);
			if(firstChar!='_' && !Character.isDigit(firstChar))	
				//check full string
				result = link.matches("[a-zA-Z0-9_]+");
		}	
			
		return result;
	}

}
