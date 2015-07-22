package com.banjo.net.BaseModules;

import java.util.ArrayList;
import java.util.Iterator;

public class AdjacentMatrix extends BaseMatrix{
	public int type;
	public ArrayList<Link> links;
	public AdjacentMatrix(int row,int col, ArrayList<Link> links,int type){
		super(row,col);
		this.links = links;
		this.type = type;
		constructMatrix();
	}
	public void constructMatrix(){
		if(this.type == Net.UNDIRECT_NETWORK){
			Iterator<Link> it = this.links.iterator();
			while(it.hasNext()){
				Link l = it.next();
				this.matrix[l.label_start][l.label_end] = l.weight;
				this.matrix[l.label_end][l.label_start] = l.weight;
			}
		}
		else{
			Iterator<Link> it = this.links.iterator();
			while(it.hasNext()){
				Link l = it.next();
				if(l.directLink){
					this.matrix[l.label_start][l.label_end] = l.weight;
				}
				else{
					this.matrix[l.label_start][l.label_end] = l.weight;
					this.matrix[l.label_end][l.label_start] = l.weight;
				}
			}
		}
	}
}
