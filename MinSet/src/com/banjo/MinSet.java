package com.banjo;

import java.util.ArrayList;
import java.util.Iterator;

public class MinSet {
	int[][] easy =  new int[][]{{0,0,7,2,3,8,0,0,0},
			{0,6,0,7,0,0,0,5,0},
			{0,0,0,4,0,0,0,0,2},
			{9,0,0,0,0,0,8,6,7},
			{1,0,0,0,0,0,0,0,3},
			{6,4,8,0,0,0,0,0,5},
			{7,0,0,0,0,3,0,0,0},
			{0,2,0,0,0,5,0,3,0},
			{0,0,0,2,7,4,9,0,0},
			};//���Գ�ʼ����
	int[][] htfs = null;//���зָ�
	int[][] vtfs = null;//���зָ�
	int[][] ctfs = null;//����ָ�
	ArrayList<BlankBlock> bbs = new ArrayList<BlankBlock>();
	public static void main(String[] args) {
		new MinSet().testLeft();
	}
	public MinSet(){
		 splitSudoku();
		 getBBS();
	}
	 public void testLeft(){
		 //easyStrategy();
		 middleStrategy();
	 }
	 public boolean easyStrategy(){//�򵥲��ԣ�leftֻ��һ�����ģ�ֱ�����ϣ�֮����£���֮����ֽ��м򵥲��ԣ�֪�����пո��left��������1�����֡�
		 int flag = 1,count=0;
		 boolean res = false;
		// System.out.println("��ʼ����Ϊ��");
		 printSudoku();
		/* for(int j=0;j<bbs.size();j++){
			 System.out.print("x="+bbs.get(j).coor_x+",y="+bbs.get(j).coor_y+":");
			 bbs.get(j).print();
		 }
		 */
		 while(flag==1){
			 flag=0;
			 count++;
			 for(int i=0;i<bbs.size();i++){
				 if(bbs.get(i).left.size()==1){
					 updateData(i);
					 i--;
					 flag=1;
					 res = true;
				 }
			 }
			// System.out.println("����"+count+"��ѭ����");
			/* for(int j=0;j<bbs.size();j++){
				 System.out.print("x="+bbs.get(j).coor_x+",y="+bbs.get(j).coor_y+":");
				 bbs.get(j).print();
			 }
			 */
		 }
		// System.out.println("�򵥲��Ժ������Ϊ��");
		// printSudoku();
		// System.out.println("����"+count+"��ѭ����");
		 return res;
	 }
	 public void updateData(int i){//�������������Լ��ָ�����п���������Ϊ��easy���Եĸ��£���ʱ��valueֵ�ǹ̶���һ��
		 int x = bbs.get(i).coor_x;
		 int y = bbs.get(i).coor_y;
		 int value = bbs.get(i).left.get(0);
		 int x0 = 3*(x/3),y0 = 3*y%3;
		 easy[x][y] = value;
		 htfs[x][y] = value;
		 vtfs[y][x] = value;
		 ctfs[x0+x/3][y0+y%3] = value;
		 bbs.remove(i);
		 for(int j=0;j<bbs.size();j++){
			 //System.out.print("x="+x+",y="+y+":");
			 //bbs.get(j).print();
			 int z = 3*(x/3)+y/3;
			 int z1 = 3*(bbs.get(j).coor_x/3)+bbs.get(j).coor_y/3;
			 if(bbs.get(j).coor_x==x){
				 if(bbs.get(j).left.contains(value)){
					 bbs.get(j).left.remove(new Integer(value));
				 	bbs.get(j).cantBe.add(value);
				 }
			 }
			 if(bbs.get(j).coor_y==y){
				 if(bbs.get(j).left.contains(value)){
					 bbs.get(j).left.remove(new Integer(value));
				 	bbs.get(j).cantBe.add(value);
				 }
			 }
			 if(z==z1){
				 if(bbs.get(j).left.contains(value)){
					 bbs.get(j).left.remove(new Integer(value));
				 	bbs.get(j).cantBe.add(value);
				 }
			 }
			
		 }
	 }
	 public void updateData(int i,int value){//�������������Լ��ָ�����п���������Ϊ��middle���Եĸ��£���ʱ��valueֵ�Ǵӷǹ̶���ѡ���һ��
		 int x = bbs.get(i).coor_x;
		 int y = bbs.get(i).coor_y;
		 int x0 = 3*(x/3),y0 = 3*y%3;
		 easy[x][y] = value;
		 htfs[x][y] = value;
		 vtfs[y][x] = value;
		 ctfs[x0+x/3][y0+y%3] = value;
		 bbs.remove(i);
		 for(int j=0;j<bbs.size();j++){
			 //System.out.print("x="+x+",y="+y+":");
			 //bbs.get(j).print();
			 int z = 3*(x/3)+y/3;
			 int z1 = 3*(bbs.get(j).coor_x/3)+bbs.get(j).coor_y/3;
			 if(bbs.get(j).coor_x==x){
				 if(bbs.get(j).left.contains(value)){
					 bbs.get(j).left.remove(new Integer(value));
				 	bbs.get(j).cantBe.add(value);
				 }
			 }
			 if(bbs.get(j).coor_y==y){
				 if(bbs.get(j).left.contains(value)){
					 bbs.get(j).left.remove(new Integer(value));
				 	bbs.get(j).cantBe.add(value);
				 }
			 }
			 if(z==z1){
				 if(bbs.get(j).left.contains(value)){
					 bbs.get(j).left.remove(new Integer(value));
				 	bbs.get(j).cantBe.add(value);
				 }
			 }
			
		 }
		 
		 
	 }
	 public void middleStrategy(){//�õ��Ƿ�����ո�������number��number����leftһ��һ���Եģ���������ǲ���һ������H,V,C��������canBe���ж�
		 int flag=1;
		 while(flag==1){
			 flag=0;
			 easyStrategy();
			 for(int i=0;i<bbs.size();i++){
				 for(int j=0;j<bbs.get(i).left.size();j++){
					 if(sureForThis(i,j)){
						 updateData(i,bbs.get(i).left.get(j));
						 i--;
						 flag=1;
						 break;
					 }
				 }
			 }
		 }
		 if(easyStrategy()) flag=1;
	 }
	 public boolean sureForThis(int i,int j){//�жϴ����е����пո��ܲ�����number��cantBe�в���
		 int value = bbs.get(i).left.get(j);
		 int x = bbs.get(i).coor_x;
		 int y = bbs.get(i).coor_y;
		 int z = 3*(x/3)+y/3;
		 ArrayList<BlankBlock> bb_h = new ArrayList<BlankBlock>();//����뵱ǰ�ո�����ȵ����пո�
		 ArrayList<BlankBlock> bb_v = new ArrayList<BlankBlock>();//����뵱ǰ�ո�����ȵ����пո�
		 ArrayList<BlankBlock> bb_c = new ArrayList<BlankBlock>();//����뵱ǰ�ո����ȵ����пո�
		 for(int jj=0;jj<bbs.size();jj++){
			 System.out.print("x="+bbs.get(jj).coor_x+",y="+bbs.get(jj).coor_y+":");
			 bbs.get(jj).print();
		 }
		 
		 
		 
		 for(int k=0;k<bbs.size();k++){
			 if(bbs.get(k).coor_x==x) bb_h.add(bbs.get(k));
			 if(bbs.get(k).coor_y==y) bb_v.add(bbs.get(k));
			 if((3*(bbs.get(k).coor_x/3)+bbs.get(k).coor_y/3)==z) bb_c.add(bbs.get(k));
		 }
		 bb_h.remove( bbs.get(i));
		 bb_v.remove( bbs.get(i));
		 bb_c.remove( bbs.get(i));
		 return judgeBB(bb_h, value)||judgeBB(bb_v, value)||judgeBB(bb_c, value);
	 }
	 public boolean judgeBB(ArrayList<BlankBlock> bb,int value){
		 Iterator<BlankBlock> it = bb.iterator();
		 while(it.hasNext()){
			 if(!it.next().cantBe.contains(value)) return false;
		 }
		 return true;
	 }
	 public void getBBS(){
		 for(int i=0;i<9;i++){
			 for(int j=0;j<9;j++){
				 if(easy[i][j]==0) bbs.add(new BlankBlock(i, j, this));
			 }
		 }
	 }
	 public void printSudoku(){
		 for(int i=0;i<9;i++){
			 for(int j=0;j<9;j++){
				 System.out.print(easy[i][j]+" ");
			 }
			 System.out.println();
		 }
	 }
	 //������split����ģ��
	 public void  splitSudoku(){
			htfs = spHor();
			vtfs = spVer();
			ctfs = spCro();
	}
	public int[][] spHor(){
		int[][] HJT =new int[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				HJT[i][j] =easy[i][j];
			}
		}
		return HJT;
	}
	public int[][] spVer(){
		int[][] HJT =new int[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				HJT[i][j]=easy[j][i];
			}
		}
		return HJT;
	}
	public int[][] spCro(){
		int[][] HJT =new int[9][9];
		int k=0,l=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				l=0;
				for(int x=3*i;x<3*i+3;x++){
					for(int y=3*j;y<3*j+3;y++){
						HJT[k][l++] = easy[x][y];//��ԭ��ʽΪ��x=x0+j/3,y=y0+j%3,x0=3*(i/3),y0=3*i%3
					}
				}
				k++;
			}
		}
		return HJT;
	}
	public void printH(){
		for(int i=0;i<9;i++){
			System.out.print("��"+i+"�У�");
			for(int j=0;j<9;j++){
				System.out.print(htfs[i][j]);
			}
			System.out.println();
		}
	}
	public void printV(){
		for(int i=0;i<9;i++){
			System.out.print("��"+i+"�У�");
			for(int j=0;j<9;j++){
				System.out.print(vtfs[i][j]);
			}
			System.out.println();
		}
	}
	public void printC(){
		for(int i=0;i<9;i++){
			System.out.print("��"+i+"�飺");
			for(int j=0;j<9;j++){
				System.out.print(ctfs[i][j]);
			}
			System.out.println();
		}
	}
}
