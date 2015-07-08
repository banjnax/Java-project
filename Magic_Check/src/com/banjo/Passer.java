package com.banjo;

import java.awt.Color;
import java.awt.Graphics;

public class Passer {
	public int loc_x;
	public int loc_y;	
	public Small_check now;
	private int size=8;
	public Small_check end;
	public Check ck = null;
	public Traces ts = null;
	public boolean bingo = false;
	Color self_color = null;
	private int init_x,init_y;
	public Passer(Small_check init,Small_check des,Check ck,Color c){
		this.now=init;
		this.end=des;	
		this.loc_x=init.getCenter().x;
		this.loc_y=init.getCenter().y;
		this.init_x = this.loc_x;
		this.init_y = this.loc_y;
		this.ck=ck;
		ts = new Traces();
		ck.bc.noavail_x[init.lab_x]=1;
		ck.bc.noavail_y[init.lab_y]=1;
		this.self_color = c;
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(this.self_color);
		g.drawOval(this.init_x-this.size/2, this.init_y-this.size/2, this.size, this.size);
		g.fillOval(this.loc_x-this.size/2, this.loc_y-this.size/2, this.size, this.size);
		this.ts.paint(g);
		g.setColor(c);
	}
	public void move(){//我们定义规则先从Y轴进发，让后再从X轴进发到达des,以后规则更丰富
		int temx = this.now.lab_x-this.end.lab_x;
		int temy = this.now.lab_y-this.end.lab_y;
		Small_check temsc = this.now;
		if(temx==0&&temy==0){
			System.out.println("到达位置！"+this.ck.count);
			this.bingo = true;
			this.ck.count--;
			this.ck.bc.noavail_x[this.now.lab_x]=0;
			this.ck.bc.noavail_y[this.now.lab_y]=0;
		}
		if(!bingo){
			int flag=0;
			int judge=0;
			if(temy==0){
				judge=1;
				if(temx>0){
					if(this.ck.bc.noavail_x[this.now.getLeftCheck().lab_x]==0){
						this.ck.bc.noavail_x[this.now.getLeftCheck().lab_x]=1;
						this.ck.bc.noavail_x[this.now.lab_x]=0;
						this.now=this.now.getLeftCheck();
						flag=1;
					}
				}
				else
				{	
					judge=2;
					if(this.ck.bc.noavail_x[this.now.getRightCheck().lab_x]==0){
						this.ck.bc.noavail_x[this.now.getRightCheck().lab_x]=1;
						this.ck.bc.noavail_x[this.now.lab_x]=0;
						this.now=this.now.getRightCheck();
						flag=1;
					}
				}
			}
			else
			{
				if(temy>0){
					judge=3;
					if(this.ck.bc.noavail_y[this.now.getUpCheck().lab_y]==0){
						this.ck.bc.noavail_y[this.now.getUpCheck().lab_y]=1;
						this.ck.bc.noavail_y[this.now.lab_y]=0;
						this.now=this.now.getUpCheck();
						flag=1;
					}
				}
				else
				{
					judge=4;
					if(this.ck.bc.noavail_y[this.now.getDownCheck().lab_y]==0){
						this.ck.bc.noavail_y[this.now.getDownCheck().lab_y]=1;
						this.ck.bc.noavail_y[this.now.lab_y]=0;
						this.now=this.now.getDownCheck();
						flag=1;
					}
				}
			}
			if(flag==0){
				switch(judge){
					case 1:this.ck.bc.noavail_x[this.now.getLeftCheck().lab_x]=0;break;
					case 2:this.ck.bc.noavail_x[this.now.getRightCheck().lab_x]=0;break;
					case 3:this.ck.bc.noavail_y[this.now.getUpCheck().lab_y]=0;break;
					case 4:this.ck.bc.noavail_y[this.now.getDownCheck().lab_y]=0;break;
				}
			}
			this.loc_x=this.now.getCenter().x;
			this.loc_y=this.now.getCenter().y;
			this.ts.ls.add(new Line(
					temsc.getCenter(),
					this.now.getCenter(),this.self_color));
		}
		
	}
}
