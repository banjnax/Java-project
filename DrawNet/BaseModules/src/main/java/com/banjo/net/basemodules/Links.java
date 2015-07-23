package com.banjo.net.basemodules;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Links {
	public ArrayList<Link> ls;
	public Links(){
		ls = new ArrayList<Link>();
	}
	public void paint(Graphics g){
		Iterator<Link> it = ls.iterator();
		while(it.hasNext())it.next().paint(g);
	}
}
