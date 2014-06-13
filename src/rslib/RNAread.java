package rslib;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.*;

class RNAread extends GContigRange
{
	protected int score;
	protected ArrayList<Gene> genes;
	
	RNAread(String s)
	{
		super();
		String[] line = s.split("\t");
		setChr(line[0]);
		setBounds(Integer.parseInt(line[1]),Integer.parseInt(line[2]));
		score = Integer.parseInt(line[3]);
		line = null;
	}
	
	RNAread(String chr, int start, int end, int score)
	{
		super(start, end); 
		this.score = score;
	}
		
	public int getScore()
	{
		return score;
	}
	
	public ArrayList<GContigRange> union(GContigRange a)
	{
		if(a instanceof RNAread)
		{
			return readUnion((RNAread)a);
		}
		return readUnion(new RNAread(this.getChr(), a.getStart(), a.getEnd(), 0));
	}
	
	private ArrayList<GContigRange> readUnion(RNAread a)
	{
		ArrayList<GContigRange> irOut = new ArrayList<GContigRange>(1);
		if (!overlaps(a))
		{
			irOut.add(this);
			irOut.add(new RNAread(a.chr, a.start, a.end, a.getScore()));
			return irOut;
		}
		if(start < a.getStart())
		{
			if(end > a.getEnd())
			{
				irOut.add(new RNAread(this.chr, this.start, a.getStart(), this.score));
				irOut.add(new RNAread(this.chr, a.getStart(), a.getEnd(), (this.score + a.getScore())));
				irOut.add(new RNAread(this.chr, a.getEnd(), this.end, this.score));
				return irOut;
			}
			irOut.add(new RNAread(this.chr, this.start, a.getStart(), this.score));
			irOut.add(new RNAread(this.chr, a.getStart(), this.end, (this.score + a.getScore())));
			irOut.add(new RNAread(this.chr, this.end, a.getEnd(), a.getScore()));
			return irOut;
		}
		if(end > a.getEnd())
		{
			irOut.add(new RNAread(this.chr, a.getStart(), this.start, this.score));
			irOut.add(new RNAread(this.chr, this.start, a.getEnd(), (this.score + a.getScore())));
			irOut.add(new RNAread(this.chr, a.getEnd(), this.end, this.score));
			return irOut;
		}
		irOut.add(new RNAread(this.chr, a.getStart(), this.start, a.getScore()));
		irOut.add(new RNAread(this.chr, this.start, this.end, (this.score + a.getScore())));
		irOut.add(new RNAread(this.chr, this.end, a.getEnd(), a.getScore()));
		return irOut;
	}
	
	public ArrayList<GContigRange> intersection(GContigRange b)
	{
		ArrayList<GContigRange> irOut = new ArrayList<GContigRange>(1);
		if(!overlaps(b))
		{
			return irOut;
		}
		if(start < b.getStart())
		{
			if(end > b.getEnd())
			{
				irOut.add(new RNAread(this.chr, b.getStart(), b.getEnd(), this.score));
				return irOut;
			}
			irOut.add(new RNAread(this.chr, b.getStart(), this.end, this.score));
			return irOut;
		}
		if(end > b.getEnd())
		{
			irOut.add(new RNAread(this.chr, start, b.getEnd(), this.score));
			return irOut;
		}
		irOut.add(this);
		return irOut;
	}
	
	public ArrayList<GContigRange> intersection(GDiscRange b)
	{
		ArrayList<GContigRange> out = new ArrayList<GContigRange>(1);
		ArrayList<GContigRange> temp = new ArrayList<GContigRange>(1);
		for(int i = 0; i < b.getNumSegments(); i++)
		{
			if ( ( temp = intersection(new Exon(b.segStart[i], b.segEnd[i])) ) != null){
				for(GContigRange x : temp){
					out.add(x);
				}
			}
		}
		return out;
	}

	public ArrayList<GContigRange> intersection(Gene g){
		ArrayList<GContigRange> out = new ArrayList<GContigRange>(1);
		ArrayList<GContigRange> temp = new ArrayList<GContigRange>();
		for(Transcript t : g.transc)
		{
			temp = intersection(t);
			if(temp != null){
				for(GContigRange x : temp){
					out.add(x);
				}
			}
		}
		return out;
	}
	
	public ArrayList<GContigRange> relativeComplement(GContigRange b)
	{
		ArrayList<GContigRange> irOut = new ArrayList<GContigRange>(1);
		if(!overlaps(b))
		{
			irOut.add(this);
			return irOut;
		}
		if(start < b.getStart())
		{
			if(end > b.getEnd())
			{
				irOut.add(new RNAread(this.chr, start, b.getStart(), this.score));
				irOut.add(new RNAread(this.chr, b.getEnd(), end, this.score));
				return irOut;
			}
			irOut.add(new RNAread(this.chr, start, b.getStart(), this.score));
			return irOut;
		}
		if(end > b.getEnd())
		{
			irOut.add(new RNAread(this.chr, b.getEnd(), end, this.score));
			return irOut;
		}
		return irOut;
	}
	
	void addGene(Gene g)
	{
		if(genes == null){
			genes = new ArrayList<Gene>(1);
		}
		genes.add(g);
//		String gname = g.getName();
//		if(annotations == null)
//			annotations = new ArrayList<String>();
//		if(!annotations.contains(gname)){
//			annotations.add(gname);
//		}
	}
	
	void addGene(Gene[] g)
	{
		if(genes == null)
		{
			genes = new ArrayList<Gene>(1);
		}
		for( Gene a : g)
		{
			genes.add(a);
		}
	}

	String toString(boolean annotations)
	{
		String out =  chr + "\t" + start + "\t" + end + "\t" + score + "\t";
		if(annotations)
			out += annotToString();
		return out;
	}
	
	public String toString()
	{
		return this.toString(false);
	}
	
	void write(BufferedWriter bw) throws IOException
	{
		bw.write(toString(true) + "\n");
	}
	
	String annotToString()
	{
		if(genes == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(Gene g : genes){
			sb.append(g.getName());
			sb.append(",");
		}
		return sb.toString();
	}
	
}