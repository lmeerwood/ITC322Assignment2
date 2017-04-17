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


           
        SingleIntLinkedList test = new SingleIntLinkedList("12345678901234567890123456789012345678901234567890");
        SingleIntLinkedList test2 = new SingleIntLinkedList("09876543210987654321098765432109876543210987654321");
        System.out.printf("%s - %s = %s\n", 
        		test.toString(), 
        		test2.toString(), 
        		SingleIntLinkedList.subtract(test, test2));
	}

}
