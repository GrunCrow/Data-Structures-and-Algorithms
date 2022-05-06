public class Link{
	
	public String ref;
	
	public Link(String ref) {
		this.ref=ref;
	}
	
	@Override
	public boolean equals(Object obj) {
		
        Link given = (Link) obj;
		if (given.ref.equals(this.ref)) {
			return true;
		}
		return false;
	}
	
    public String toString() {
    	return ref;
	}
}