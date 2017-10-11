
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


// 

public class REtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Regexp a;
		try {
			File file = new File("REtests.txt");
			FileReader filereader = new FileReader(file);
			BufferedReader bufferedreader = new BufferedReader(filereader);
			
			String line;
			while ((line = bufferedreader.readLine()) != null)
			{
				if (!line.isEmpty())
				{
					int firstAtIndex = line.indexOf("@");
				
					if (firstAtIndex != -1)
					{
						String secondAndThirdPortion = line.substring(firstAtIndex + 1);
						int second = secondAndThirdPortion.indexOf("@");
						if (second != -1)
						{
							int secondAtIndex = second + firstAtIndex + 1;
							String regexp = line.substring(0, firstAtIndex);
							String string = line.substring(firstAtIndex + 1, secondAtIndex);
							String results = line.substring(secondAtIndex + 1);
							
							int checkNoMoreAt = results.indexOf("@");
							if (checkNoMoreAt == -1)
							{
								
								int start = results.indexOf("[");
								int end = results.indexOf("]") - 1;
								if (end == -2)
								end = -1;
							
							
							
								int leftbracket = results.indexOf("[");
								int rightbracket = results.indexOf("]");
								String stringNoBrackets;
								if (start != -1 && end != -1)
								{
									stringNoBrackets = results.substring(0, leftbracket)
															+ results.substring(leftbracket + 1, rightbracket)
															+ results.substring(rightbracket + 1);
								}
								else
								{
									stringNoBrackets = results;
								}
								
								
								if (!string.equals(stringNoBrackets) && !stringNoBrackets.isEmpty())
								{
									System.out.println(line);
								}
								else
								{
									a = new Regexp(regexp);
									boolean match = a.match(string);
									if (a.start() != start || a.end() != end)
									{
										System.out.println(line);
							// abcd@a[bc]d
							//System.out.println(a.start()); 
							//System.out.println(a.end());
							// expected results
							//System.out.println(start);
							//System.out.println(end);
									}
									if (a.start() == -1 && start == -1 && a.end() == -1 && end == -1
											&& !string.equals(stringNoBrackets))
										System.out.println(line);
								}
							//System.out.println();
							}
							else
							{
								System.out.println(line);
							}
						}
						else
						{
							System.out.println(line);
						}
					}
					else
					{
						System.out.println(line);
					}
				} 
			}
			
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

}
