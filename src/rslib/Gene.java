package rslib;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.StringBuilder;


public class Gene
{	
	private String chr, name;
	
	private int numTranscripts;
	public ArrayList<Transcript> transc;
	
	public Gene(){
		super();
	}
	
	public Gene(int numerator){
		numerator++;
		int s = numerator * 10 ;
		int e = s + 4;
		int tl = 4;
		numTranscripts++;
		transc = new ArrayList<Transcript>();
		StringBuilder exos = new StringBuilder();
		exos.append(s);
		exos.append(",");
		exos.append(s + (tl / 4));
		exos.append(",");
		StringBuilder exoe = new StringBuilder();
		exoe.append(s + (tl / 2));
		exoe.append(",");
		exoe.append(s + (tl * 3 / 4));
		exoe.append(",");
		this.addTrans("chr0", "testGene", Integer.toString(numerator), s, e, 2, 
				exos.toString(), exoe.toString());
	}
		
	public Gene(String bedfileL)
	{
		numTranscripts++;
		transc = new ArrayList<Transcript>();
		this.addTrans(bedfileL);

	}
			
	public Gene(String chr, String name, int numTranscripts, ArrayList<Transcript> transc)
	{
		super();
		this.chr = chr;
		this.name = name;
		this.numTranscripts = numTranscripts;
		this.transc = transc;
	}
	
	public Gene(String chr, String name, Transcript transc)
	{
		this(chr, name, 1, new ArrayList<Transcript>(Arrays.asList(new Transcript[]{transc})));
//		log("in contructor: " + name);
//		log("transcripts: " + this.transc.size());
	}
	
	public Gene(String chr, String name, int start, int end)
	{
		this.chr = chr;
		this.name = name;
		transc = new ArrayList<Transcript>(1);
		transc.add(new Transcript(chr, name, "A", start, end));
	}
	
	public void addTrans(String line)
	{
		String[] sl = line.split("\t");
		int start = Integer.parseInt(sl[1]);
		int end = Integer.parseInt(sl[2]);
		String trans = (sl[3].split("-"))[1];
		int numexo = Integer.parseInt(sl[4]);
		transc.add(new Transcript(chr, name, trans, start, end, numexo, sl[5], sl[6]));
	}
	
	public void addTrans(String chr, String gname, String tID, int start, int end, int numexo, String exostarts, String exoends)
	{
		transc.add(new Transcript(chr, gname, tID, start, end, numexo, exostarts, exoends));
	}
	
	public void addTrans(Transcript iT)
	{
		transc.add(iT);
	}
	
	public String getName(){
		return name;
	}
	
	public boolean overlaps(GContigRange g)
	{
		for(Transcript t : transc)
		{
			if(t.overlaps(g)){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<GContigRange> intersection(GContigRange g)
	{
		ArrayList<GContigRange> out = new ArrayList<GContigRange>(1);
		ArrayList<GContigRange> temp = new ArrayList<GContigRange>(1);
		for(Transcript t : transc)
		{
			temp = g.intersection(t);
			if(temp != null)
			{
				for(GContigRange x : temp){
					out.add(x);
				}
			}
		}
		return out;
	}
	
	
	public String toString()
{
		StringBuilder s = new StringBuilder();
		s.append(chr);
		s.append("\t");
		String[] outTrans = new String[transc.size()];
		for(int i = 0; i < outTrans.length; i++)
		{
			Transcript cT = transc.get(i);
			outTrans[i] = chr + "\t" + Integer.toString(cT.getStart()) + "\t" + Integer.toString(cT.getEnd()) + "\t" + name + "-" + cT.name + "\t" + Integer.toString(cT.getNumExo()) + "\t" + cT.getExoStarts() + "\t" + cT.getExoEnds() + "\n"; 
		}
		StringBuilder out = new StringBuilder();
		for (String str : outTrans)
		{
			out.append(str);
		}
		return out.toString();
			
	}

//	public ArrayList<GRange> union(GRange ir)
//	{
//		ArrayList<Range> irOut = new ArrayList<Range>(0);
//		
//		return irOut;
//	}
	
//	public ArrayList
	
	
	
	
	
//	public boolean overlapsRNA(String rnaChr, int rnaStart, int rnaEnd, boolean exonsOnly)
//	{
//		//log("rnaChr: " + rnaChr + " chr: " + chr);
//		if(!rnaChr.equals(chr)){
//			//log("Chr: " + chr + " " + rnaChr);
//			return false;
//		}
//		//Check for overlap with wholegene
//		for(Transcript t : transc)
//		{
//			if(t.overlaps(rnaStart, rnaEnd, exonsOnly))
//			{
//				return true;
//			}
//		}
//		return false;
//		
//	}
//	
//	public ExonList getIntersection(Exon inE)
//	{
//		ExonList elist = new ExonList();
//		ExonList inEL = new ExonList();
//		inEL.add(inE);
//		ExonList el;
//		for(Transcript t : this.transc)
//		{
//			if((el = ExonList.getIntersection(t.exons, inEL)) != null)
//			{
//				elist.merge(el);
//			}
//			for(Exon thisE : t.exons)
//			{
//				if(thisE.overlaps(inE))
//				{
//					elist.add(Exon.getIntersection(inE, thisE));
//				}
//			}
//		}
//		return elist;
//	}
//
//	public void getRelativeComplement(Gene gIn)
//	{	
//		for(int i = 0; i < this.transc.size(); i++)
//		{
//			for(Transcript u : gIn.transc)
//			{
//				ExonList.subtractIntersection(this.transc.get(i).exons, u.exons);
//				if(this.transc.get(i).getNumExo() == 0)
//				{
//					this.transc.remove(i);
//				}
//			}
//		}
//	}
//
//	public Gene getConstitutive()
//	{
//		Transcript outEx = transc.get(0);
//		for(int i = 1; i < transc.size(); i++)
//		{
//			outEx = Transcript.union(outEx, transc.get(i));
//		}
//		return new Gene(chr, outEx.gname, outEx);
//	}
		

	
}	