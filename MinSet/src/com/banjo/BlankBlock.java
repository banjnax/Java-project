package com.banjo;

import java.util.ArrayList;
import java.util.Iterator;

public class BlankBlock {
	public int coor_x;
	public int coor_y;
	ArrayList<Integer> left = new ArrayList<Integer>();
	ArrayList<Integer> cantBe = new ArrayList<Integer>();
	MinSet ms = null;
	public BlankBlock(int x,int y, MinSet m_s){
		coor_x = x;
		coor_y = y;
		ms = m_s;
		getL_R(x,y);
	}
	public void print(){
		 Iterator<Integer> it = left.iterator();
		 System.out.print("可取数字：");
		 while(it.hasNext()) System.out.print(it.next()+" ");
		 System.out.print("  ");
		 it = cantBe.iterator();
		 System.out.print("不可取数字：");
		 while(it.hasNext()) System.out.print(it.next()+" ");
		 System.out.println();
	 }
	public ArrayList<Integer> getL_R(int x,int y){//得到一个空格坐标(x,y)处的只能填的（left）和不可以填的（cantBe）
		 initLeft(left);
		 initLeft(cantBe);
		 int z = 3*(x/3)+y/3;//得到在ctfs中的行数
		 for(int i=0;i<9;i++){
			 if(left.contains(ms.htfs[x][i])) left.remove(new Integer(ms.htfs[x][i]));
			 if(left.contains(ms.vtfs[y][i])) left.remove(new Integer(ms.vtfs[y][i]));
			 if(left.contains(ms.ctfs[z][i])) left.remove(new Integer(ms.ctfs[z][i]));
		 }
		 cantBe.removeAll(left);
		 return left;
	 }
	 public void initLeft(ArrayList<Integer> left){
		 for(int i=1;i<=9;i++) left.add(i);
	 }
	 
}
