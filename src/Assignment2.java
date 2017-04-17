/**
 * @author Leonard Meerwood
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Assignment2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final String os = System.getProperty("os.name");
		boolean fileLoaded = false;
		
        String number1 = null, number2 = null;
        try {
        	Scanner in = null;
        	in = new Scanner(new File(args[0]));
            number1 = in.next();
            number2 = in.next();
            in.close();
            menu(0, "");
            fileLoaded = true;
        } catch (FileNotFoundException e) {
            menu(-1, "The file could not be loaded: " + e);
        } catch (ArrayIndexOutOfBoundsException e) {
        	menu(-1, "No command line arguments provided, no file loaded.");
        }
        
        int input = 0;
        Scanner sc = new Scanner(System.in);
        
        while(input != 6) {
        	input = sc.nextInt();
        	
        	try {
				if (os.contains("Windows")) {
					new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				} else {
					System.out.print("\033[H\033[2J");  
				    System.out.flush();
				}
			} catch (InterruptedException e) {
				System.out.println("Error with the IO. Exiting application.");
				input = 6;
			} catch (IOException e) {
				System.out.println("Error with the IO. Exiting application.");
				input = 6;
			}
        	
        	if (input == 1) {
        		menu(input, "Please enter a file name:");
        		String filename = sc.next();
        		try {
                	Scanner in = null;
                	in = new Scanner(new File(filename));
                    number1 = in.next();
                    number2 = in.next();
                    in.close();
                    menu(0, "");
                    fileLoaded = true;
                } catch (FileNotFoundException e) {
                    menu(-1, "The file could not be loaded: " + e);
                } 
        	} else if (input == 2 && fileLoaded) {
        		menu(input, displaySILL(number1, number2));
        	} else if (input == 3 && fileLoaded) {
        		menu(input, displaySILLOperations(number1, number2));
        	} else if (input == 4 && fileLoaded) {
        		menu(input, displayMILL(number1, number2));
        	} else if (input == 5 && fileLoaded) {
        		menu(input, displayMILLOperations(number1, number2));
        	} else if (input == 6) {
        		System.out.println("Good bye!");
        	} else {
        		menu(-1, "Can't perform that operation. No values have been loaded.");
        	}
        }
        sc.close();
	}
	
	
	private static void menu (int stage, String extra) {
		String headLine;
		
		if (stage >= 2 && stage <= 5) {
			System.out.println("====================================================");
			System.out.println(extra);
			System.out.println("====================================================");
		}
		
		if (stage == -1) {
			headLine = extra;
		} else {
			headLine = "File loaded!";
		}
		if (stage != 1) {
			System.out.println(headLine);
			System.out.println("1) Input new file name and load new values.");
			System.out.println("2) Display integers stored in SILL form.");
			System.out.println("3) Display four integer arithmitac operations in SILL form.");
			System.out.println("4) Display integers stored in MILL form.");
			System.out.println("5) Display four integer arithmitac operations in MILL form.");
			System.out.println("6) Exit.");
		} else {
			System.out.print("Please insert a file name: ");
		}
		
		
	}
	
	private static String displaySILL(String number1, String number2) {
		SingleIntLinkedList firstList = new SingleIntLinkedList(number1);
		SingleIntLinkedList secondList = new SingleIntLinkedList(number2);
		
		return String.format("Long Number Stored in SILL\n"
				+ "Number 1: %s, Nodes uses: %d\n"
				+ "Number 2: %s, Nodes uses: %d\n"
				, firstList, firstList.length()
				, secondList, secondList.length());
	}

	private static String displaySILLOperations(String number1, String number2) {
		SingleIntLinkedList firstList = new SingleIntLinkedList(number1);
		SingleIntLinkedList secondList = new SingleIntLinkedList(number2);
		
		return String.format("Arithmatic Operations in SILL\n"
				+ "ADDITION\n%s\n+\n%s\n--------------\n%s\n\n"
				+ "SUBTRACTION\n%s\n-\n%s\n--------------\n%s\n\n"
				+ "MULTIPLICATION\n%s\n*\n%s\n--------------\n%s\n\n"
				//+ "%s / %s = %s"
				, firstList, secondList, SingleIntLinkedList.add(firstList, secondList)
				, firstList, secondList, SingleIntLinkedList.subtract(firstList, secondList)
				, firstList, secondList, SingleIntLinkedList.multiply(firstList, secondList));
				//, firstList, secondList, SingleIntLinkedList.divide(firstList, secondList));
	}
	
	private static String displayMILL(String number1, String number2) {
		MultiIntLinkedList firstList = new MultiIntLinkedList(number1);
		MultiIntLinkedList secondList = new MultiIntLinkedList(number2);
		
		return String.format("Long Number Stored in MILL\n"
				+ "Number 1: %s, Nodes uses: %d\n"
				+ "Number 2: %s, Nodes uses: %d\n"
				, firstList, firstList.length()
				, secondList, secondList.length());
	}

	private static String displayMILLOperations(String number1, String number2) {
		MultiIntLinkedList firstList = new MultiIntLinkedList(number1);
		MultiIntLinkedList secondList = new MultiIntLinkedList(number2);
		
		return String.format("Arithmatic Operations in MILL\n"
				+ "ADDITION\n%s\n+\n%s\n--------------\n%s\n\n"
				+ "SUBTRACTION\n%s\n-\n%s\n--------------\n%s\n\n"
				+ "MULTIPLICATION\n%s\n*\n%s\n--------------\n%s\n\n"
				//+ "%s / %s = %s"
				, firstList, secondList, MultiIntLinkedList.add(firstList, secondList)
				, firstList, secondList, MultiIntLinkedList.subtract(firstList, secondList)
				, firstList, secondList, MultiIntLinkedList.multiply(firstList, secondList));
				//, firstList, secondList, MultiIntLinkedList.divide(firstList, secondList));
	}
}
