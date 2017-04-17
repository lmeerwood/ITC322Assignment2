
public class SingleIntLinkedList implements Cloneable, Comparable<SingleIntLinkedList>{
	private SingleIntLinkedNode head;
	private SingleIntLinkedNode tail;
	
	private boolean isPositive = true;
	
	public SingleIntLinkedList() {
	}
	
	public SingleIntLinkedList(String val) {
		boolean first = true;
		
		
		if (val.charAt(0) == '-'){
			isPositive = false;
			val = val.substring(1);
		}
		
		int stringLength = val.length();
		
		for (int i = stringLength - 1; i >= 0; i--) {
			char charVal = val.charAt(i);
			if(charVal != ',') {
				int intVal = Character.getNumericValue(charVal);
				if (first) {
					this.head = this.tail = new SingleIntLinkedNode(intVal);
					first = false;
				} else {
					this.tail.setNext(new SingleIntLinkedNode(intVal));
					this.tail = this.tail.getNext();
				}
			}
		}
		
		trimLeadingZeros();
	}
	
	public String toString() {
		String output = "";
		int digitCounter = 0;
		for (SingleIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			if (digitCounter % 3 == 0 && digitCounter != 0) {
				output = "," + output;
			}
			output = node.getVal() + output;
			digitCounter++;
		}
		
		if(!this.isPositive) {
			output = "-" + output;
		}
		
		return output;
	}
	
	
	public int length() {
		int length = 0;
		for (SingleIntLinkedNode node = this.head; node != null; node = node.getNext()) {
			length++;
		}
		return length;
	}
	
	public SingleIntLinkedNode getNode(int position) {
		SingleIntLinkedNode result = this.head;
		for(int i = 0; i < position; i++) {
			try {
				result = result.getNext();
			} catch (NullPointerException e) {
				throw e;
			}
		}
		
		return result;
	}
	
	public static SingleIntLinkedList add(SingleIntLinkedList firstList, SingleIntLinkedList secondList) {
		SingleIntLinkedList result = new SingleIntLinkedList();
		
		if (firstList.isPositive() && !secondList.isPositive()) {
			secondList = SingleIntLinkedList.clone(secondList);
			secondList.setIsPositive(true);
			return SingleIntLinkedList.subtract(firstList, secondList);
		} else if (!firstList.isPositive() && secondList.isPositive()) {
			firstList = SingleIntLinkedList.clone(firstList);
			firstList.setIsPositive(true);
			return SingleIntLinkedList.subtract(secondList, firstList);
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
			carryOver = SingleIntLinkedList.carryOverValue(resultVal);
			resultVal = resultVal - (carryOver * 10);
			
			result.addNode(resultVal);
			
			
		}
		
		if (carryOver != 0) {
			result.addNode(carryOver);
		}
		
		result.trimLeadingZeros();
		return result;
	}
	
	public static SingleIntLinkedList subtract (SingleIntLinkedList firstList, SingleIntLinkedList secondList) {
		
		SingleIntLinkedList result = new SingleIntLinkedList();
		
		if(!firstList.isPositive() && !secondList.isPositive()) {
			secondList = SingleIntLinkedList.clone(secondList);
			secondList.setIsPositive(!secondList.isPositive());
			return SingleIntLinkedList.subtract(secondList, firstList);
		} else if(!firstList.isPositive() ^ !secondList.isPositive()) {
			secondList = SingleIntLinkedList.clone(secondList);
			secondList.setIsPositive(!secondList.isPositive());
			return SingleIntLinkedList.add(firstList, secondList);
		} else if(firstList.compareTo(secondList) == -1) {
			SingleIntLinkedList temp = SingleIntLinkedList.clone(firstList);
			firstList = SingleIntLinkedList.clone(secondList);
			firstList.setIsPositive(true);
			secondList = SingleIntLinkedList.clone(temp);
			result.setIsPositive(false);
		} else {
			firstList = SingleIntLinkedList.clone(firstList);
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
			
			carryOver = SingleIntLinkedList.carryOverValue(resultVal);
			resultVal = resultVal - (carryOver * 10);
			
			result.addNode(resultVal);
			
			
		}
		
		if (carryOver != 0) {
			result.addNode(carryOver);
		}
		
		result.trimLeadingZeros();
		return result;
		
	}
	
	public static SingleIntLinkedList multiply (SingleIntLinkedList firstList, SingleIntLinkedList secondList) {
		
		SingleIntLinkedList result = new SingleIntLinkedList();
		SingleIntLinkedList stepList;
		boolean answerIsPositive = (firstList.isPositive()==secondList.isPositive());
		
		if(!firstList.isPositive()) {
			firstList = SingleIntLinkedList.clone(firstList);
			firstList.setIsPositive(true);
		}
		
		if(!secondList.isPositive()) {
			secondList = SingleIntLinkedList.clone(secondList);
			secondList.setIsPositive(true);
		}
		
		int carryOver = 0;
		int firstLength = firstList.length();
		int secondLength = secondList.length();
		
		int firstVal, secondVal, resultVal;
		
		for(int i = 0; i < firstLength; i++){
			stepList = new SingleIntLinkedList();
			SingleIntLinkedList.padWithZeros(stepList, i);
			
			for (int j = 0; j < secondLength; j++) {
				firstVal = firstList.getNode(i).getVal();
				secondVal = secondList.getNode(j).getVal();
				
				resultVal = firstVal * secondVal + carryOver;
				carryOver = SingleIntLinkedList.carryOverValue(resultVal);
				resultVal = resultVal - (carryOver * 10);
				
				
				stepList.addNode(resultVal);
				
			}
			
			if (carryOver != 0) {
				stepList.addNode(carryOver);
				carryOver = 0;
			}
			result = SingleIntLinkedList.add(stepList, result);
		}
		
		result.trimLeadingZeros();
		result.setIsPositive(answerIsPositive);
		int length = result.length();
		return result;
	}
	
	private static int carryOverValue(int val) {
		val = val - (val % 10);
		val = val/10;
		return val;
	}
	
	private void addNode(int val) {
		if (this.head == null) {
			this.head = this.tail = new SingleIntLinkedNode(val);
		} else {
			this.tail.setNext(new SingleIntLinkedNode(val));
			this.tail = this.tail.getNext();
		}
	}
	
	private static void padWithZeros(SingleIntLinkedList list, int padding) {
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


	public static SingleIntLinkedList clone(SingleIntLinkedList clonee) {
		return new SingleIntLinkedList(clonee.toString());
	}
	
	private void trimLeadingZeros() {
		int length = this.length();
		while(this.tail.getVal() == 0 && this.length() != 1) {
			this.tail = getNode(this.length() - 2);
			this.tail.setNext(null);
			length = this.length();
		}
	}

	@Override
	public int compareTo(SingleIntLinkedList otherList) {
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
