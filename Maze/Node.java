class Node<Type> {
	private Type data;
	private Node<Type> link;
	
	public Node() {
		data = null;
		link = null;
	}
	//accessor for nodes data
	public Type getData() {
		return data;
	}
	//Mutator for nodes data
	public void setData(Type data) {
		this.data = data;
	}
	//Accessor for nodes link
	public Node<Type> getLink() {
		return link;
	}
	//Mutator for nodes link
	public void setLink(Node<Type> Link) {
		this.link = Link;
	}
}