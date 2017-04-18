
public class IntLinkedNode {

	private int val;
	private IntLinkedNode next = null;

	public SingleIntLinkedNode(int val) {
		this.setVal(val);
	}

	public IntLinkedNode() {
		throw new IllegalArgumentException("An IntLinkedNode cannot be called with"
																			+" an empty constructor");

	}

	public int getVal() {
		return this.val;
	}

	public void setVal(int val) {
	}

	public IntLinkedNode getNext() {
		return next;
	}

	public void setNext(IntLinkedNode next) {
		this.next = next;
	}
}
