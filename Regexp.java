/*******************************************************
 * Name: Thomas Weng								   *
 * 													   *	
       *
 ******************************************************/


/*
 * Creates a data type Regexp that contains a 
 * regular expression with methods for matching
 * and reporting on where a match occurred.
 */
public class Regexp
{
	private String regExpression; // regular expression used for
								  // subsequent matching operations
	
	
	private int startIndex; // stores the index of the first character
							// of the matched substring
	
	private int endIndex;  // stores the index of the last character
	                       // of the matched substring
	
	//private boolean repetitionMode; // keeps track of whether previous character
									// is a repetition like *, +, or ?
	/*
	 * Constructs a data type containing a regular expression. 
	 */
	public Regexp(String re)
	{
		regExpression = re;
		startIndex = -1;
		endIndex = -1;
		//repetitionMode = false;
	}
		
	/*
	 * Search for the regular expression anywhere in String s.
	 * Returns true if there is a match and false if there is 
	 * not a match.
	 */
	public boolean match(String s)
	{
		if (regExpression.isEmpty())
		{
			startIndex = 0;
			endIndex = 0;  // DONE
			return true;
		}
		
		if (regExpression.charAt(0) == '^')
		{
			// if match, start index must be 0
			startIndex = 0;
			endIndex = 0; // DONE
			boolean match = matchhere(s, regExpression.substring(1, regExpression.length()));
			if (!match)
			{
				// if not match, then -1 for both start index and end index
				startIndex = -1;
				endIndex = -1;
			}
			return match;
		}
		
		int i = 0;
		do
		{
			startIndex = i;
			endIndex = startIndex;
			if (matchhere(s.substring(i), regExpression))
				return true;
			i++;
		} while (i < s.length());

		
		startIndex = -1;
		endIndex = -1;
		return false;
	}
	
	/*
	 * Search for regexp2 at beginning of String s. 
	 * Returns true if there is a match and false if there 
	 * is not a match.
	 */
	private boolean matchhere(String s, String regexp2)
	{
		//repetitionMode = false;
		
		// No need to set start index and end index to -1 if false
		// because they reset themselves in the loop in the match method.
		// Just make sure that the start index and end index are updated
		// accurately (in case there is a match).
		
		
		if (regexp2.isEmpty())
		{
			return true;
		}
		if (regexp2.length() >= 2)
		{
			if (regexp2.charAt(1) == '*')
			{
				//repetitionMode = true;
				return matchstar(s, regexp2.substring(2, regexp2.length()), regexp2.charAt(0));
			}
			
			if (regexp2.charAt(1) == '+')
			{
				return matchplus(s, regexp2.substring(2, regexp2.length()), regexp2.charAt(0));
			}
			
			if (regexp2.charAt(1) == '?')
			{
				return matchquestionmark(s, regexp2.substring(2, regexp2.length()), regexp2.charAt(0));
			}
		
		}
		if (regexp2.charAt(0) == '$' && regexp2.length() == 1)
		{
			return s.isEmpty();
		}
		if (!s.isEmpty() && (regexp2.charAt(0) == '.' || regexp2.charAt(0) == s.charAt(0)) )
		{/*
			if (regexp2.length() == 1)
			{
				return true;
			}*/
			endIndex++;
			return matchhere(s.substring(1, s.length()), regexp2.substring(1, regexp2.length()));
		}
		return false;
	}
	
	/* Leftmost longest search after confirming regular expression is in
	 * the format of c*regexp3. Returns true if there is a match and false if there 
	 * is not a match. 
	 */
	private boolean matchstar(String s, String regexp3, char c)
	{	
		//x*x*  xxxb
		if (s.isEmpty())
			return matchhere(s, regexp3);
		
		int t;
		for (t = 0; t < s.length() && (s.charAt(t) == c || c == '.'); t++);
		
		//if (t == s.length())
			//t--;
		endIndex += t;
		
		while (t >= 0)
		{
			//System.out.println(s.substring(t) + ", " + t + ", " + regexp3);
			if (matchhere(s.substring(t), regexp3))
				return true;
			t--;
			endIndex--;
		} 
	
		return false;
	}
	
	/* Leftmost longest search after confirming regular expression is in
	 * the format of c+regexp3. Returns true if there is a match and false if there
	 * is not a match.   
	 */
	private boolean matchplus(String s, String regexp3, char c)
	{	
		//x+x+ xx
		if (s.isEmpty())
			return false;
		int t;
		//if (regexp3.isEmpty())
			//return true;
		for (t = 0; t < s.length() && (s.charAt(t) == c || c == '.'); t++);
		//endIndex += t;
		// make sure that there is at least one match
		if (t == 0)
			return false;
		//if (t == s.length() && t != 1)
			//t--;
		endIndex += t;
		
		while (t >= 1)
		{
			//System.out.println(s.substring(t) + ", " + t + ", " + regexp3);
			if (matchhere(s.substring(t), regexp3))
				return true;
			t--;
			endIndex--;
		} 
	
		return false;
	}
	
	/* Leftmost longest search after confirming regular expression is in
	 * the format of c?regexp3. Returns true if there is a match and false if there  
	 * is not a match.  
	 */
	private boolean matchquestionmark(String s, String regexp3, char c)
	{	
		//x?x?x  xx
		if (s.isEmpty())
			return matchhere(s, regexp3); // correct
		if (s.charAt(0) == c || c == '.')
		{
			boolean test = matchhere(s.substring(1), regexp3);
			if (test)
			{
				endIndex++;
				return true;
			}
		}
		
		return matchhere(s, regexp3);
		
		
	}
	
	/* 
	 * Returns the index of the first character of the matched
	 * substring. If there is no match, it returns -1.
	 */
	public int start()
	{
		return startIndex;
	}
	
	/*
	 * Returns the index of one beyond the last character of
	 * the matched substring. If there is no match, it returns
	 * -1.
	 */
	public int end()
	{
		return endIndex;
	}
}
