package rslib;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class RNAList extends ArrayList<RNAread> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8400434530168350775L;

	public RNAList()
	{
		super();
	}
	

	public RNAList(File rnafile){
		super();
		Scanner s = null;
		try{
			s = new Scanner(rnafile);
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

	public RNAList(String s){
		this(new File("./" + s));
	}
	
	public void addAnnotations(GeneList gl, boolean exonsOnly){
		//log("start overlap");
		//log("gl.size: " + gl.size);
		
		for(int i = 0; i < gl.size(); i++){
			//log("gl.size: " + gl.size + " i: " + i);
			Gene g = gl.get(i);
//			if(i % 10 == 0){
//				annotate.writeLog("Gene: " + i + "\n");
//			}
			for( RNAread r : this ){
				//log("r.chr: " + r.chr + " r.start: " + r.start + " r.end: " + r.end);
				if(g.overlapsRNA(r.chr, r.start, r.end, exonsOnly)){
					//log("adding gene");
					r.addGene(g);
				}
			}
			g = null;
		}
			
	}
	
	public void write(boolean annotations){
		System.out.println("Printing RNA# " + this.size());
		for(RNAread r : this){
			String s = r.toString(annotations);
			System.out.println(s);
		}
	}
//	private static void log(String s){
//		System.out.println("LOG(RNAL): " + s);
//	}
		
}
		