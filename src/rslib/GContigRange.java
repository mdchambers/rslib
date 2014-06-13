package rslib;

import java.util.ArrayList;

public abstract class GContigRange extends GRange
{
	
	
	//Constructors
	public GContigRange(int start, int end)
	{
		this("NA", start, end);
	}
	
	public GContigRange(String chr, int start, int end)
	{
		this.start = start;
		this.end = end;
		this.chr = chr;
	}

	protected GContigRange(){
		
	}
	
	//Accessors
	
	
	public int getLength(){
		return end - start;
	}
	
	//Setters
	public void setBounds(int s, int e){
		start = s;
		end = e;
	}
	
	
	public boolean overlaps(GRange ir){
		if(ir instanceof GContigRange){
			return overlaps(ir.start, ir.end);
		}
		if(ir instanceof GDiscRange){
			GDiscRange gdr = (GDiscRange) ir;
			for(int i = 0; i < gdr.segStart.length; i++){
				if(overlaps(gdr.segStart[i], gdr.segEnd[i])){
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public boolean overlaps(int s, int e)
	{
		if(start >= e)
			return false;
		if(end <= s)
			return false;
		return true;
	}
	
	abstract public ArrayList<GContigRange> union(GContigRange a);
	abstract public ArrayList<GContigRange> intersection(GContigRange a);
	abstract public ArrayList<GContigRange> intersection(GDiscRange a);
	abstract public ArrayList<GContigRange> relativeComplement(GContigRange a);
}
