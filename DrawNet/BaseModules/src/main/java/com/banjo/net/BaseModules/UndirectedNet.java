package com.banjo.net.BaseModules;

import java.util.ArrayList;

public class UndirectedNet extends Net{
	public AdjacentMatrix adjMatrix;
	public ReachableMatrix reaMatrix;
	public UndirectedNet(String name,ArrayList<Node> nodes,ArrayList<Link> links){
		super(name,nodes,links);
		this.adjMatrix = new AdjacentMatrix(nodes.size(), nodes.size(), links, AdjacentMatrix.UNDIRECT);
		this.reaMatrix = new ReachableMatrix(nodes.size(), nodes.size(), links);
	}
	@Override
	public String print(){
		return "AdjacentMatrix:\n"+printMatrix(adjMatrix) + "\nReachableMatrix:\n"+printMatrix(reaMatrix);
	}
	public String printMatrix(BaseMatrix bm){
		int row = bm.row;
		int col = bm.col;
		int i,j;
		String s = "";
		for(i=0;i<row;i++){
			for(j=0;j<col;j++){
				s+=(bm.matrix[i][j]+" ");
			}
			s+="\n";
		}
		return s;
	}
}
