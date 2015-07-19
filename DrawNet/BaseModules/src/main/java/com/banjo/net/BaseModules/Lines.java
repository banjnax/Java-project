package com.banjo.net.BaseModules;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Lines {
	public ArrayList<Line> ls;
	public Lines(){
		ls = new ArrayList<Line>();
	}
	public void paint(Graphics g){
		Iterator<Line> it = ls.iterator();
		while(it.hasNext())it.next().paint(g);
	}
}
