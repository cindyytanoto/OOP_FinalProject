package utility;

import java.util.Scanner;

public class Helper {
	
	Scanner scan = new Scanner(System.in);
	
	private static Helper helper = null;
	
	public static Helper getInstance() {
		if(helper == null) {
			helper = new Helper();
		}
		return helper;
	}
	
	public void clear() {
		for (int i = 0; i < 50; i++) {
			System.out.println("");
		}
	}
	
	public String getString(String command) {
		String input;
		System.out.print(command);
		input = scan.nextLine();
		
		return input;
	}
	
	public int getInteger(String command) {
		int input;
		System.out.print(command);
		input = scan.nextInt();
//		scan.nextLine();
		return input;
	}
	
	public void enter() {
		System.out.println("Press ENTER To Continue...");
		scan.nextLine();
	}

}
