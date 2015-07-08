package com.banjo;

import java.awt.Color;
import java.awt.Graphics;

public class Line {
	public Point start = null;
	public Point end = null;
	Color self_color = null;
	public Line(Point s,Point e,Color c){
		this.start=s;
		this.end=e;
		this.self_color = c;
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(this.self_color);
		g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
		g.setColor(c);
		
	}
	
}
