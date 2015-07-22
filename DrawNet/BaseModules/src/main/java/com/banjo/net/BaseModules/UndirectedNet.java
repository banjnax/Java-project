package com.banjo.net.BaseModules;

import java.util.ArrayList;

public class UndirectedNet extends Net{

	public UndirectedNet(String name,ArrayList<Node> nodes,ArrayList<Link> links,int type){
		super(name,nodes,links,type);
	}
	
	@Override
	public String print(){
		return "AdjacentMatrix:\n"+printMatrix(adjMatrix.useMatrix) + "\nReachableMatrix:\n"+printMatrix(reaMatrix.useMatrix);
	}
}
