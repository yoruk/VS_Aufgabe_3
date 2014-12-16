package tests;

import mware_lib.StringChecker;

public class StringCheckerTest {
	public static void main(String[] args) {
		System.out.println("StringCheckerTest is running");
		
		String s = "A";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = "Z";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = "x";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = "#";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = ".";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = "";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = " ";
		System.out.println(s + " = " + StringChecker.checkString(s));
		
		s = "42Schnitzel";
		System.out.println(s + " = " + StringChecker.checkString(s));
	}
}
