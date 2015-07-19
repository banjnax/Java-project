package com.banjo.net.BaseModules;

import java.awt.Color;
import java.awt.Graphics;

public class Agent {
	public Point self;
	public String label;
	int count=0;
	Color self_color = Color.black;
	public Agent(int x,int y, int s){
		this.self = new Point(x,y);
		this.label = s+"";
		while(s!=0){
			s/=10;
			count++;
		}
	}
	public void paint(Graphics g){
		Color c =g.getColor();
		g.setColor(self_color);
		g.drawOval(self.x-10, self.y-10, 20, 20);
		g.drawString(label, self.x-3-count*2, self.y+4);
		g.setColor(c);
	}
}
