package rslib;

import java.lang.Math;
import java.util.ArrayList;

public class Exon extends GContigRange{
	
	public Exon(int is, int ie){
		super(is, ie);
	}
	
	public String toString()
	{
		String s = start + "\t" + end;
		return s;
	}
		
	public ArrayList<GContigRange> union(GContigRange a)
	{
		ArrayList<GContigRange> irOut = new ArrayList<GContigRange>(1);
		if (!overlaps(a))
		{
			irOut.add(this);
			irOut.add(new Exon(a.getStart(), a.getEnd()));
			return irOut;
		}
		if(start < a.getStart())
		{
			if(end > a.getEnd())
			{
				irOut.add(this);
				return irOut;
			}
			irOut.add(new Exon(start, a.getEnd()));
			return irOut;
		}
		if(end > a.getEnd())
		{
			irOut.add(new Exon(a.getStart(), end));
			return irOut;
		}
		irOut.add(new Exon(a.getStart(), a.getEnd()));
		return irOut;
	}
	
	public ArrayList<GContigRange> intersection(GDiscRange a)
	{
		ArrayList<GContigRange> out = new ArrayList<GContigRange>(1);
		ArrayList<GContigRange> temp = new ArrayList<GContigRange>(1);
		for(int i = 0; i < a.getNumSegments(); i++)
		{
			temp = intersection(new Exon(a.start, a.end));
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
				irOut.add(new Exon(start, b.getStart()));
				irOut.add(new Exon(b.getEnd(), end));
				return irOut;
			}
			irOut.add(new Exon(start, b.getStart()));
			return irOut;
		}
		if(end > b.getEnd())
		{
			irOut.add(new Exon(b.getEnd(), end));
			return irOut;
		}
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
				irOut.add(b);
				return irOut;
			}
			irOut.add(new Exon(b.getStart(), end));
			return irOut;
		}
		if(end > b.getEnd())
		{
			irOut.add(new Exon(start, b.getEnd()));
			return irOut;
		}
		irOut.add(this);
		return irOut;
	}
	
	public static boolean equals(Exon e1, Exon e2)
	{
		if(e1.start == e2.start && e1.end == e2.end)
		{
			return true;
		}
		return false;
	}
	
//	public ArrayList<GContigRange> getBounds(){
//		return new ArrayList<GcontigRange>(new GContigRange())
//	}
	//Useless stuff (replaced by Range stuff)
	public static Exon getIntersection(Exon e1, Exon e2)
	{
		int is, ie;
//		if(e1.overlaps(e2))
//		{
			is = Math.max(e1.start, e2.start);
			ie = Math.min(e1.end, e2.end);
			return new Exon(is, ie);
//		}
//		return null;
//		if(e1.start > e2.start)
//			is = e1.start;
//		else
//			is = e2.start;
//		if(e1.end < e.end)
//			ie = end;
//		else
//			ie = e.end;
	
	}
	

	public ExonList subtractExon(Exon e)
	{
		ExonList outEL = new ExonList();
		if(this.overlaps(e))
		{
			if(this.start > e.start && this.end < e.end)
			{
				return outEL;
			}
			if(this.start < e. start && this.end > e.end)
			{
				outEL.add(new Exon(this.start, e. start));
				outEL.add(new Exon(e.end, this.end));
				return outEL;
			}
			outEL.add(new Exon( 
					(this.start < e.start) ? this.start : e.end, 
					(this.end > e.end) ?  this.end : e.start));
		}
		return outEL;
	}

}
