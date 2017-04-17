
public class MultiIntLinkedList implements Cloneable, Comparable<MultiIntLinkedList>{
	private MultiIntLinkedNode head;
	private MultiIntLinkedNode tail;
	
	private boolean isPositive = true;
	
	public MultiIntLinkedList() {
	}
	
	public MultiIntLinkedList(String val) {
		boolean first = true;
		
		
		
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
			this.tail.setNext(new MultiIntLinkedNode(intVal));
			this.tail = this.tail.getNext();
		}
		
		trimLeadingZeros();
	}
	
	public String toString() {
		String output = "";
		for (MultiIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			output = node.getVal() + output;
			
			if (node != tail) {
				output = "," + output;
			}
		}
		
		if(!this.isPositive) {
			output = "-" + output;
		}
		
		return output;
	}
	
	
	public int length() {
		
		int length = 0;
		for (MultiIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			length++;
		}
		
		return length;
	}
	
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
			resultVal = resultVal - (carryOver * 10);
			
			result.addNode(resultVal);
			
			
		}
		
		if (carryOver != 0) {
			result.addNode(carryOver);
		}
		
		result.trimLeadingZeros();
		return result;
	}
	
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
						firstList.getNode(j).setVal(9);
					}
					resultVal += 10;
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
	
	private static int carryOverValue(int val) {
		val = val - (val % 1000);
		val = val/1000;
		return val;
	}
	
	private void addNode(int val) {
		if (this.head == null) {
			this.head = this.tail = new MultiIntLinkedNode(val);
		} else {
			this.tail.setNext(new MultiIntLinkedNode(val));
			this.tail = this.tail.getNext();
		}
	}
	
	private static void padWithZeros(MultiIntLinkedList list, int padding) {
		for (int i = 0; i < padding; i++) {
			list.addNode(0);
		}
	}
	
	private void setIsPositive(boolean positive) {
		this.isPositive = positive;
	}
	
	private boolean isPositive() {
		return this.isPositive;
	}


	public static MultiIntLinkedList clone(MultiIntLinkedList clonee) {
		return new MultiIntLinkedList(clonee.toString());
	}
	
	private void trimLeadingZeros() {
		
		while(this.tail.getVal() == 0 && this.length() != 1) {
			this.tail = getNode(this.length() - 2);
			this.tail.setNext(null);
		}
	}

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
