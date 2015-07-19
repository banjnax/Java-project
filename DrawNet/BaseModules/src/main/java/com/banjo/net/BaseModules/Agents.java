package com.banjo.net.BaseModules;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Agents {
	public ArrayList<Agent> ags;
	public Agents(){
		this.ags = new ArrayList<Agent>();
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		Iterator<Agent> it = ags.iterator();
		while(it.hasNext()){
			Agent ag = it.next();
			g.setColor(ag.self_color);
			ag.paint(g);
		}
		g.setColor(c);
	}
}
