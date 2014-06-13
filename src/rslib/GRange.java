package rslib;

//import java.util.ArrayList;

public abstract class GRange {
	
	protected int start, end;
	
	protected String chr;//, name;
	
	
	public int getStart(){
		return start;
	}
	
	public int getEnd(){
		return end;
	}
	
	public String getChr(){
		return chr;
	}
	
//	public String getName(){
//		return name;
//	}
//	
//	public void setName(String s){
//		name = s;
//	}
	
	public void setChr(String c){
		chr = c;
	}
	
	public boolean sameChr(GContigRange i){
		return chr.equals(i.getChr());
	}
	
	public abstract int getLength();
//	public abstract int
	
	abstract public String toString();
	abstract public boolean overlaps(GRange r);
//	abstract public ArrayList<GContigRange> getBounds();
//	
//	abstract public ArrayList<GContigRange> union(GContigRange a);
//	abstract public ArrayList<GContigRange> intersection(GContigRange a);
//	abstract public ArrayList<GContigRange> relativeComplement(GContigRange a);
}
