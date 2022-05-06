public class Link implements Comparable<Link>{
	public String ref;
	public int weight;
	public Link(String ref) {
		this.ref=ref;
		weight=1;
	}
	public Link(String ref, int weight) {
		this.ref=ref;
		this.weight=weight;
	}
	@Override
	public boolean equals(Object obj) {
        Link given = (Link) obj;
        
		return given.ref.equals(this.ref); //&& given.weight == this.weight
	}
	@Override
	public String toString() {
		return ref+"("+weight+")";
	}
	@Override
	public int compareTo(Link another) {
		if (this.ref.compareToIgnoreCase(another.ref) == 0) {			
			return 0;			
		} else if (this.ref.compareToIgnoreCase(another.ref) < 0)			
			return -1;		
		else
			return 1;		
	}
	
	public String getRef() {
		return ref;
	}
	
	public int getWeight() {
		return weight;
	}
	
	
}