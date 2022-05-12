import java.util.LinkedList;
import java.util.*;

public class HashTable{
	//LinkedList arr[]; // use pure array
    LinkedList<IWithName> arr[]; // use pure array
	private final static int defaultInitSize = 8;
	private final static double defaultMaxLoadFactor = 0.7;
	private int size;	
	private final double maxLoadFactor;
    private int threshold; 	//percentage of the hash table that needs to be occupied before resizing. 
    						//A hash table with a threshold of 0.6 would resize when 60% of the space is occupied.
	public HashTable() {
		this(defaultInitSize);
	}
        
    public HashTable(int size) {
        this(size, defaultMaxLoadFactor);
    }


	
    @SuppressWarnings("unchecked")
    public HashTable(int initCapacity, double maxLF){
        if (initCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initCapacity);
        
        if (maxLF <= 0 || Double.isNaN(maxLF)) // check if maxLF is a num
            throw new IllegalArgumentException("Illegal Load: " + maxLF);
            

        if (initCapacity == 0)
            initCapacity = 1;
        
        this.maxLoadFactor = maxLF;
        arr = new LinkedList[initCapacity];
        threshold = (int) (initCapacity * maxLoadFactor); //if initcap = 10 and maxloadfact = 60%, threshold = 6
    }

    public boolean add(Object elem){
        if (!(elem instanceof IWithName))
            return false;
        
        IWithName value = (IWithName) elem;

        if (size >= threshold)
            doubleArray();

        int index = (value.hashCode() & 0x7FFFFFFF) % arr.length;
        
        if (arr[index] == null)
            arr[index] = new LinkedList<>(); //no list in the index for that value -> create list for that index
        
        if (arr[index].contains(value)) //if value already exists
            return false;
        
        arr[index].add(value); //calls to linkedlist add
        size++;
        
        return true;
    }

    @SuppressWarnings("unchecked")
    private void doubleArray(){ //3 if capacity is full (>loadmax) double and reestructure the array)
        int oldCap = arr.length;
        int newCap = oldCap * 2;

        LinkedList<IWithName>[] oldArr = arr;
        arr = new LinkedList[newCap];

        threshold = (int) (newCap * maxLoadFactor); //ex 10*2 * 60% = 20 * 60% = 12

        for (int i = 0; i < oldCap; i++){
            List<IWithName> old = oldArr[i];
            
            if (old == null)
                continue;
            
            for (IWithName e : old){ //for each element in the old list -> reorganize
            	
                int index = (e.hashCode() & 0x7FFFFFFF) % newCap; // hash & 0X7FF... = + integer % newcap -> range of tab length -> force the index to be positive
                
                if (arr[index] == null)
                    arr[index] = new LinkedList<>();
                
                arr[index].add(e);
            }
        }
    }


    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < arr.length; i++){
            result.append(i).append(':');
            LinkedList<IWithName> list = arr[i];
            if (list != null)
            {
                Iterator<IWithName> it = list.iterator();
                while (it.hasNext())
                {
                    IWithName elem = it.next();
                    result.append(' ').append(elem.getName());
                    
                    if (it.hasNext())
                        result.append(',');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public Object get(Object toFind)
    {
        if (toFind == null)
            return null;
        
        int index = (toFind.hashCode() & 0x7FFFFFFF) % arr.length; //get the index of the list where we have to look for the obj tofind
        LinkedList<IWithName> list = arr[index];
        
        if (list == null)
            return null;
        
        for (IWithName e : list){ //check each element of the list if its the obj tofind
            if (toFind.equals(e))
                return e;
        }
        
        return null;
    }

}