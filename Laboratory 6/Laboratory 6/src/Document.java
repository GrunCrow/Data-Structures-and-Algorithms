import java.util.ListIterator;
import java.util.Scanner;

import java.util.Arrays;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}
	
	public void load(Scanner scan) {
        String marker="link=";
		String endMarker="eod";
		String line=scan.nextLine().toLowerCase();
		while(!line.equalsIgnoreCase(endMarker)) {
			String arr[]=line.split(" ");
			for(String word:arr) {
				if(word.startsWith(marker))
				{
					String linkStr=word.substring(marker.length());
					Link l;
					if((l=createLink(linkStr))!=null)
						link.add(l);
				}

			}
			line=scan.nextLine().toLowerCase();
		}
	}
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)	

	public static boolean isCorrectId(String id) {
		id=id.toLowerCase();
		if(id.length()==0) return false;
		if( id.charAt(0)<'a' ||id.charAt(0)>'z')
			return false;
		for(int i=1;i<id.length();i++) {
			if( !(id.charAt(i)>='a' && id.charAt(i)<='z' 
					|| id.charAt(i)>='0' && id.charAt(i)<='9'
					|| id.charAt(i)=='_'))
				return false;
		}
		return true;
	}
        
	private static Link createIdAndNumber(String id, int n) {
		if(!isCorrectId(id))
			return null;
		
		return new Link(id.toLowerCase(),n);
	}
        
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static Link createLink(String link) {
		if(link.length()==0) 
			return null;
		
		int openBracket=link.indexOf('(');
		int closeBracket=link.indexOf(')');
		if(openBracket>0 && closeBracket>openBracket && closeBracket==link.length()-1) {
			String strNumber=link.substring(openBracket+1, closeBracket);
			try {
				int number=Integer.parseInt(strNumber);
				if(number<1)
					return null;
				return createIdAndNumber(link.substring(0, openBracket),number);
			}
			catch(NumberFormatException ex) {
				return null;
			}
		}

		return createIdAndNumber(link,1);
	}

	@Override
	public String toString() { ////
		/*String retStr="Document: "+name;
		return retStr;*/
        String retStr="Document: "+name;
		int counter=0;
		for(Link linkElem:link) {
			if(counter%10==0)
				retStr+="\n";
			else
			retStr+=" ";
			retStr+=linkElem.toString();
			counter++;
		}
		return retStr;
	}

	public String toStringReverse() {   
        String retStr="Document: "+name;
		int counter=0;
		ListIterator<Link> iter=link.listIterator();
		while(iter.hasNext())
			iter.next();
		while(iter.hasPrevious()){
			if(counter%10==0)
				retStr+="\n";
			else
				retStr+=" ";
			retStr+=iter.previous().toString();
			counter++;
		}
		return retStr;
	}
        
	public int[] getWeights() {
		int[] arr=new int[link.size()];
		int i=0;
		for(Link linkElem:link) {
			arr[i++]=linkElem.weight;
		}
		return arr;
	}

	public static void showArray(int[] arr) {
		if(arr.length>0) {
			System.out.print(arr[0]);
			for(int i=1;i<arr.length;i++)
				System.out.print(" "+arr[i]);
		}
		System.out.println();
    }

	void bubbleSort(int[] arr) {
		showArray(arr);
        for(int begin=0; begin<arr.length-1; begin++) {
			for(int j=arr.length-1; j>begin; j--)
				if(arr[j-1]>arr[j])
					swap(arr,j-1,j);
			showArray(arr);
		}
	}
        
    private void swap(int[] arr, int i, int j) {
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;	
	}

	public void insertSort(int[] arr) {
		showArray(arr);
		for(int pos=arr.length-2; pos>=0; pos--) {
			int value=arr[pos];
			int i=pos+1;
			while(i<arr.length && value>arr[i]) {
				arr[i-1]=arr[i];
				i++;
			}
			arr[i-1]=value;
			showArray(arr);
		}		
	}
        
	public void selectSort(int[] arr) {
		showArray(arr);
		for(int border=arr.length; border>1; border--) {
			int maxPos=0;
			for (int pos=0; pos<border; pos++)
				if(arr[maxPos]<arr[pos])
					maxPos=pos;
			swap(arr, border-1, maxPos);
			showArray(arr);
		}
	}
	
	
	
	public static void merge(int[] arr, int[] temp, int left, int mid, int right) {
		
		if (right > arr.length)
			right = arr.length;
			if(mid > arr.length)
				if (right-left<2)
					mid = right;
				else
					mid = left + (right - left)/2;
                
		int i = left;
		int j = mid;
		for (int k = left; k < right; k++){
			if (i == mid)
				temp[k] = arr[j++];
			else if (j == right)
				temp[k] = arr[i++];
			else if (arr[j] < arr[i]) //error here with the j
				temp[k] = arr[j++];
			else // arr i < arr j
				temp[k] = arr[i++];
		}
		
		if (right - left >= 0)
			System.arraycopy(temp, left, arr, left, right - left);
	}
	

	public void iterativeMergeSort(int[] arr) {
		if(arr == null)
            return;
		//else if (arr.length == 1) return arr;
		else if (arr.length == 1) // already ordered
			showArray(arr);
		else if (arr.length > 1) {
			int[] temp = new int[arr.length];
	        showArray(arr);
	        
	        for (int i = 1; i < arr.length; i *= 2) {
	            for (int j = 0; j < arr.length; j += 2 * i) {
	                merge(arr, temp, j, i + j, j + 2 * i);
	            }
	
	            showArray(arr);
	        }
		}
	}

	private int findMax(int[] arr) {
		int max = arr[0]; 
		for(int i = 1; i < arr.length; i++){
			if(arr[i] > max)
				max = arr[i];
		}
		return max;
	}
	
	public static void countingSort(int[] arr, int exp) {
		int[] output = new int[arr.length];
		int[] count = new int[10]; 
		Arrays.fill(count, 0); 

		for (int j = 0; j < arr.length; j++) {
			count[(arr[j] / exp) % 10]++; //coloca en las posiciones del array segun el resto
		}
		for (int i = 1; i < 10; i++) { //sum
			count[i] += count[i - 1];
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			output[count[(arr[i] / exp) % 10] - 1] = arr[i]; 
			count[(arr[i]) / exp % 10]--; 
		}
		
		System.arraycopy(output, 0, arr, 0, arr.length); 
		showArray(output);
	}

	public void radixSort(int[] arr) {
		if(arr == null)
            return;
		
		showArray(arr);
		findMax(arr);
		for(int exp = 1; exp < 1000; exp *= 10) {
			countingSort(arr, exp);
		}

	}

}