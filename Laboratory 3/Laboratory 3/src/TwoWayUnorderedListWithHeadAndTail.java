import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayUnorderedListWithHeadAndTail <E> implements IList<E>{
	
	private class Element{
		public Element(E e) {
            this.object=e;
            this.next = null;
			this.prev = null;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		
		E object;
		Element next=null;
		Element prev=null;               
	}
	
	Element head;
	Element tail;
	// int size = 0; 	// can be done with or without field size
	
	
	private class InnerIterator implements Iterator<E> {
		Element it; //moving element -> iterator
		
		public InnerIterator() {
            this.it = head;
		}
                
		@Override
		public boolean hasNext() {
            return this.it.next != null && this.it.next != tail;
		}
		
		@Override
		public E next() { //move to next
            this.it = this.it.next;
			E value = it.object;
			return value;
        }
	}
	
	private class InnerListIterator implements ListIterator<E> { //si prev
		Element elem = head;

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
                        
		}

		@Override
		public boolean hasNext() {
            return this.elem.next != null && this.elem.next != tail;

		}

		@Override
		public boolean hasPrevious() {
            return this.elem.prev != null && this.elem.prev != head;
		}

		@Override
		public E next() { //ir al sig
            this.elem = this.elem.next;
			E value = this.elem.object;
			return value;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() { //ir al ant
            this.elem = this.elem.prev;
			E value = this.elem.object;
			return value;
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
			
		}

		@Override
		public void set(E e) { //poner un valor
           this.elem.object = e;
			
		}
	}
	
	public TwoWayUnorderedListWithHeadAndTail() {
		// make a head and a tail	
        head = new Element(null, tail, null);
		tail = new Element(null, null, head);
		clear(); //htal tailh
	}
	
	@Override
	public boolean add(E e) {
		InnerListIterator it = new InnerListIterator();
		Element newEl = new Element(e);

		while (it.hasNext()) {
			it.next();
		}

		newEl.prev = it.elem;
		newEl.next = tail;
		it.elem.next = newEl;
		tail.prev = newEl;
		return true;
	}

	@Override
	public void add(int index, E element) {
        InnerListIterator it = new InnerListIterator();
		Element newEl = new Element(element);

		while (it.hasNext() && index > 0) {
			it.next();
			index--;
		}

		if (it.elem == null || index > 0) {
			throw new NoSuchElementException();
		} else {
			newEl.prev = it.elem;
			newEl.next = it.elem.next;
			it.elem.next.prev = newEl;
			it.elem.next = newEl;
		}
	}

	@Override
	public void clear() {
        this.head.next = this.tail;
		this.tail.prev = this.head;
	}

	@Override
	public boolean contains(E element) {
        InnerListIterator it = new InnerListIterator();
		while (it.hasNext()) {
			it.next();
			if (it.elem.object.equals(element))
				return true;
		}
		return false;
	}

	@Override
	public E get(int index) {
        InnerListIterator it = new InnerListIterator();

		while (it.hasNext() && index >= 0) {
			it.next();
			index--;
		}

		if (it.elem == null || index >= 0)
			throw new NoSuchElementException();
		else
			return it.elem.object;
	}

	@Override
	public E set(int index, E element) {
        E value = null;
		InnerListIterator it = new InnerListIterator();
		Element newElement = new Element(element);

		while (it.hasNext() && index >= 0) {
			it.next();
			index--;
		}

		if (it.elem.equals(head) || it.elem.equals(tail) || index >= 0) {
			throw new NoSuchElementException();
		} else {
			value = it.elem.object;
			newElement.next = it.elem.next;
			newElement.prev = it.elem.prev;
			it.elem.next.prev = newElement;
			it.elem.prev.next = newElement;
		}

		return value;
	}

	@Override
	public int indexOf(E element) {
        int index = 0;
		InnerListIterator it = new InnerListIterator();

		while (it.hasNext()) {
			it.next();
			if (it.elem.object.equals(element))
				return index;
			index++;
		}
                
		return -1;
	}

	@Override
	public boolean isEmpty() {
        return this.head.next == this.tail && this.tail.prev == this.head;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() { //NO
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
        E value = null;
		InnerListIterator it = new InnerListIterator();

		while (it.hasNext() && index >= 0) {
			it.next();
			index--;

		}
		if (it.elem == null || index >= 0) {
			throw new NoSuchElementException();
		} else {
			value = it.elem.object;
			it.elem.prev.next = it.elem.next;
			it.elem.next.prev = it.elem.prev;
		}
		return value;
	}

	@Override
	public boolean remove(E e) {
        InnerListIterator it = new InnerListIterator();
		while (it.hasNext()) {
			it.next();
			if (it.elem.object.equals(e)) {
				it.elem.prev.next = it.elem.next;
				it.elem.next.prev = it.elem.prev;
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
        int size = 0;
		InnerListIterator it = new InnerListIterator();

		while (it.hasNext()) {
			it.next();
			size++;
		}

		return size;
	}
        
	public String toStringReverse() {
        InnerListIterator it = new InnerListIterator(); 
        String retStr="";
                
		while(it.hasNext()){
			it.next();
        }
                	                                
        retStr += "\n";

		while (it.hasPrevious()) {
			retStr = retStr + it.elem.object.toString() + "\n";
			it.previous();
		}

		if (!this.isEmpty()) //save the last
			retStr += it.elem.object.toString();
		else
			return "";
                
		return retStr;
	}

	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
        if (!this.equals(other)) {
			Element thisTail = this.tail.prev;
			Element otherHead = other.head.next;

			thisTail.next = otherHead;
			otherHead.prev = thisTail;

			this.tail.prev = other.tail.prev;
			other.tail.prev.next = this.tail;

			other.clear();

		}
	}
}