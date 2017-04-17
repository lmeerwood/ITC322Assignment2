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


           
        SingleIntLinkedList test = new SingleIntLinkedList("666666");
        SingleIntLinkedList test2 = new SingleIntLinkedList("666666");
        System.out.println("The value of test is: " + test.toString());
        System.out.println("The value of test is " + test.length() + " digits long.");
        System.out.println("The value of test2 is: " + test2.toString());
        System.out.println("The value of test2 is " + test2.length() + " digits long.");
        System.out.printf("%s + %s = %s\n", 
        		test.toString(), 
        		test2.toString(), 
        		SingleIntLinkedList.multiply(test, test2));
	}

}
