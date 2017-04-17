
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
	 * 
	 * 
	 */
	public MultiIntLinkedList(String val) {
		boolean first = true;
		
		if(!val.matches("^-?[0-9,]+$")){
			throw new IllegalArgumentException("Invalid characters found in string");
		}
		
		if (val.charAt(0) == '-'){
			isPositive = false;
			val = val.substring(1);
		}
		
		val = val.replace(",", "");

		int fullGroups = val.length() / 3;
		int leftOver = val.length() % 3;
		
		String piece;
		
		int substringBegin, substringEnd, intVal;
		
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
		for (MultiIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			
			if (node != tail) {
				output = "," + String.format("%03d", node.getVal()) + output;
			} else {
				output = node.getVal() + output;
			}
		}
		
		if(!this.isPositive) {
			output = "-" + output;
		}
		
		return output;
	}
	
	
	/**
	 * @return
	 */
	public int length() {
		
		int length = 0;
		for (MultiIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			length++;
		}
		
		return length;
	}
	
	/**
	 * @param position
	 * @return
	 */
	public MultiIntLinkedNode getNode(int position) {
		MultiIntLinkedNode result = this.head;
		for(int i = 0; i < position; i++) {
			try {
				result = result.getNext();
			} catch (NullPointerException e) {
				throw e;
			}
		}
		
		return result;
	}
	
	/**
	 * @param firstList
	 * @param secondList
	 * @return
	 */
	public static MultiIntLinkedList add(MultiIntLinkedList firstList, MultiIntLinkedList secondList) {
		MultiIntLinkedList result = new MultiIntLinkedList();
		
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
		
		int carryOver = 0;
		int firstLength = firstList.length();
		int secondLength = secondList.length();
		int length = (firstLength > secondLength) ? firstLength : secondLength;
		
		int firstVal, secondVal, resultVal;
		
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
	 * @param firstList
	 * @param secondList
	 * @return
	 */
	public static MultiIntLinkedList subtract (MultiIntLinkedList firstList, MultiIntLinkedList secondList) {
		
		MultiIntLinkedList result = new MultiIntLinkedList();
		
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
		
		
		int carryOver = 0;
		int firstLength = firstList.length();
		int secondLength = secondList.length();
		int length = (firstLength > secondLength) ? firstLength : secondLength;
		
		int firstVal, secondVal, resultVal;
		
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
	 * @param firstList
	 * @param secondList
	 * @return
	 */
	public static MultiIntLinkedList multiply (MultiIntLinkedList firstList, MultiIntLinkedList secondList) {
		
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
	 * @param val
	 * @return
	 */
	private static int carryOverValue(int val) {
		val = val - (val % 1000);
		val = val/1000;
		return val;
	}
	
	/**
	 * @param val
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
	 * @param list
	 * @param padding
	 */
	private static void padWithZeros(MultiIntLinkedList list, int padding) {
		for (int i = 0; i < padding; i++) {
			list.addNode(0);
		}
	}
	
	/**
	 * @param positive
	 */
	private void setIsPositive(boolean positive) {
		this.isPositive = positive;
	}
	
	/**
	 * @return
	 */
	private boolean isPositive() {
		return this.isPositive;
	}


	/**
	 * @param clonee
	 * @return
	 */
	public static MultiIntLinkedList clone(MultiIntLinkedList clonee) {
		return new MultiIntLinkedList(clonee.toString());
	}
	
	/**
	 * 
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
		
		if (this.isPositive() && !otherList.isPositive()){
			return 1;
		} else if (!this.isPositive() && otherList.isPositive()) {
			return -1;
		} else if (!this.isPositive() && !otherList.isPositive()) {
			negativeModifier = -1;
		}
		
		int thisVal, otherListVal;
		
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
