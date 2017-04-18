
/**
 * @author Leonard Meerwood
 *
 */
public class MultiIntLinkedList implements Cloneable, Comparable<MultiIntLinkedList>{
	private MultiIntLinkedNode head;
	private MultiIntLinkedNode tail;

	private boolean isPositive = true;

	/**
	 * Initialize an empty list with no value.
	 * @postcondition
	 *   Empty list with no value
	 */
	public MultiIntLinkedList() {
	}

	/**
	 * Initialize a linked list with an intial value
	 * @param val
	 *   The initial value to set the list to.
	 * @precondition
	 *   The val string has only 0-9 and ',' and '-' in it
	 * @postcondition
	 *   A linked list with the value set as val
	 */
	public MultiIntLinkedList(String val) {
		boolean first = true;

		//Make sure there are no illegal characters in input
		if(!val.matches("^-?[0-9,]+$")){
			throw new IllegalArgumentException("Invalid characters found in string");
		}


		//Check for negativivity
		if (val.charAt(0) == '-'){
			isPositive = false;
			val = val.substring(1);
		}

		val = val.replace(",", "");

		// check how many groups of 3 digits there and then the leftover digits
		int fullGroups = val.length() / 3;
		int leftOver = val.length() % 3;

		// variables to assist parsing string
		String piece;
		int substringBegin, substringEnd, intVal;

		//a loop to go through and grab all groups of three digits,
		//starting with the least significant group
		for (int i = fullGroups - 1; i >= 0; i--) {

			substringBegin = leftOver + i * 3;
			substringEnd = substringBegin + 3;

			piece = val.substring(substringBegin, substringEnd);


			intVal = Integer.parseInt(piece);
			if (first) {
				this.head = this.tail = new MultiIntLinkedNode(intVal);
				first = false;
			} else {
				this.tail.setNext(new MultiIntLinkedNode(intVal));
				this.tail = this.tail.getNext();
			}
		}

		//grab the left over digits in the most significant position
		if (leftOver != 0) {
			piece = val.substring(0, leftOver);
			intVal = Integer.parseInt(piece);
			if (fullGroups > 0) {
				this.tail.setNext(new MultiIntLinkedNode(intVal));
				this.tail = this.tail.getNext();
			} else {
				this.head = this.tail = new MultiIntLinkedNode(intVal);
			}
		}

		trimLeadingZeros();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String output = "";

		// loops through the nodes and prints the value to a string, starting
		// with the least significant digits.
		for (MultiIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			if (node != tail) {
				output = "," + String.format("%03d", node.getVal()) + output;
			} else {
				output = node.getVal() + output;
			}
		}

		if(!this.isPositive()) {
			output = "-" + output;
		}
		return output;
	}


	/**
	 * return the number of nodes used in the list
	 * @return
	 *	number of nodes
	 */
	public int length() {

		int length = 0;
		for (MultiIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			length++;
		}

		return length;
	}

	/**
	 * Return the node at a specific position
	 * @param position
	 *   needed position of node
	 * @return
	 *   node at specific position
	 */
	public MultiIntLinkedNode getNode(int position) {
		MultiIntLinkedNode result = this.head;
		for(int i = 0; i < position; i++) {
			try {
				result = result.getNext();
			} catch (NullPointerException e) {
				throw e;
			}

		return result;
	}

	/**
	 * A function to add two list together
	 * @param firstList
	 *   The first list to add
	 * @param secondList
	 *   The second list to add
	 * @return
	 *   A new MultiIntLinkedList that holds the value of firstList + secondList
	 */
	public static MultiIntLinkedList add(MultiIntLinkedList firstList, MultiIntLinkedList secondList) {
		MultiIntLinkedList result = new MultiIntLinkedList();

		/*
			This block checks to make sure the numbers are in the right set up.
			The list needs to be in: A+B or -(A+B). If invalid set up then the list
			are rearranged and sent to the correct function. While they are
			rearranged they are still mathamatically equivilent.
		 */

		if (firstList.isPositive() && !secondList.isPositive()) {
			secondList = MultiIntLinkedList.clone(secondList);
			secondList.setIsPositive(true);
			return MultiIntLinkedList.subtract(firstList, secondList);

		} else if (!firstList.isPositive() && secondList.isPositive()) {
			firstList = MultiIntLinkedList.clone(firstList);
			firstList.setIsPositive(true);
			return MultiIntLinkedList.subtract(secondList, firstList);

		} else if (!firstList.isPositive() && !secondList.isPositive()) {
			result.setIsPositive(false);

		}

		//Variables to help add the list together.
		int carryOver = 0;
		int firstLength = firstList.length();
		int secondLength = secondList.length();
		int length = (firstLength > secondLength) ? firstLength : secondLength;

		int firstVal, secondVal, resultVal;

		//For each block, add the two matching nodes. Any value over 1000 is stored
		//in the carryover and added to the next nodes.
		for(int i = 0; i < length; i++){
			try {
				firstVal = firstList.getNode(i).getVal();
			} catch (NullPointerException e) {
				firstVal = 0;
			}

			try {
				secondVal = secondList.getNode(i).getVal();
			} catch (NullPointerException e) {
				secondVal = 0;
			}

			resultVal = firstVal + secondVal + carryOver;
			carryOver = MultiIntLinkedList.carryOverValue(resultVal);
			resultVal = resultVal - (carryOver * 1000);

			result.addNode(resultVal);
		}

		if (carryOver != 0) {
			result.addNode(carryOver);
		}

		result.trimLeadingZeros();
		return result;
	}

	/**
	 * A function to subtract list2 from list1.
	 * @param  MultiIntLinkedList firstList
	 *  List being subtracted from.
	 * @param  MultiIntLinkedList secondList
	 *   Value subtracted from firstList
	 * @return
	 *   A MultiIntLinkedList holding the answer.
	 */
	public static MultiIntLinkedList subtract (MultiIntLinkedList firstList, MultiIntLinkedList secondList) {

		MultiIntLinkedList result = new MultiIntLinkedList();

		/*
			This block checks to make sure the numbers are in the right set up.
			The list needs to be in: A-B. If invalid set up then the list
			are rearranged and sent to the correct function. While they are
			rearranged they are still mathamatically equivilent.
		 */

		if(!firstList.isPositive() && !secondList.isPositive()) {
			secondList = MultiIntLinkedList.clone(secondList);
			secondList.setIsPositive(!secondList.isPositive());
			return MultiIntLinkedList.subtract(secondList, firstList);

		} else if(!firstList.isPositive() ^ !secondList.isPositive()) {
			secondList = MultiIntLinkedList.clone(secondList);
			secondList.setIsPositive(!secondList.isPositive());
			return MultiIntLinkedList.add(firstList, secondList);

		} else if(firstList.compareTo(secondList) == -1) {
			MultiIntLinkedList temp = MultiIntLinkedList.clone(firstList);
			firstList = MultiIntLinkedList.clone(secondList);
			firstList.setIsPositive(true);
			secondList = MultiIntLinkedList.clone(temp);
			result.setIsPositive(false);

		} else {
			firstList = MultiIntLinkedList.clone(firstList);
		}

		//Variables to help subtract the list.
		int carryOver = 0;
		int firstLength = firstList.length();
		int secondLength = secondList.length();
		int length = (firstLength > secondLength) ? firstLength : secondLength;

		int firstVal, secondVal, resultVal;

		//For each block, subtract the two matching nodes. If result is less than
		//0, then borrow the next value you can.
		for(int i = 0; i < length; i++){
			try {
				firstVal = firstList.getNode(i).getVal();
			} catch (NullPointerException e) {
				firstVal = 0;
			}

			try {
				secondVal = secondList.getNode(i).getVal();
			} catch (NullPointerException e) {
				secondVal = 0;
			}

			resultVal = firstVal - secondVal;

			if (resultVal < 0) {

				int borrowVal = -1;
				int travelDistance = 0;
				for (int j = i + 1; j < length; j++) {
					borrowVal = firstList.getNode(j).getVal();
					if (borrowVal != 0) {
						firstList.getNode(j).setVal(borrowVal - 1);
						travelDistance = j - i;
						break;
					}
				}
				if (borrowVal >= 0) {
					for (int j = i + 1; j < i  + travelDistance; j++) {
						firstList.getNode(j).setVal(999);
					}
					resultVal += 1000;
				} else {
					resultVal *= -1;
					result.setIsPositive(false);
				}
			}

			carryOver = MultiIntLinkedList.carryOverValue(resultVal);
			resultVal = resultVal - (carryOver * 1000);

			result.addNode(resultVal);


		}

		if (carryOver != 0) {
			result.addNode(carryOver);
		}

		result.trimLeadingZeros();
		return result;

	}

	/**
	 * A program to multiply two list together.
	 * @param  MultiIntLinkedList firstList
	 *   First list to be multiplied
	 * @param  MultiIntLinkedList secondList
	 *   Second list to be multiplied
	 * @return
	 * 	 Result of the multiplication stored in a MultiIntLinkedList
	 */
	public static MultiIntLinkedList multiply (MultiIntLinkedList firstList,
																						 MultiIntLinkedList secondList) {

		MultiIntLinkedList result = new MultiIntLinkedList();
		MultiIntLinkedList stepList;
		boolean answerIsPositive = (firstList.isPositive()==secondList.isPositive());

		if(!firstList.isPositive()) {
			firstList = MultiIntLinkedList.clone(firstList);
			firstList.setIsPositive(true);
		}

		if(!secondList.isPositive()) {
			secondList = MultiIntLinkedList.clone(secondList);
			secondList.setIsPositive(true);
		}

		int carryOver = 0;
		int firstLength = firstList.length();
		int secondLength = secondList.length();

		int firstVal, secondVal, resultVal;

		for(int i = 0; i < firstLength; i++){
			stepList = new MultiIntLinkedList();
			MultiIntLinkedList.padWithZeros(stepList, i);

			for (int j = 0; j < secondLength; j++) {
				firstVal = firstList.getNode(i).getVal();
				secondVal = secondList.getNode(j).getVal();

				resultVal = firstVal * secondVal + carryOver;
				carryOver = MultiIntLinkedList.carryOverValue(resultVal);
				resultVal = resultVal - (carryOver * 1000);


				stepList.addNode(resultVal);

			}

			if (carryOver != 0) {
				stepList.addNode(carryOver);
				carryOver = 0;
			}
			result = MultiIntLinkedList.add(stepList, result);
		}

		result.trimLeadingZeros();
		result.setIsPositive(answerIsPositive);

		return result;
	}

	/**
	 * A program to divde two list.
	 * @param  MultiIntLinkedList firstList
	 *   List that is the dividend
	 * @param  MultiIntLinkedList secondList
	 *   List that is the Divisor
	 * @return
	 * 	 Result of the division stored in a MultiIntLinkedList
	 * @note
	 * 	 This method of division is EXTREMELY inefficient. It works when the
	 * 	 difference between dividend and divisor is small, however anything where
	 * 	 the dividend is more than 1000 times bigger than divisor will take
	 * 	 far too long to be useful. For this reason is has not been implemented
	 * 	 in the assignment demonstration example.
	 */
	public static MultiIntLinkedList divide(MultiIntLinkedList firstList, MultiIntLinkedList secondList)  {
		MultiIntLinkedList result = new MultiIntLinkedList("0");

		result.setIsPositive(firstList.isPositive() == secondList.isPositive());

		firstList = MultiIntLinkedList.clone(firstList);
		firstList.setIsPositive(true);
		secondList = MultiIntLinkedList.clone(secondList);
		secondList.setIsPositive(true);

		firstList.setIsPositive(true);
		secondList.setIsPositive(true);

		MultiIntLinkedList runner = MultiIntLinkedList.clone(secondList);
		MultiIntLinkedList one = new MultiIntLinkedList("1");


		while(firstList.compareTo(runner) >= 0) {
			runner = MultiIntLinkedList.add(runner, secondList);
			result = MultiIntLinkedList.add(result, one);
		}

		return result;
	}

	/**
	 * Calulates how far over the limit the value is (for an idividual node)
	 * and returns the excess value.
	 * @param val
	 *   Value to be calulated
	 * @return
	 *   Excess carried over
	 */
	private static int carryOverValue(int val) {
		val = val - (val % 1000);
		val = val/1000;
		return val;
	}

	/**
	 * Adds a new int holding node to the end of the tail, thus extending the
	 * value of the list.
	 * @param val
	 *   the value to be added to the list
	 * @note
	 *   Will fail if there is not enough memory to create a new node.
	 */
	private void addNode(int val) {
		if (this.head == null) {
			this.head = this.tail = new MultiIntLinkedNode(val);
		} else {
			this.tail.setNext(new MultiIntLinkedNode(val));
			this.tail = this.tail.getNext();
		}
	}

	/**
	 * This is used in the multiplication function to pad the offset needed with
	 * zeros
	 * @param list
	 *   The list to pad
	 * @param padding
	 *   How many nodes to pad with zeros
	 */
	private static void padWithZeros(MultiIntLinkedList list, int padding) {
		for (int i = 0; i < padding; i++) {
			list.addNode(0);
		}
	}

	/**
	 * Toggle the positive value to the 'positive' boolean input
	 * @param positive
	 */
	private void setIsPositive(boolean positive) {
		this.isPositive = positive;
	}

	/**
	 * Returns te posititivity of the list
	 * @return
	 * 	Posititivity of list
	 */
	private boolean isPositive() {
		return this.isPositive;
	}


	/**
	 * A static function to clone a list.
	 * @param clonee
	 *   List to be cloned
	 * @return
	 *   A clone of the list
	 */
	public static MultiIntLinkedList clone(MultiIntLinkedList clonee) {
		return new MultiIntLinkedList(clonee.toString());
	}

	/**
	 *   This function removes leading nodes that are empty. This prevents
	 *   Storing values such as 000,100,000. Also useful for saving memory by
	 *   removing unneeded nodes.
	 */
	private void trimLeadingZeros() {
		while(this.tail.getVal() == 0 && this.length() != 1) {
			this.tail = getNode(this.length() - 2);
			this.tail.setNext(null);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MultiIntLinkedList otherList) {
		int length = (this.length() > otherList.length()) ? this.length() : otherList.length();
		int negativeModifier = 1;

		//Some quick checks to see which list is bigger without complex calulations
		if (this.isPositive() && !otherList.isPositive()){
			return 1;
		} else if (!this.isPositive() && otherList.isPositive()) {
			return -1;
		} else if (!this.isPositive() && !otherList.isPositive()) {
			negativeModifier = -1;
		}

		int thisVal, otherListVal;

		//This compares each node, starting with the most significant node.
		//When a bigger node is found, the corresponding value is returned.
		//If no value found, 0 is return for equal.
		for (int i = length - 1; i >= 0; i--) {
			try {
				thisVal = this.getNode(i).getVal();
			} catch (NullPointerException e) {
				thisVal = 0;
			}

			try {
				otherListVal = otherList.getNode(i).getVal();
			} catch (NullPointerException e) {
				otherListVal = 0;
			}

			if (thisVal > otherListVal) {
				return 1 * negativeModifier;
			} else if (thisVal < otherListVal) {
				return -1 * negativeModifier;
			}
		}
		return 0;

	}
}
