package mware_lib;

public class StringChecker {
	public static boolean checkString(String s) {
		if(s.length() == 0) {
			return false;
		}
		
		for(int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			if(((c < 'A') || (c > 'Z')) 
			&& ((c < 'a') || (c > 'z')) 
			&& ((c < '0') || (c > '9')) 
			&& (c != '.') && (c != '-') && (c != ' ')) {
				return false;
			}
		}
		
		return true;
	}
}
