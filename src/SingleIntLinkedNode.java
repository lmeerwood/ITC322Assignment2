
public class SingleIntLinkedNode {
	
	private int val;
	private SingleIntLinkedNode next = null;
	
	public SingleIntLinkedNode(int val) {
		if (val > 9 || val < -9) {
			throw new IllegalArgumentException("A SingleIntLinkedNode cannot hold more than one decimal number");
		}
		this.val = val;
	}
	
	public SingleIntLinkedNode() {
		throw new IllegalArgumentException("A SingleIntLinkedNode cannot be called with empty constructor");
	}
	
	public int getVal() {
		return this.val;
	}
	
	public void setVal(int val) {
		if (val > 9 || val < -9) {
			throw new IllegalArgumentException("A SingleIntLinkedNode cannot hold more than one decimal number");
		}
		this.val = val;
	}
	
	public SingleIntLinkedNode getNext() {
		return next;
	}
	
	public void setNext(SingleIntLinkedNode next) {
		this.next = next;
	}
}
