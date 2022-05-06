import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Document {
	
    public String name;

    public TwoWayUnorderedListWithHeadAndTail<Link> link; 	// OneWayLinkedList<Link> links;
    
    public Document(String name, Scanner scan) {

            link = new TwoWayUnorderedListWithHeadAndTail<Link>(); // links = new OneWayLinkedList<>();
            this.name = name;
            
            String contents = " ";
            
            File file = new File(name+".txt");
            
            if(file.exists() && !file.isDirectory()) {	
    			try (Scanner sc = new Scanner(file)) {
    				
    				while(sc.hasNext() && !contents.equals("eod")) {
    					
    					contents = sc.next();
    					//System.out.println(contents);
    					
    					//check if string starts with link=
    					//divide string and take the part after link= and check if its a correct, if correct -> print
    					//create an array, first will be link, second the id
    					
    					boolean result=contents.contains("=");
    					if(result) {
    						String[] res = contents.split("[=]", 2); //if contains an = -> split
    						if(res[0].toUpperCase().equals("LINK")) { //check if it starts by link (first string of the split)
    							if(!res[1].equals(null) && correctLink(res[1])) {
    								//System.out.println(res[1]);
    								Link list = new Link(res[1]);
		                            link.add(list);
    								
    							}
    						}
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
                load(scan);
            }
    }

    public void load(Scanner scan) {
        String documentLink = "link=";
        String documentEnd = "eod";
        String line = scan.nextLine().toLowerCase();
        
        while (!line.equalsIgnoreCase(documentEnd)) {
            String[] arr = line.split(" ");
            for (String word : arr) {
                if (word.startsWith(documentLink)) {
                    String links = word.substring(documentLink.length());
                    if (correctLink(links)) {
                    	Link list = new Link(links);
    					link.add(list);
                    }
                }
            }
            line = scan.nextLine().toLowerCase();
        }
    }

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

    @Override
    public String toString() {
		String ret = "Document: " + name;
		String element=null;
		for(int i=0;i<link.size();i++) {
			element=link.get(i).ref;
			//element=link.get(i).ref;
			ret = ret + "\n" + element;
		}
		
		return ret;
	}
    

	public String toStringReverse() {
		String retStr = "Document: " + name;
		return retStr + link.toStringReverse();
	}


}

