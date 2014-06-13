package rslib;

import java.io.*;

public class annotate {
	
	static FileWriter logfw;
	static boolean exonsOnly;
	
	public static void main(String[] argv)throws IOException{
		if(argv.length != 3){
			System.out.println("Usage: annotate \"RNA-seq file\" \"gene annotations\"");
			return;
		}
		logfw = new FileWriter(new File("log.annotate"));

		//Populate gene data
		GeneList genes = new GeneList(argv[1]);
		
		//Populate RNA-seq data
		RNAList rna = new RNAList(argv[0]);
		
		if(argv[2].equals("true")){
			exonsOnly = true;
		} else if (argv[2].equals("false")) {
			exonsOnly = false;
		} else {
			System.out.println("True or false only please");
			return;
		}
		
		rna.addAnnotations(genes, exonsOnly);
		rna.write(true);
		
		logfw.close();
	}

	public static void writeLog(String s){
		try{
			logfw.write(s);
		}catch(IOException ioex){
			System.out.println("Error writing log!!\n");
		}
	}	
}
