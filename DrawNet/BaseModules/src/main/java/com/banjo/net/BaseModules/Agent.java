package com.banjo.net.BaseModules;

import java.awt.Color;
import java.awt.Graphics;

public class Agent {
	public Point self;
	public String label;
	int count=0;
	Color self_color = Color.green;
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
		g.fillOval(self.x-15, self.y-15, 30, 30);
		g.setColor(Color.black);
		g.drawString(label, self.x-3-count*2, self.y+4);
		g.setColor(c);
	}
}
