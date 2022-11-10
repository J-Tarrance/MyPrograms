/* Name: Jayden Tarrance
   Date: 4/8/21
   Program: #4 LinkedList*/
   
public class LinkedList<Type> {
	/*************
	* ATTRIBUTES *
	*************/
	public static final int MAX_SIZE = 10000;
	private Node<Type> head;
	private Node<Type> current;
	private Node<Type> tail;
	private Node<Type> temp;
	private Node<Type> temp1;
	private int numElements;

	/***************
	* CONSTRUCTORS *
	***************/

	// Newly constructed lists have no nodes, so head and tail point to null
	public LinkedList() {
		head = null;
		current = null;
		tail = null;
		numElements = 0;
	}
	// The copy Constructor 
	public LinkedList(LinkedList<Type> l) {
		head = null;
		current = null;
		tail = null;
		numElements = 0;
		
		for (int i = 0; i < l.size(); i++) {
			Type Temp = l.get(i);
			if (!isFull()) {
				// If the list is empty, the new node becomes the head
				if (isEmpty()) {
					head = new Node<Type>();
					head.setData(Temp);
					current = head;
					tail = head;
				}

				// If the list is not empty, the new node is added to the end of the list
				else {
					tail.setLink(new Node<Type>());
					tail = tail.getLink();
					tail.setData(Temp);
				}

				// Increment the number of elements
				numElements++;
			}
			l.next();
		}
	}

	// Returns a String representation of the list ("NULL" if empty)
	public String toString() {
		// Return "NULL" if the list is empty
		if (head == null) {
			return "NULL";
		}
		else {
			// Initialize the string representation of the linked list
			String s = "";

			// Start traversing the list at the head
			Node<Type> temp = head;

			// Iterate through the list and concatenate each element to the string
			// Seperate each element by a space
			while (temp != null) {
				s += temp.getData() + " ";
				temp = temp.getLink();
			}

			return s;
		}
	}

	// Returns the number of elements in the list (not max capacity)
	public int size() {
		return numElements;
	}

	public boolean isEmpty() {
		return (numElements == 0);
	}

	public boolean isFull() {
		return (numElements == MAX_SIZE);
	}

	// Set the current Node reference to the head node
	public void first() {
		current = head;
	}

	// Set current to the next node in the list
	public void next() {
		current = (current != null) ? current.getLink() : current;
	}

	// Return the element at the current node
	public Type getElement(int index) {
		current = head;
		if (!isFull()) {
			for (int i = 0; i < index; i++) {
				current = (current != null) ? current.getLink() : current;
			}	
		}
		return (current != null) ? current.getData() : null;
	}
	// replaces element with specified element at specified index
	public void set(int index, Type element) {
		current = head;
		if (!isFull()) {
			for (int i = 0; i < index; i++) {
				current = (current != null) ? current.getLink() : current;
			}
			
			current.setData(element);
			current = head;
		}
	}
	
	// adds element add specified index
	public void add(int index, Type element) {
		current = head;
		temp1 = new Node<Type>();
		if (!isFull()) {
			for (int i = 0; i < index; i++) {
				current = (current != null) ? current.getLink() : current;
				temp = current;
			}
			current = head;
			
			for (int i = 1; i < index; i++) {
				current = (current != null) ? current.getLink() : current;
			}
			
			current.setLink(temp1);
			current = temp1.getLink();
			temp1.setData(element);
			temp1.setLink(temp);
			current = head;
			
		}
		numElements++;
	}

	// Adds the specified element to the end of the list
	// Not possible for a full list
	public void add(Type element) {
		// Don't add anything if the list is full
		if (!isFull()) {
			// If the list is empty, the new node becomes the head
			if (isEmpty()) {
				head = new Node<Type>();
				head.setData(element);
				current = head;
				tail = head;
			}

			// If the list is not empty, the new node is added to the end of the list
			else {
				tail.setLink(new Node<Type>());
				tail = tail.getLink();
				tail.setData(element);
			}

			// Increment the number of elements
			numElements++;
		}
	}
	
	//removes element at specified index
	public void remove(int index) {
		current = head;
		if (index == 0){
			current = (current != null) ? current.getLink() : current;
			head.setLink(null);
			head = current;
			numElements--;
			
		} else if (!isEmpty()) {
			current = head;
			temp = current;
			for (int i = 1; i < index; i++) {
				current = (current != null) ? current.getLink() : current;
				temp = current;
			}
			current = head;
			for (int i = -1; i < index; i++) {
				current = (current != null) ? current.getLink() : current;
				temp1 = current;
			}
			
			temp.setLink(temp1);
			current = head;
			numElements--;
			
		}
	}

	// Returns the value in the node at the given index
	public Type get(int index) {
		// Don't traverse the list if the index is out of bounds
		if (!(index < 0 || index >= numElements)) {
			Node<Type> temp = head;
			int i = 0;

			// Traverse the list starting at the head until index is reached
			while (i < index) {
				temp = temp.getLink();
				i++;
			}
			return temp.getData();
		}
		else {
			return null;
		}
	}
	
	//returns index of first occurrence of specified element
	public int indexOf(Type element){
		Node<Type> current = head;
		int i = 0;
		while (i < size()) {
			if (current.getData() == element){
				return i;
			}
			current = (current != null) ? current.getLink() : current;
			i++;
		}
		return -1;
	}

	// Returns the value in the head node
	public Type getFirst() {
		return head.getData();
	}

	// Returns the value in the tail node
	public Type getLast() {
		return tail.getData();
	}
}