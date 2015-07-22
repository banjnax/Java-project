package com.banjo.net.BaseModules;

import java.util.ArrayList;
import java.util.Iterator;

public class ReachableMatrix extends BaseMatrix{
	public ArrayList<Link> links;
	public ReachableMatrix(int row,int col, ArrayList<Link> links){
		super(row,col);
		this.links = links;
		constructMatrix();
	}
	public void constructMatrix(){
		Iterator<Link> it = this.links.iterator();
		while(it.hasNext()){
			Link l = it.next();
			this.matrix[l.label_start][l.label_end] = 1;
			if(!l.directLink)
				this.matrix[l.label_end][l.label_start] = 1;
		}
	}
	
}
