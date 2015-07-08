package com.banjo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

public class Check extends JFrame{

	private static final long serialVersionUID = 1L;
	static int C_WIDTH=600;
	static int C_HEIGHT=600;
	static int Size_x=200;
	static int Siez_y=30;
	public BoardCheck bc = null;
	public ArrayList<Passer> ps = new ArrayList<Passer>();
	public Passer p = null;
	public Traces ts = null;
	Image vImage=null;//双缓冲消除闪烁的虚拟图片
	Image offScreenImage = null;
	Graphics gImage = null;
	public int count;
	public static void main(String[] args) {
		new Check().launch();
	}
	public void launch(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bc = new BoardCheck(10,10);
		initPs();
		ts = new Traces();
		this.setSize(C_WIDTH, C_HEIGHT);
		this.setLocation(Size_x, Siez_y);
		this.setVisible(true);
		while(count>0){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			repaint();
			for(int i=0;i<ps.size();i++){
				if(!ps.get(i).bingo) ps.get(i).move();
			}
			/*
			Iterator<Passer> it = ps.iterator();
			while(it.hasNext())
				it.next().move();
				*/
			System.out.print("*");
		}
	}
	public void initPs(){
		
		ps.add(new Passer(bc.getSmall(1,2),bc.getSmall(5,5), this,Color.black));
		this.count++;
		ps.add(new Passer(bc.getSmall(2,2),bc.getSmall(8,6), this,Color.blue));
		this.count++;
		ps.add(new Passer(bc.getSmall(9,1),bc.getSmall(1,8), this,Color.cyan));
		this.count++;
		ps.add(new Passer(bc.getSmall(7,4),bc.getSmall(2,5), this,Color.red));
		this.count++;
		ps.add(new Passer(bc.getSmall(6,3),bc.getSmall(6,10), this,Color.yellow));
		this.count++;
		ps.add(new Passer(bc.getSmall(3,5),bc.getSmall(8,7), this,Color.green));
		this.count++;	
		ps.add(new Passer(bc.getSmall(3,5),bc.getSmall(1,9), this,Color.orange));
		this.count++;
		
	}
	public void paint(Graphics g){
		// 在重绘函数中实现双缓冲机制
		offScreenImage = this.createImage(C_WIDTH, C_HEIGHT);
        // 获得截取图片的画布
		gImage = offScreenImage.getGraphics();
        // 获取画布的底色并且使用这种颜色填充画布,如果没有填充效果的画，则会出现拖动的效果
        gImage.setColor(gImage.getColor());
        gImage.fillRect(0, 0, C_WIDTH, C_HEIGHT); // 有清楚上一步图像的功能，相当于gImage.clearRect(0, 0, WIDTH, HEIGHT)     
        // 调用父类的重绘方法，传入的是截取图片上的画布，防止再从最底层来重绘     
        super.paint(gImage);
		
		bc.paint(gImage);
		Iterator<Passer> it = ps.iterator();
		while(it.hasNext())
			it.next().paint(gImage);
		
		// 将接下来的图片加载到窗体画布上去，才能考到每次画的效果    
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}

}
