package rslib;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GDiscRange extends GRange {
	
//	protected String name, chr;
	protected int[] segStart, segEnd;
	int numSeg;
	
	GDiscRange(String chr, int[] st, int[] end){
		this.chr = chr;
		this.segStart = st;
		this.segEnd = end;
	}
	
	GDiscRange(){

	}
	
	GDiscRange(String chr){
		this.chr = chr;
	}
	
	public int getLength()
	{
		int x = 0;
		for(int i = 0; i < numSeg; i++){
			x += (segEnd[i] - segStart[i] + 1);
		}
		return x;
	}
		
	public boolean overlaps(GRange r){
		if(!chr.equals(r.chr)){
			return false;
		}
		if(r instanceof GDiscRange)
		{
			GDiscRange gdR = (GDiscRange) r;
			for(int i = 0; i < (gdR.segStart).length; i++)
			{
				if(overlaps(gdR.segStart[i], gdR.segEnd[i]))
				{
					return true;
				}
			}
			return false;
		}
		if(r instanceof GContigRange)
		{
			return overlaps(r.start, r.end);
		}
		return false;
	}
		
	private boolean overlaps(int s, int e){
		for(int i = 0; i < segStart.length; i++){
			if(segStart[i] >= e){
				continue;
			}
			if(segEnd[i] <= s){
				continue;
			}
			return true;
		}
		return false;
	}
	
	public boolean overlaps(ArrayList<GContigRange> a){
		if(! chr.equals(a.get(0).chr)){
			return false;
		}
		for(GContigRange r : a){
			if(overlaps(r)){
				return true;
			}
		}
		return false;
	}
	
	public void setSegments(int[] s, int[]e){
		segStart = s;
		segEnd = e;
		numSeg = segStart.length;
	}
	
	public void addSegment(int s, int e){
		segStart = Arrays.copyOf(segStart, (segStart.length + 1));
		segEnd = Arrays.copyOf(segEnd, (segEnd.length + 1));
		segStart[segStart.length - 1] = s;
		segEnd[segEnd.length -1] = e;
	}
	
	
	public int[] getStarts(){
		return segStart;
	}
	
	public int[] getEnds(){
		return segEnd;
	}
	
	public int getNumSegments(){
		return segStart.length;
	}
	
	//	abstract public GRange getBounds();
//	
//	abstract public ArrayList<GDiscRange> union(GRange a);
//	abstract public ArrayList<GDiscRange> intersection(GRange a);
//	abstract public ArrayList<GDiscRange> relativeComplement(GRange a);
}

