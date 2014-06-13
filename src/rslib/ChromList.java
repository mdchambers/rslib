package rslib;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class ChromList extends ArrayList<Chrom>
{
	String assembly;
	
	//Construct from a bed file
	public ChromList(File bedFile)
	{
		try
		{
			Scanner s = new Scanner(bedFile);
			while(s.hasNextLine())
			{
				String l = s.nextLine();
				String[] splitl = l.split("\t");
				
			}
		}catch(IOException iox)
		{
			System.out.println("Error!!");
		}
		
	}
}
