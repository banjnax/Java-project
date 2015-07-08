package com.banjo;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Traces {
	ArrayList<Line> ls = null;
	public Traces(){
		ls = new ArrayList<Line>();
	}
	public void paint(Graphics g){
		Iterator<Line> it = ls.iterator();
		while(it.hasNext()){
			it.next().paint(g);
		}
	}
}
