
public class SingleIntLinkedList {
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
		} else {
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
		
		return result;
	}
	
	public static SingleIntLinkedList subtract (SingleIntLinkedList firstList, SingleIntLinkedList secondList) {
		
		if(!firstList.isPositive() && !secondList.isPositive()) {
			secondList = SingleIntLinkedList.clone(secondList);
			secondList.setIsPositive(!secondList.isPositive());
			return SingleIntLinkedList.subtract(secondList, firstList);
		} else if(!firstList.isPositive() ^ !secondList.isPositive()) {
			secondList = SingleIntLinkedList.clone(secondList);
			secondList.setIsPositive(!secondList.isPositive());
			return SingleIntLinkedList.add(firstList, secondList);
		} else {
			return null;
		}
	}
	
	public static SingleIntLinkedList multiply (SingleIntLinkedList firstList, SingleIntLinkedList secondList) {
		
		SingleIntLinkedList result = new SingleIntLinkedList();
		SingleIntLinkedList stepList;
		
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
		
		result.setIsPositive(firstList.isPositive()==secondList.isPositive());
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
}
