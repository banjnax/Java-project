package com.banjo.net.BaseModules;

import java.awt.Color;
import java.awt.Graphics;

public class Link {
	Point start;
	Point end;
	public int label_start;
	public int label_end;
	public int weight;
	public boolean directLink = true;
	public Color self_color = Color.red;
	public Link(Point s,Point e){
		this.start = new Point(s.x,s.y);
		this.end = new Point(e.x,e.y);
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(self_color);
		g.drawLine(start.x, start.y, end.x, end.y);
		g.drawString(weight+"", (start.x+end.x)/2, (start.y+end.y)/2);
		g.setColor(c);
	}
	
}
