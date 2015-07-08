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
	Image vImage=null;//˫����������˸������ͼƬ
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
		// ���ػ溯����ʵ��˫�������
		offScreenImage = this.createImage(C_WIDTH, C_HEIGHT);
        // ��ý�ȡͼƬ�Ļ���
		gImage = offScreenImage.getGraphics();
        // ��ȡ�����ĵ�ɫ����ʹ��������ɫ��仭��,���û�����Ч���Ļ����������϶���Ч��
        gImage.setColor(gImage.getColor());
        gImage.fillRect(0, 0, C_WIDTH, C_HEIGHT); // �������һ��ͼ��Ĺ��ܣ��൱��gImage.clearRect(0, 0, WIDTH, HEIGHT)     
        // ���ø�����ػ淽����������ǽ�ȡͼƬ�ϵĻ�������ֹ�ٴ���ײ����ػ�     
        super.paint(gImage);
		
		bc.paint(gImage);
		Iterator<Passer> it = ps.iterator();
		while(it.hasNext())
			it.next().paint(gImage);
		
		// ����������ͼƬ���ص����廭����ȥ�����ܿ���ÿ�λ���Ч��    
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}

}
