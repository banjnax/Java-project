package com.banjo.net.basemodules;

import java.io.Serializable;

public class Point  implements Serializable{

	private static final long serialVersionUID = 1L;
	int x;
	int y;
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
}
