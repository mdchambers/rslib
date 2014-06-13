package rslib;

import java.util.ArrayList;
//import java.io.*;
//import java.util.Scanner;
//import java.lang.StringBuilder;
//import java.lang.Integer;

public class ExonList extends ArrayList<Exon> 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1380780071910193883L;
	String assembly;
	
	private static void log(String s)
	{
		UITest.writeLog("EXL: " + s + "\n");
	}
	
	private static void log2(String s, String... ses)
	{
		StringBuilder sb = new StringBuilder(s);
		for(String x : ses)
		{
			sb.append(" ");
			sb.append(x);
		}
		UITest.writeLog(sb.toString());
	}
	
	public ExonList(ArrayList<Exon> exons)
	{
		for(Exon e : exons)
		{
			this.add(e);
		}
	}
	
	
	public ExonList(Gene g, boolean consOnly, boolean uniqueOnly)
	{
		if(consOnly){
			this.addConsExons(g);
		}
		else if(uniqueOnly){
			this.addUniqExons(g);
		}
		else{
			this.addAllExons(g);
		}
		
//		log("Const : " + this.size());
	}
	
	public ExonList(int num)
	{
		super(num);
	}
	
	public ExonList()
	{
		super();
	}
	
	public void addExons(ArrayList<Exon> alEx)
	{
		for(Exon e : alEx)
		{
			this.add(e);
		}
	}
	
	public void addAllExons(Gene g)
	{
		for(Transcript t : g.transc)
		{
			for(Exon e : t.exons)
			{
				this.add(e);
			}
		}
	}
	
	public void addUniqExons(Gene g)
	{
		addAllExons(g);
		for(int i = 0; i < this.size(); i++)
		{
			for(int j = i+1; j < this.size(); j++)
			{
				if(Exon.equals(this.get(i), this.get(j)))
				{
					this.remove(j);
					j--;
				}
			}
		}
	}
	
	public void addConsExons(Gene g)
	{
		Gene conGene = g.getConstitutive();
		
		for (Exon e : conGene.transc.get(0).exons)
		{
			this.add(e);
		}
	}
	
	public int minStart()
	{
		int i = this.get(0).start;
		for (Exon e : this)
		{
			i = Math.min(i, e.start);
		}
		return i;
	}
	
	public int maxEnd()
	{
		int i = this.get(0).end;
		for(Exon e : this)
		{
			i = Math.max(i, e.end);
		}
		return i;
	}
	
	
	public static ExonList getIntersection(ExonList e1, ExonList e2)
	{
//		ExonList exlg1 = new ExonList(g1, false, true);
//		ExonList exlg2 = new ExonList(g2, false, true);
		ExonList exlout = new ExonList();
		for(Exon e : e1)
		{
			for(Exon d : e2)
			{
				if(e.overlaps(d))
				{
					exlout.add(Exon.getIntersection(e, d));
				}
			}
		}
		if(exlout.size() == 0)
		{
			return null;
		}
		return exlout;
	}
	
	public void merge(ExonList e)
	{
		for(int i = 0; i < this.size(); i++)
		{
			for(int j = 0; j < e.size(); j++)
			{
				
			}
		}
	}
	
	
	
	public static void subtractIntersection(ExonList el1, ExonList el2)
	{
		ExonList elIS = getIntersection(el1, el2);
		if(elIS != null)
		{
//			log("elIS not null");
			el1.subtractExonList(elIS);
			el2.subtractExonList(elIS);
		}
	}
	
	public void subtractExonList(ExonList el)
	{
//		log("elIS " + el.size());
//		el.write();
//		log("this " + this.size());
//		this.write();
		for(int j = 0; j < el.size(); j++)
		{
			for(int i = 0; i < this.size(); i++)
			{
				Exon e = this.get(i);
				Exon d = el.get(j);
//				log("Before subtractExon");
				ExonList t = e.subtractExon(d);
//				log2("subtracting", e.toString(), d.toString(), Integer.toString(t.size()));
				switch (t.size()) 
				{
					case 0:
						this.remove(i);
//						log("case 0");
						break;
					case 1:
						this.set(i, t.get(0));
//						log("case 1");
						break;
					case 2:
//						log("case 2");
						this.set(i, t.get(0));
						this.add(i, t.get(0));
						i++;
						if(i == this.size())
						{
							continue;
						}
						break;
				}
			}

		}
		
		
	}

	//Output methods
	
	public void write()
	{
		for(Exon e : this)
		{
			System.out.println("yo: " + e.toString());
		}
	}
	
	
}