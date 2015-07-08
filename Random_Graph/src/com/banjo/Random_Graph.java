package com.banjo;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JFrame;

public class Random_Graph extends JFrame{
	private static final long serialVersionUID = 1L;
	static int C_WIDTH=600;
	static int C_HEIGHT=600;
	static int Size_x=200;
	static int Siez_y=30;
	Image vImage=null;//˫����������˸������ͼƬ
	Image offScreenImage = null;
	Graphics gImage = null;
	Grap gs = new Grap();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Random_Graph().launch();
	}
	public void launch(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(C_WIDTH, C_HEIGHT);
		this.setLocation(Size_x, Siez_y);
		this.setVisible(true);
		Random r = new Random();
		while(!gs.fullCon()){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			int index1 = r.nextInt(gs.ps.size());
			int index2 = r.nextInt(gs.ps.size());
			if(!gs.con[index1][index2]){
				gs.ls.add(
						new Line(gs.ps.get(index1),gs.ps.get(index2))
				);
			}
			gs.con[index1][index2] = true;
			gs.con[index2][index1] = true;
			//gs.printFull();
		}
		System.out.println("I am already full connected!:)");
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
        
        gs.paint(gImage);
		// ����������ͼƬ���ص����廭����ȥ�����ܿ���ÿ�λ���Ч��    
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}

}
