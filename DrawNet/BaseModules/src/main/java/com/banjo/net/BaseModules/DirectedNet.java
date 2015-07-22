package com.banjo.net.BaseModules;

import java.util.ArrayList;

public class DirectedNet extends Net{

	public DirectedNet(String name,ArrayList<Node> nodes,ArrayList<Link> links,int type){
		super(name,nodes,links,type);
	}
	
	@Override
	public String print(){
		return "AdjacentMatrix:\n"+printMatrix(adjMatrix.useMatrix) + "\nReachableMatrix:\n"+printMatrix(reaMatrix.useMatrix);
	}
	
}
