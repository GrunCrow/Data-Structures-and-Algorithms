package dsaa.lab04;

import java.util.ListIterator;
import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
		//TODO
	}

	public static boolean isCorrectId(String id) {
		//TODO
		return false;
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	private static Link createLink(String link) {
		//TODO
		return null;
	}

	@Override
	public String toString() {
		String retStr="Document: "+name;
		//TODO
		return retStr;
	}

	public String toStringReverse() {
		String retStr="Document: "+name;
		ListIterator<Link> iter=link.listIterator();
		while(iter.hasNext())
			iter.next();
		//TODO
		while(iter.hasPrevious()){
		}
		return retStr;
	}
}

