package com.banjo.net.basemodules;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Nodes {
	public ArrayList<Node> ags;
	public Nodes(){
		this.ags = new ArrayList<Node>();
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		Iterator<Node> it = ags.iterator();
		while(it.hasNext()){
			Node ag = it.next();
			g.setColor(ag.self_color);
			ag.paint(g);
		}
		g.setColor(c);
	}
}
