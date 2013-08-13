package us.fusu.misc;

public class PhoneNumbersToText {
	
	private static char[][] PHONE_CHARS = new char[][] {
		{'0'}, 
		{'1'}, 
		{'A', 'B', 'C'}, 
		{'D', 'E', 'F'}, 
		{'G', 'H', 'I'}, 
		{'J', 'K', 'L'}, 
		{'M', 'N', 'O'},
		{'P', 'R', 'S'},
		{'T', 'U', 'V'},
		{'W', 'X', 'Y'}};

	private static char getCharKey(int telephoneKey, int place) {
		return PHONE_CHARS[telephoneKey][place - 1];
	}
	
	private static int getPossibleValuesCount(int telephoneKey) {
		return PHONE_CHARS[telephoneKey].length;
	}
	
	private static void printAllPossibleNumbers(int[] phone, int currentDigit, char[] result) {
		if (currentDigit == phone.length) {
			System.out.println(new String(result));
			return;
		}
		
		for (int i = 1; i <= getPossibleValuesCount(phone[currentDigit]); i++) {
			result[currentDigit] = getCharKey(phone[currentDigit], i);
			printAllPossibleNumbers(phone, currentDigit + 1, result);
		}
	}
	
	public static void printAllPossibleNumbers(String telephoneNumber) {
		String phone = telephoneNumber.trim().replace("-", "");
		int[] phoneNumber = new int[phone.length()];
		
		for (int i = 0; i< phone.length(); i++) {
			phoneNumber[i] = Integer.parseInt(phone.substring(i, i+1));
		}
		
		printAllPossibleNumbers(phoneNumber, 0, new char[phoneNumber.length]);
	}
	
	public static void main(String[] args) {
		printAllPossibleNumbers("497-1927");
	}
}
