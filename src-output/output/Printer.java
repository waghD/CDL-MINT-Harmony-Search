package output;

public class Printer {
	public static String div = "------------------------------------------------------------------------------------------";
	
	
	public static void printHeader(String s) {
		Printer.printDivider(2);
		System.out.println(s);
		Printer.printDivider(1);
	}
	
	public static void printDivider(int dividers) {
		for(int i = 0; i < dividers; i++) 
			System.out.println(div);
	}
}
