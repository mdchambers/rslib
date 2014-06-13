package rslib;

import java.io.*;

public class UITest 
{

	static FileWriter logfw;  
	static boolean exonsOnly;
	
	public static void main(String[] argv) throws IOException
	{
		if(argv.length != 1){
			System.out.println("Usage: uitest genefile.all");
			return;
		}
		logfw = new FileWriter(new File("log.uitest"));

		//Populate gene data
		GeneList genes = new GeneList(argv[0]);
//		genes.write();
		
		GeneList uigenes = genes.makeUIGeneList();
//		log("out genes" + uigenes.size());
		
		uigenes.write();
		
		logfw.close();
	}

	private static void log(String s)
	{
//		writeLog("LOG(UIT): " + s + "\n");
	}
	
	public static void writeLog(String s){
		if(logfw == null)
		{
//			System.out.println("ERR: making logfw");
			try
			{
				logfw = new FileWriter(new File("log.uitest"));
			}
			catch(IOException iox){System.err.println("Errot opening log: log.uitest");}
		}
		try{
			logfw.write(s);
		}catch(IOException ioex){
			System.out.println("Error writing log!!\n");
		}
	}	
}
