package com.banjo.net.NetFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import com.banjo.net.BaseModules.*;


public class DrawNetwork extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static int G_WIDTH = 600;
	private static int G_HEIGHT = 600;
	private static int G_X = 400;
	private static int G_Y = 100;
	
	Image vImage=null;
	Image offScreenImage = null;
	Graphics gImage = null;
	int count=0;
	
	Agents agents = new Agents();
	Lines edges = new Lines();
	
	JTextField start = new JTextField("start");
	JTextField end = new JTextField();
	JTextField weight = new JTextField();
	JButton link = new JButton("Link");
	public static void main(String[] args) {
		new DrawNetwork().launch();
	}
	public void launch(){
		this.setSize(G_WIDTH,G_HEIGHT);
		this.setLocation(G_X,G_Y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		
		JPanel palette = new JPanel();
		JPanel operation = new JPanel();
		operation.setLayout(new GridLayout(4, 1));
		

		link.addActionListener(this);
		
		this.add(palette,BorderLayout.CENTER);
		operation.add(start);
		operation.add(end);
		operation.add(weight);
		operation.add(link);
		this.add(operation,BorderLayout.EAST);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				agents.ags.add(new Agent(e.getX(),e.getY(),++count));
			}
		});
	}
	public void paint(Graphics g){
		offScreenImage = this.createImage(G_WIDTH, G_HEIGHT);
		gImage = offScreenImage.getGraphics();
        gImage.setColor(gImage.getColor());
        gImage.fillRect(0, 0, G_WIDTH, G_HEIGHT); 
        super.paint(gImage);
		
		this.agents.paint(gImage);
		this.edges.paint(gImage);
		
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==link){
			int label_start = Integer.parseInt(start.getText())-1;
			int label_end = Integer.parseInt(end.getText())-1;
			int w = Integer.parseInt(weight.getText());
			
			Line l = new Line(agents.ags.get(label_start).self,agents.ags.get(label_end).self);
			l.label_start = label_start;
			l.label_end = label_end;
			l.weight = w;
			edges.ls.add(l);
			repaint();
		}

	}


}
