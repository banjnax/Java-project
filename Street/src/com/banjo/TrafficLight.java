package com.banjo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class TrafficLight {
	boolean status=false;
	int inteval;
	int count;
	int x;
	int y;
	public TrafficLight(boolean s,int x,int y,int inteval){
		this.status=s;
		this.x=x;
		this.y=y;
		this.inteval = inteval;
		this.count = new Random().nextInt(inteval);
	}
	public void run(){
		count++;
		if(count==inteval) {
			status=!status;
			count=0;
		}
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.red);
		if(status)
			g.setColor(Color.green);
		g.fillOval(x-10, y-10, 20, 20);
		g.setColor(c);
	}
}
