package rslib;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
//import java.lang.StringBuilder;
//import java.lang.Integer;


public class GeneList extends ArrayList<Gene>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6401215790120080616L;
	

	
	private static void log(String s)
	{
		UITest.writeLog("LOG(GL): " + s + "\n");
		//System.out.println("LOG(GL): " + s);
	}
	/*Generates a GeneList object from one of several common filetypes (as defined by Flybase, etc.)
	 * ftype:
	 * 	"bed" (tsv)
	 * 		chr start end transcript(gene-transcript ID) #exons	exon_starts(csv) exon_ends(csv)
	 * 	"exons" (tsv)
	 * 	"bed_noexons" (tsv)
	 * 		chr start end gene transcript 
	 * 
	 */
	public GeneList (File gFile, String ftype)
	{
		super(100);
		Scanner s = null;
		try{
			s = new Scanner(gFile);
			String cgene = "";
			int index = -1;
			while(s.hasNextLine()){
				String tline = s.nextLine();
				if (tline != null){
					//Split line and parse chr, start, end (same for all file types)
					String[] l = tline.split("\t");
					String chr = l[0];
					int start = Integer.parseInt(l[1]);
					int end = Integer.parseInt(l[2]);
					if(ftype.equals("exon_bed"))
					{
						String gene = l[4];
						if(gene.equals(cgene))
						{
							(this.get(index)).transc.get(0).addExon(start, end);
						}
						else
						{
							this.add(new Gene(chr, gene, start, end));
							index++;
							cgene = gene;
						}
					}else
					{
						
						String[] trans = (l[3]).split("-");
						int numexo;
						String exostart, exoend;
						
						//For bed files
						if(ftype.equals("all"))
						{	
							numexo = Integer.parseInt(l[4]);
							exostart = l[5].trim();
							exoend = l[6].trim();
							
						}
						//For files lacking exons
						else if(ftype.equals("all_noexons"))
						{
							numexo = 1;
							exostart = Integer.toString(start) + ",";
							exoend = Integer.toString(end) + ",";
						}
						else
						{
							numexo = 1;
							exostart = Integer.toString(start) + ",";
							exoend = Integer.toString(end) + ",";
						}
						if (trans[0].equals(cgene))
						{
							(this.get(index)).addTrans(new Transcript(chr, trans[0], trans[1], start, end, numexo, exostart, exoend));
						}else
						{
							this.add(new Gene(chr, trans[0], new Transcript(chr, trans[0], trans[1], start, end, numexo, exostart, exoend)));
							index++;
							cgene = trans[0];
						}
					}	
				}
			}
		}
		catch( FileNotFoundException fex){
			System.out.println("File not found?\n" + fex.getMessage());
		}
		finally{
			s.close();
		}
	}
	
	public GeneList (File gFile)
	{
		this(gFile, "bed");
	}
	
	public GeneList(String file, String ftype)
	{
		this(new File("./" + file), ftype);
	}
	
	public GeneList(String file){
		this(new File("./" + file));
	}
	
	public GeneList(int num){
		super(num);
		for(int i = 0; i < num; i++){
			this.add( new Gene(i) );
		}	

	}
	
	public GeneList()
	{

	}
	
	public boolean hasGene(String g)
	{
		for (Gene x : this)
		{
			if(x.getName().equals(g))
			{
				return true;
			}
		}
		return false;
	}
	
	public Gene byName(String g)
	{
		for (Gene x : this)
		{
			if(x.getName().equals(g))
			{
				return x;
			}
		}
		return this.get(0);
	}
	
//	public GeneList makeUIGeneList()
//	{
//		GeneList UIGenes = new GeneList();
////		log("in genes " + this.size());
//		for(Gene g : this)
//		{
////			log("In mUIGL for " + g.getName());
////			UIGenes.add(g.getConstitutive());
////			log("Now this many genes: " + UIGenes.size());
//		}
////		log("out genes " + UIGenes.size());
//		UIGenes.removeOverlaps();
//		return UIGenes;
//	}
	
//	private void removeOverlaps()
//	{
//		for(int i = 0; i < this.size(); i++)
//		{
//			for(int j = 0; j < this.size(); j++)
//			{
//				if( i == j)
//					continue;
//				if(!this.get(i).getChr().equals(this.get(j).getChr()))
//					continue;
////				log("removeOverlaps Doing i j: " + i + " " + j);
//				this.get(i).getRelativeComplement(this.get(j));
//			}
//
//		}
		//Check if still has a transcript
//		System.out.println("in remove overlap");
//		log("starting removal");
//		for(int i = 0; i < this.size(); i++)
//		{
//			if(this.get(i).transc.size() == 0)
//			{
//				log("removing" + this.get(i).getName());
//				this.remove(i);
//			}
//		}
//	}
	
	//Various output methods
	
	public void write()
	{
		for(Gene g : this)
		{
			System.out.print(g.toString());
		}
	}
	
	
}	
