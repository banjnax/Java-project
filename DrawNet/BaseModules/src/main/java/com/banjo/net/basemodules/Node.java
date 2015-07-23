package com.banjo.net.basemodules;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Node implements Serializable{

	private static final long serialVersionUID = 1L;
	public Point self;
	public String label;
	public int number;
	int count=0;
	Color self_color = Color.green;
	public Node(int x,int y, int s){
		this.self = new Point(x,y);
		this.label = s+"";
		this.number = s;
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
