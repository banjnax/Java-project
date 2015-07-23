package com.banjo.net.basemodules;

import java.io.Serializable;
import java.util.ArrayList;

import Jama.Matrix;

public class Net implements Serializable{

	private static final long serialVersionUID = 1L;
	String name;
	public ArrayList<Node> nodes;
	public ArrayList<Link> links;
	public AdjacentMatrix adjMatrix;
	public ReachableMatrix reaMatrix;
	public int row;
	public int col;
	public int type;
	public static int UNDIRECT_NETWORK = 1;
	public static int DIRECT_NETWORK = 2;

	public Net(String name,ArrayList<Node> nodes,ArrayList<Link> links,int type){
		this.name = name;
		this.nodes = nodes;
		this.links = links;
		this.row = nodes.size();
		this.col = nodes.size();
		this.type = type;
		constructMatrix();
	}
	public void constructMatrix(){
		adjMatrix = new AdjacentMatrix(nodes.size(), nodes.size(), links, type);
		reaMatrix = new ReachableMatrix(nodes.size(), nodes.size(), links);
	}
	public String printMatrix(Matrix m){
		int i,j;
		String s = "";
		for(i=0;i<row;i++){
			for(j=0;j<col;j++){
				s+=(m.get(i, j)+" ");
			}
			s+="\n";
		}
		return s;
	}
	public String print(){
		return "";
	}
	@Override
	public String toString(){
		return this.name;
	}
}
