package com.banjo;

import java.awt.Color;
import java.awt.Graphics;

public class Line {
	Point start;
	Point end;
	public Line(Point s,Point e){
		start = new Point(s.x,s.y);
		end = new Point(e.x,e.y);
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.drawLine(start.x, start.y, end.x, end.y);
		g.setColor(c);
	}
}
