package com.banjo;

import java.awt.Color;
import java.awt.Graphics;

public class Small_check {
	public int loc_x;
	public int loc_y;
	public int lab_x;
	public int lab_y;
	static int size=50;
	static int init_x=50;
	static int init_y=50;
	public Small_check(int x,int y){
		this.lab_x=x;
		this.lab_y=y;
		this.loc_x=init_x+(x-1)*size;
		this.loc_y=init_y+(y-1)*size;
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.drawRect(loc_x, loc_y, size, size);
		g.setColor(c);
	}
	public Point getCenter(){
		return new Point(this.loc_x+size/2,this.loc_y+size/2);
	}
	public Small_check getUpCheck(){
		return new Small_check(this.lab_x,this.lab_y-1);
	}
	public Small_check getDownCheck(){
		return new Small_check(this.lab_x,this.lab_y+1);
	}
	public Small_check getLeftCheck(){
		return new Small_check(this.lab_x-1,this.lab_y);
	}
	public Small_check getRightCheck(){
		return new Small_check(this.lab_x+1,this.lab_y);
	}
}
