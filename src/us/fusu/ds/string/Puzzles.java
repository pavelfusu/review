package us.fusu.ds.string;

import us.fusu.io.IO;

public class Puzzles {
	public static char[] reverseWords(char[] sentence) {
		reverseString(sentence, 0, sentence.length);
		int wordStart = 0, i = 0;
		while (i < sentence.length) {
			if (sentence[i] == ' ') {
				reverseString(sentence, wordStart, i);
				wordStart = ++i;
			} else if (i == sentence.length - 1) {
				reverseString(sentence, wordStart, i + 1);
				i++;
			} else {
				i++;
			}
		}
		
		return sentence;
	}
	
	public static char[] reverseString(char[] string, int start, int end) {
		for (int i = start; i < (start + end) / 2; i++) {
			swap(string, i, start + end - i - 1);
		}
		
		return string;
	}
	
	private static void swap(char[] array, int a, int b) {
		char temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	
	public  static void permutation(String str) { 
	    permutation("", str); 
	}
	
	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) {
	    	IO.writeLn(prefix);
	    }
	    else {
	        for (int i = 0; i < n; i++) {
	        	permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	        }
	    }
	}
	
	public static boolean hasSubstring(String string, String find) {
		if (string == null || find == null) {
			return false;
		}

		if (string.isEmpty() && find.isEmpty()) {
			return true;
		}

		for (int i = 0; i < string.length(); i++) {
			boolean notMatch = false;
			for (int j = 0; j < find.length(); j++) {
				if (string.charAt(i + j) != find.charAt(j)) {
					notMatch = true;
					break;
				}
			}
			if (!notMatch) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
//		String str = "My hat is round";
//		char[] chars = new char[str.length()]; 
//		str.getChars(0, str.length(), chars, 0);
//		IO.writeLn(reverseWords(chars));
		permutation("abra");
	}
}