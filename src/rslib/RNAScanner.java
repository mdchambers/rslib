package rslib;

//import java.util.Scanner;
import java.io.*;

public class RNAScanner{
//	Scanner scan;
	File f; 
	
	BufferedReader br;
	
	public RNAScanner(File bedfile) throws IOException
	{
		f = bedfile;
	}
	
	public RNAScanner(String s)
	{
		f = new File("./" + s);
	}
	
	public void open()
	{
		try{
			br = new BufferedReader(new FileReader(f));	
		}catch (FileNotFoundException fex){
			System.err.println("File not found: " + f.getName() + "\n" + fex.getMessage());
		}
	}
	
	public void close(){
		try{
			br.close();	
		}catch (IOException ioex){
			System.err.println("Can't close file!");
		}
	}
	
	  public void annotateAndWrite(GeneList gl) throws IOException
	{
		String line; 
		try{
			br = new BufferedReader(new FileReader(f));	
		}catch(FileNotFoundException fex){
			System.out.println("RNAScanner file not found: " + f.getName() + "\n" + fex.getMessage());
		}
//		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
//		if(!br.ready())
//		{
//			this.open();
//		}
		while((line = br.readLine()) != null)
		{
			RNAread thisRNA = new RNAread(line);
			for(int i = 0; i < gl.size(); i++)
			{
				Gene g = gl.get(i);
//				if(i % 10 == 0)
//				{
//					annotate.writeLog("Gene: " + i + "\n");
//				}
//				System.out.println("in gene yo");
				if(g.overlaps(thisRNA)){
					System.out.println("adding gene: " + g.getName());
					System.out.println((thisRNA.intersection(g)).toString());
				}
			}
//			thisRNA.write(bw);
			
		}
		this.close();
	}
	
	
	
}
