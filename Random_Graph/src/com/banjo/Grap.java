package com.banjo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Grap {
	ArrayList<Point> ps = new ArrayList<Point>();
	ArrayList<Line> ls = new ArrayList<Line>();
	static int number = 5;
	boolean[][] con;
	public Grap(){
		Random r = new Random();
		con = new boolean[number][number];
		for(int i=0;i<number;i++){
			int index1 = r.nextInt(600);
			int index2 = r.nextInt(600);
			Point p = new Point(index1,index2);
			ps.add(p);
			con[i][i] = true;
		}

	}
	public boolean fullCon(){
		for(int i=0;i<number;i++){
			for(int j=0;j<number;j++){
				if(!con[i][j]) return false;
			}
		}
		return true;
	}
	public void printFull(){
		System.out.println();
		for(int i=0;i<number;i++){
			for(int j=0;j<number;j++){
				System.out.print(con[i][j]+" ");
			}
			System.out.println();
		}
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		Iterator<Point> it = this.ps.iterator();
		Iterator<Line> its = this.ls.iterator();
		
		Point pp;
		while(it.hasNext()){
			pp = it.next();
			g.drawOval(pp.x-5, pp.y-5,10,10);	
		}
		Line ll;
		while(its.hasNext()){
			ll = its.next();
			ll.paint(g);
		}
		
		g.setColor(c);
	}
}
