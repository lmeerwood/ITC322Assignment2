
public class MultiIntLinkedNode {
	
	private int val;
	private MultiIntLinkedNode next = null;
	
	public MultiIntLinkedNode(int val) {
		this.setVal(val);
	}
	
	public MultiIntLinkedNode() {
		throw new IllegalArgumentException("A MultiIntLinkedNode cannot be called with empty constructor");
	}
	
	public int getVal() {
		return this.val;
	}
	
	public void setVal(int val) {
		if (val > 999 || val < -999) {
			throw new IllegalArgumentException("A MultiIntLinkedNode cannot hold more than three digits");
		}
		this.val = val;
	}
	
	public MultiIntLinkedNode getNext() {
		return next;
	}
	
	public void setNext(MultiIntLinkedNode next) {
		this.next = next;
	}
}
