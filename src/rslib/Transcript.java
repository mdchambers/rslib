package rslib;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class Transcript extends GDiscRange
{
	String gene, name;
//	String chr, name;
//	ArrayList<GCon exons;
	
	private static void log(String s)
	{
		UITest.writeLog("TRANS: " + s + "\n");
	}
	
	public Transcript(String ichr, String igname, String itransID, int istart, int iend, int inumexo, int[] iexostart, int[] iexoend)
	{
		super(ichr, iexostart, iexoend);
//		chr = ichr;
		gene = igname;
		name = itransID;
//		start = istart;
//		end = iend;
//		numexo = inumexo;
//		exons = new ExonList(numexo);
//		for(int i = 0; i < inumexo; i++)
//		{
//			exons.add(new Exon(iexostart[i], iexoend[i]));
//		}
	}
	
	public Transcript(String ichr, String igname, String itransID, int istart, int iend, int inumexo, String iexostart, String iexoend)
	{
		this(ichr, igname, itransID, istart, iend, inumexo, 
				splitExonsFromString(iexostart), 
				splitExonsFromString(iexoend));
	}
	
	public Transcript(String ichr, String igname, String itransID, int istart, int iend, int inumexo, ArrayList<Exon> alExo)
	{
		chr = ichr;
		gene = igname;
		name = itransID;
		start = istart;
		end = iend;
//		numexo = inumexo;
//		exons = new ExonList(alExo);
		int[] s = new int[alExo.size()];
		int[] e = new int[alExo.size()];
		for(int i = 0; i < alExo.size(); i++){
			s[i] = alExo.get(i).start;
			e[i] = alExo.get(i).end;
		}
	}
	
	public Transcript(String ichr, String igname, String itransID, int istart, int iend)
	{
		this(ichr, igname, itransID, istart, iend, 1, new int[]{istart}, new int[]{iend});
	}
	
	public Transcript(String bedLine)
	{
		String[] line = bedLine.split("\t");
		chr = line[0];
		start = Integer.parseInt(line[1]);
		end = Integer.parseInt(line[2]);
		gene = (line[3].split("-"))[0];
		name = (line[3].split("-"))[1];
//		int numexo  = Integer.parseInt(line[4]);
		setSegments(splitExonsFromString(line[5]), splitExonsFromString(line[5]));
	}
	
	public static int[] splitExonsFromString(String s){
		String[] exons = s.split(",");
		int[] out = new int[exons.length];
		for(int i = 0; i < exons.length; i++){
			out[i] = Integer.parseInt(exons[i]);
		}
		return out;
	}
	
	public String getExoStarts()
	{
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < segStart.length; i++)
		{
			s.append(segStart[i]);
			s.append(",");
		}
		return s.toString();
		
	}
	
	public String getExoEnds()
	{
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < segEnd.length; i++)
		{
			s.append(segEnd[i]);
			s.append(",");
		}
		return s.toString();
	}
	
	public int getNumExo()
	{
		return segStart.length;
	}
	
	public void addExon(int s, int e)
	{
		addSegment(s, e);
	}
	
	public String toString(){
		String out = chr + "\t" + start + "\t" + end + "\t" + gene + "-" + name + "\t" + this.getNumSegments() + "\t" + getExoStarts() +
			"\t" + getExoEnds();
		return out;
	}
	
//	public ArrayList<GDiscRange> intersection(GRange a)
//	{
//		if(!overlaps(a)){
//			return null;
//		}
//		if(a instanceof GContigRange)
//		{
//			GContigRange x = (GContigRange) a;
//			return intersection(x.start, x.end);
//		}
//		if(a instanceof GDiscRange)
//		{
//			ArrayList<GDiscRange> gdr = new ArrayList<GDiscRange>(1);
//			for(int i = 0; i < ((GDiscRange) a).getNumSegments(); i++)
//			{
//				ArrayList<GDiscRange> x = intersection()
//			}
//		}
//		return null;
//	}
//	
//	private ArrayList<GDiscRange> intersection(int s, int e)
//	{
//		ArrayList<GDiscRange> irOut = new ArrayList<GDiscRange>(1);
//		GContigRange b = new Exon(s, e);
//		if(!overlaps(b))
//		{
//			return null;
//		}
//		for(int i = 0; i < segStart.length; i++)
//		{
//			if(segStart[i] < b.start)
//			{
//				if(segEnd[i] > b.end){
//					irOut.add(b);
//				}else{
//					irOut.add(new Transcript(this.chr, this.name, "A", b.start, segEnd[i]));
//				}
//			}
//		}
//		
//		
//		
//		if(start < b.getStart())
//		{
//			if(end > b.getEnd())
//			{
//				irOut.add(b);
//				return irOut;
//			}
//			irOut.add(new Exon(b.getStart(), end));
//			return irOut;
//		}
//		if(end > b.getEnd())
//		{
//			irOut.add(new Exon(start, b.getEnd()));
//			return irOut;
//		}
//		irOut.add(this);
//		return irOut;
//		return gdr;
//	}
//	
	
//	public boolean overlaps(int s, int e, boolean exonsOnly)
//	{
//		if(e <= start || s > end){
//			return false;
//		}
//		//Check for overlap with exon
//		if(exonsOnly){
//			for(int j = 0; j < exons.size(); j++){
//				if( e <= exons.get(j).start ){
//					continue;
//				}
//				if( s > exons.get(j).end ){
//					continue;
//				}
//				//Overlaps exon
//				return true;	
//			}
//		}
//		else{
//			//Overlaps whole gene; not necessarily overlaps exon (doesn't matter if exonsOnly = false
//			return true;
//		}
//		//Overlaps gene, but does not overlap exon; only if exonsOnly = true
//		return false;
//	}
//	
//	public static Transcript union(Transcript t1, Transcript t2)
//	{
//		ArrayList<Exon> t1Ex = t1.exons;
//		ArrayList<Exon> t2Ex = t2.exons;
//		ArrayList<Exon> outEx = new ArrayList<Exon>();
//			for(int j = 0; j < t1Ex.size(); j++)
//			{
////				boolean keep = false;
//				for(int k = 0; k < t2Ex.size(); k++)
//				{
//					if(t1Ex.get(j).overlaps(t2Ex.get(k)))
//					{
////						log("Cutting exon#: " + j + " from: " + t1.gname + " round: j " + j);
////						log("Cutting: " + t1Ex.get(j).toString());
////						log("Before: " + printExons(outEx));
//						outEx.add(Exon.getIntersection(t1Ex.get(j), t2Ex.get(k)));
////						log("Cut to: " + outEx.get(outEx.size() - 1).toString());
////						log("After: " + printExons(outEx));
////						keep = true;
//						continue;
//					}
//				 }
////				if(!keep)
////				{
////					log("Removing exon#: " + j + " from: " + t1.gname + " round: i " + j);
////					log("Removing: " + t1Ex.get(j).toString());
////					conEx.remove(j);
////					j--;
////				}
//			}
////		log("out exons: " + outEx.size());
//		
//		if(outEx.size() == 0)
//		{
//			log("No constitutive exons: " + t1.gene + "-" + t1.name);
//			return t1;
//		}
//		int outS = outEx.get(0).start;
//		int outE = outEx.get(0).end;
//		for(Exon e : outEx)
//		{
//			if(outS > e.start)
//			{
//				outS = e.start;
//			}
//			if(outE < e.end)
//			{
//				outE = e.end;
//			}
//		}
//		return new Transcript(t1.chr, t1.gene, "A", outS, outE, outEx.size(), outEx);
//	}
	

	
//	private static String printExons(ArrayList<Exon> alEx)
//	{
//		StringBuilder s = new StringBuilder();
//		for( Exon e : alEx)
//		{
//			s.append(e.start);
//			s.append("\t");
//			s.append(e.end);
//			s.append("\n");
//		}
//		return s.toString();
//	}
//	private static String pasteExons(int[] exons){
//		StringBuilder sb = new StringBuilder();
//		for(int i : exons){
//			sb.append(i);
//			sb.append(",");
//		}
//		return sb.toString();
//	}
	

	
}
