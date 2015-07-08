package com.banjo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Road {
	ArrayList<Line> ls =new ArrayList<Line>();
	public Road(){
		ls.add(
				new Line(new Point(200,200),new Point(800,200))
				);
		for(int i=0;i<10;i++){
			ls.add(
					new Line(new Point(250,200+20*i),new Point(350,200+20*i))
					);
			ls.add(
					new Line(new Point(650,200+20*i),new Point(750,200+20*i))
					);
		}
		ls.add(
				new Line(new Point(200,420),new Point(800,420))
				);
	}
	public void paint(Graphics g){
		Color c= g.getColor();
		
		Iterator<Line> it = ls.iterator();
		while(it.hasNext()) it.next().paint(g);
		
		g.setColor(c);
	}
}
