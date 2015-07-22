package com.banjo.net.BaseModules;

import java.util.ArrayList;

public class Net {
	String name;
	public ArrayList<Node> nodes;
	public ArrayList<Link> links;
	
	public Net(String name,ArrayList<Node> nodes,ArrayList<Link> links){
		this.name = name;
		this.nodes = nodes;
		this.links = links;
	}
	public String print(){
		return this.name;
	}
}
