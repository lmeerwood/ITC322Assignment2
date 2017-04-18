/**
 * A node that stores a positive integer.
 * @author Leonard Meerwood
 *
 */
public class IntLinkedNode {

	private int val;
	private IntLinkedNode next = null;

	
	/**
	 * Generates a new IntLinkedNode
	 * @param val
	 * 	The value to give the node.
	 */
	public IntLinkedNode(int val) {
		this.setVal(val);
	}

	/**
	 * IntLinkedNode's must be generated with a value. 
	 * Will throw an error otherwise.
	 */
	public IntLinkedNode() {
		throw new IllegalArgumentException("An IntLinkedNode cannot be called with"
																			+" an empty constructor");
	}

	/**
	 * Returns the value of the node.
	 * @return
	 * 	Value of the node
	 */
	public int getVal() {
		return this.val;
	}

	/**
	 * Sets the value of the node.
	 * @param val
	 * 	The value to set
	 * @note
	 *  Will throw an error if val is less than zero.
	 */
	public void setVal(int val) {
		if (val < 0) {
			throw new IllegalArgumentException("An IntLinkedNode cannot hold a "
										 +"negative value. The sign should be stored in the list.");
		}

		this.val = val;
	}

	/**
	 * Returns the next node in the list.
	 * @return
	 *  Next node
	 */
	public IntLinkedNode getNext() {
		return next;
	}

	/**
	 * Sets the next node in the list.
	 * @param next
	 *  The next node to set.
	 */
	public void setNext(IntLinkedNode next) {
		this.next = next;
	}

}
