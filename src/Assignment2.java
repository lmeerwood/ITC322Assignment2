/**
 * @author Leonard Meerwood
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        String number1 = null, number2 = null;
        try {
        	Scanner in = null;
        	in = new Scanner(new File(args[0]));
            number1 = in.next();
            number2 = in.next();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error reading the file: " + e);
        } catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("There where no arguments presented");
        }
       

       //write your code here

        MultiIntLinkedList test = new MultiIntLinkedList("12341");
        MultiIntLinkedList test2 = new MultiIntLinkedList("534523");
        
        System.out.printf("%s + %s = %s\n", test, test2, MultiIntLinkedList.add(test, test2));
        System.out.printf("%s - %s = %s\n", test, test2, MultiIntLinkedList.subtract(test, test2));
        System.out.printf("%s * %s = %s\n", test, test2, MultiIntLinkedList.multiply(test, test2));
        
        
	}

}
