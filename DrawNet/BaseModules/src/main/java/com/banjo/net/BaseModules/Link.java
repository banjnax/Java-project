package com.banjo.net.BaseModules;

import java.awt.Color;
import java.awt.Graphics;

public class Link {
	Point start;
	Point end;
	public int label_start;
	public int label_end;
	public int weight;
	public boolean directLink = false;
	public Color self_color = Color.gray;
	public Link(Point s,Point e){
		this.start = new Point(s.x,s.y);
		this.end = new Point(e.x,e.y);
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(self_color);
		if(directLink){
			this.paintk(g, start.x, start.y, end.x, end.y);
		}
		g.drawLine(start.x, start.y, end.x, end.y);
		g.drawString(weight+"", (start.x+end.x)/2, (start.y+end.y)/2);
		g.setColor(c);
	}
	
	
	 /** 
     * 画带箭头的线
     *  */ 
      public   void  paintk(Graphics g,  int  x1,  int  y1,  int  x2,  int  y2)  {

         double  H  =   10 ;  // 箭头高度    
          double  L  =   7 ; // 底边的一半   
          int  x3  =   0 ;
         int  y3  =   0 ;
         int  x4  =   0 ;
         int  y4  =   0 ;
         double  awrad  =  Math.atan(L  /  H);  // 箭头角度    
          double  arraow_len  =  Math.sqrt(L  *  L  +  H  *  H); // 箭头的长度    
          double [] arrXY_1  =  rotateVec(x2  -  x1, y2  -  y1, awrad,  true , arraow_len);
         double [] arrXY_2  =  rotateVec(x2  -  x1, y2  -  y1,  - awrad,  true , arraow_len);
         double  x_3  =  x2  -  arrXY_1[ 0 ];  // (x3,y3)是第一端点    
          double  y_3  =  y2  -  arrXY_1[ 1 ];
         double  x_4  =  x2  -  arrXY_2[ 0 ]; // (x4,y4)是第二端点    
          double  y_4  =  y2  -  arrXY_2[ 1 ];

        Double X3  =   new  Double(x_3);
        x3  =  X3.intValue();
        Double Y3  =   new  Double(y_3);
        y3  =  Y3.intValue();
        Double X4  =   new  Double(x_4);
        x4  =  X4.intValue();
        Double Y4  =   new  Double(y_4);
        y4  =  Y4.intValue();
         // g.setColor(SWT.COLOR_WHITE);
         // 画线 
         g.drawLine(x1, y1, x2, y2);
         // 画箭头的一半 
         g.drawLine(x2, y2, x3, y3);
         // 画箭头的另一半 
         g.drawLine(x2, y2, x4, y4);
    } 
    
     /** 
     *取得箭头的绘画范围
      */ 
     public   double [] rotateVec( int  px,  int  py,  double  ang,  boolean  isChLen,
             double  newLen)  {

         double  mathstr[]  =   new   double [ 2 ];
         // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度    
          double  vx  =  px  *  Math.cos(ang)  -  py  *  Math.sin(ang);
         double  vy  =  px  *  Math.sin(ang)  +  py  *  Math.cos(ang);
         if  (isChLen)  {
             double  d  =  Math.sqrt(vx  *  vx  +  vy  *  vy);
            vx  =  vx  /  d  *  newLen;
            vy  =  vy  /  d  *  newLen;
            mathstr[ 0 ]  =  vx;
            mathstr[ 1 ]  =  vy;
        } 
         return  mathstr;
    }
     
	
}
