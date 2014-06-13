package rslib;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class AnnoRNAList extends RNAList 
{
	ArrayList<String> annotations;
	
	AnnoRNAList(File bedFile)
	{
		super();
		Scanner s = null;
		try{
			s = new Scanner(bedFile);
			while(s.hasNextLine()){
				String line = s.nextLine();
				//log("this line is: " + line);
				add(new RNAread(line));
			}
		}
		catch(FileNotFoundException fex){
			System.out.println("File not found: " + fex.getStackTrace() );
		}
		finally{
			s.close();
			s = null;
		}
	}
}
