package com.banjo;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BoardCheck {
	ArrayList<Small_check> scs = new ArrayList<Small_check>();
	int[] noavail_x;
	int[] noavail_y;
	public BoardCheck(int x,int y){
		noavail_x = new int[x+1];
		noavail_y = new int[y+1];
		noavail_x[0]=x;
		noavail_y[0]=y;
		for(int i=1;i<=x;i++){
			noavail_x[i]=0;
			for(int j=1;j<=y;j++){
				noavail_y[j]=0;
				scs.add(new Small_check(i,j));
			}
		}

	}
	public void paint(Graphics g){
		Iterator<Small_check> it = scs.iterator();
		while(it.hasNext()){
			Small_check sc = it.next();
			sc.paint(g);
		}
	}
	public Small_check getSmall(int x,int y){
		Iterator<Small_check> it = scs.iterator();
		Small_check sc = null;
		while(it.hasNext()){
			sc = it.next();
			if(sc.lab_x==x&&sc.lab_y==y) break;
		}
		return sc;
	}
	public void check_and_mod(){
		int flag=0;
		for(int i=1;i<=noavail_x[0];i++){
			if(noavail_x[i]==0){
				flag=1;
				break;
			}
		}
		if(flag==0){
			noavail_x[new Random().nextInt(noavail_x[0])+1]=0;
		}
		flag=0;
		for(int i=1;i<=noavail_y[0];i++){
			if(noavail_y[i]==0){
				flag=1;
				break;
			}
		}
		if(flag==0){
			noavail_y[new Random().nextInt(noavail_y[0])+1]=0;
		}
	}
}
