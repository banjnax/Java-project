package com.banjo;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;

public class Street extends JFrame{
	private static final long serialVersionUID = 1L;
	static int C_WIDTH=1000;
	static int C_HEIGHT=600;
	static int Size_x=200;
	static int Siez_y=30;
	Image vImage=null;//双缓冲消除闪烁的虚拟图片
	Image offScreenImage = null;
	Graphics gImage = null;
	ArrayList<Person> ps = new ArrayList<Person>();
	TrafficLight tl = new TrafficLight(false,410,175,new Random().nextInt(20)+1);
	TrafficLight tl2 = new TrafficLight(false,810,175,60);
	Road road = new Road();
	int countP=0;
	static int pn = 100;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Street().launch();
	}
	public void launch(){

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(C_WIDTH, C_HEIGHT);
		this.setLocation(Size_x, Siez_y);
		this.setVisible(true);
		initPeople();
		
		while(true){
			repaint();
			for(int i=0;i<ps.size();i++) ps.get(i).move();
			tl.run();
			tl2.run();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void initPeople(){
		Random r = new Random();
		int x,y;
		for(int i=0;i<Street.pn;i++){
			x = r.nextInt(150);
			y = r.nextInt(150);
			ps.add(
					new Person(this,225+x,420+y,r.nextDouble(),0.8)
			);
		}
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
		
        tl.paint(gImage);
        tl2.paint(gImage);
        road.paint(gImage);
        Iterator<Person> it = ps.iterator();
        while(it.hasNext()) it.next().paint(gImage);
		// 将接下来的图片加载到窗体画布上去，才能考到每次画的效果    
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}

}
