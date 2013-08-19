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

	private static String expandAroundCenter(String s, int c1, int c2) {
		int l = c1, r = c2;
		int n = s.length();
		while (l >= 0 && r <= n-1 && s.charAt(l) == s.charAt(r)) {
			l--;
			r++;
		}

		return (l + 1 <= r) ? s.substring(l + 1, r) : "";
	}

	// does it in O(n^2)
	public static String longestPalindromeSimple(String s) {
		int n = s.length();
		if (n == 0) return "";
		String longest = s.substring(0, 1);  // a single char itself is a palindrome
		for (int i = 0; i < n-1; i++) {
			String p1 = expandAroundCenter(s, i, i);
			if (p1.length() > longest.length())
				longest = p1;

			String p2 = expandAroundCenter(s, i, i+1);
			if (p2.length() > longest.length())
				longest = p2;
		}
		return longest;
	}

	// Transform S into T.
	// For example, S = "abba", T = "^#a#b#b#a#$".
	// ^ and $ signs are sentinels appended to each end to avoid bounds checking
	private static String preProcess(String argInput) {
		int n = argInput.length();
		if (n == 0) {
			return "";
		}

		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < n; i++) {
			ret.append("#" + argInput.substring(i, i+1));
		}

		ret.append("#");

		return ret.toString();
	}

	// does it in O(n)
	public static String longestPalindrome(String argInput) {
		String input = preProcess(argInput);
		int n = input.length();
		int[] p = new int[n];
		int C = 0, // current center 
				R = 0;

		for (int i = 1; i < n-1; i++) {
			int i_mirror = 2*C-i; // equals to i' = C - (i-C)

			p[i] = (R > i) ? Math.min(R-i, p[i_mirror]) : 0;

			// Attempt to expand palindrome centered at i
			while (i + 1 + p[i] < input.length() && i - 1 - p[i] >= 0 && input.charAt(i + 1 + p[i]) == input.charAt(i - 1 - p[i])) {
				p[i]++;
			}

			// If palindrome centered at i expand past R,
			// adjust center based on expanded palindrome.
			if (i + p[i] > R) {
				C = i;
				R = i + p[i];
			}
		}

		// Find the maximum element in P.
		int maxLen = 0;
		int centerIndex = 0;
		for (int i = 1; i < n-1; i++) {
			if (p[i] > maxLen) {
				maxLen = p[i];
				centerIndex = i;
			}
		}

		return input.substring(centerIndex - maxLen, centerIndex + maxLen + 1).replace("#", "");
	}

	private static void brackets(String output, int open, int close, int pairs, boolean opened) {
		if (open == pairs && close == pairs && output.length() == pairs * 2) {
			System.out.println(output);
		} else {
			if (open < pairs) {
				brackets(output + "(", open + 1, close, pairs, true);
			}
			if (close < open && opened) {
				brackets(output + ")", open, close + 1, pairs, opened);
			}
		}
	}

	public static void brackets(int total) {
		brackets("", 0, 0, total, false);
	}
	// Find shortest substring in a string that contains all of a set of characters
	public static String subStringFromCharSet(String input, char[] charSet) {
		int[] toFindHistogram = new int[256];

		for (int i = 0; i < charSet.length; i++) {
			toFindHistogram[charSet[i]]++;
		}

		int[] foundHistogram = new int[256];
		int currentMinimum = Integer.MAX_VALUE;
		int currentMinimumStartIndex = 0;
		int currentMinimumEndIndex = 0;
		int count = 0;

		for (int begin = 0, end = 0; end < input.length(); end++) {
			// skip characters not in the set
			if (toFindHistogram[input.charAt(end)] == 0) {
				continue;
			}

			foundHistogram[input.charAt(end)]++;

			if (foundHistogram[input.charAt(end)] <= toFindHistogram[input.charAt(end)]) {
				count++; 
			}

			// if window constraint is satisfied
			if (count == charSet.length) {
				// advance begin index as far right as possible,
				// stop when advancing breaks window constraint.
				while (toFindHistogram[input.charAt(begin)] == 0 || foundHistogram[input.charAt(begin)] > toFindHistogram[input.charAt(begin)]) {
					if (foundHistogram[input.charAt(begin)] > toFindHistogram[input.charAt(begin)]) {
						foundHistogram[input.charAt(begin)]--;
					}
					begin++;
				}

				// update minWindow if a minimum length is met
				int substringLength = end - begin + 1;
				if (substringLength < currentMinimum) {
					currentMinimumStartIndex = begin;
					currentMinimumEndIndex = end;
					currentMinimum = substringLength;
				}
			}
		}
		
		return input.substring(currentMinimumStartIndex, currentMinimumEndIndex + 1);
	}
	
	/**
	   * Finds the first occurrence of the pattern in the text using Knuth-Morris-Pratt algorithm
	   */
	  public static int matchKMP(String pattern, String text) {
	    int j = 0;
	    if (text.length() == 0) {
	      return -1;
	    }
	    
	    int[] failure = preProcessKMP(pattern);
	    
	    for (int i = 0; i < text.length(); i++) {
	      while (j > 0 && pattern.charAt(j) != text.charAt(i)) {
	        j = failure[j - 1];
	      }
	      if (pattern.charAt(j) == text.charAt(i)) { j++; }
	      if (j == pattern.length()) {
	        return i - pattern.length() + 1;
	      }
	    }
	    
	    return -1;
	  }
	  
	  private static int[] preProcessKMP(String pattern) {
	    int j = 0;
	    int[] failure = new int[pattern.length()];
	    for (int i = 1; i < pattern.length(); i++) {
	      while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) { 
	        j = failure[j - 1];
	      }
	      if (pattern.charAt(j) == pattern.charAt(i)) { 
	        j++; 
	      }
	      failure[i] = j;
	    }
	    
	    return failure;
	  }

	public static void main(String[] args) {
		//		String str = "My hat is round";
		//		char[] chars = new char[str.length()]; 
		//		str.getChars(0, str.length(), chars, 0);
		//		IO.writeLn(reverseWords(chars));
		//		permutation("abra");
		//		String input = "abacdfdcgdcaba";
		//		System.out.println("Longest palindrome of " + input + " is " + longestPalindrome(input));

//		brackets(3);
		
//		System.out.println(subStringFromCharSet("acbbaca", new char[] {'a', 'b', 'a'}));

	  System.out.println(matchKMP("aba", "akrabarot"));
	  System.out.println(matchKMP("abak", "akrabarot"));
	  System.out.println(matchKMP("lol", "akrabarot"));
	  System.out.println(matchKMP("rot", "akrabarot"));
	}
}
