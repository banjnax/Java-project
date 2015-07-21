package com.banjo.net.NetFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.banjo.net.BaseModules.Agent;
import com.banjo.net.BaseModules.Agents;
import com.banjo.net.BaseModules.Line;
import com.banjo.net.BaseModules.Lines;


public class DrawNetwork extends JFrame implements ActionListener , MouseListener{

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
	
	JTextField start = new JTextField(2);
	JTextField end = new JTextField(2);
	JTextField weight = new JTextField(2);
	JPanel palette = new JPanel();
	JTextArea history = new JTextArea(5,10);
	JButton clear = new JButton("Clear History");
	
	JLabel s = new JLabel("Start point:");
	JLabel e = new JLabel("End point:");
	JLabel w = new JLabel("Weight:");
	JLabel h = new JLabel("History:");
	JButton link = new JButton("Link");
	JScrollPane jcp = new JScrollPane(history);
	public static void main(String[] args) {
		new DrawNetwork().launch();
	}
	public void launch(){
		this.setSize(G_WIDTH,G_HEIGHT);
		this.setLocation(G_X,G_Y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		this.setResizable(false);
		
		
		
		palette.setBackground(Color.white);

		e.setHorizontalAlignment(JLabel.RIGHT);
		s.setHorizontalAlignment(JLabel.RIGHT);
		w.setHorizontalAlignment(JLabel.RIGHT);
		h.setHorizontalAlignment(JLabel.LEFT);
		
		start.setHorizontalAlignment(JTextField.CENTER);
		end.setHorizontalAlignment(JTextField.CENTER);
		weight.setHorizontalAlignment(JTextField.CENTER);
		jcp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		history.setLineWrap(true);//Auto Wrap
		history.setWrapStyleWord(true);//Do not split the word
		
		link.addActionListener(this);
		clear.addActionListener(this);
		
		GridBagConstraints cons = new GridBagConstraints();
		//Add the palette
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.gridwidth = 1;
		cons.gridheight = 7;
		this.add(palette,cons);
		
		//Add the operation control panel
		
		//Add start,end,weight
		cons.insets = new Insets(10, 10, 0, 0);
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		this.add(s,cons);
		
		cons.gridx = 2;
		cons.gridy = 0;
		this.add(start,cons);
		
		cons.gridx = 1;
		cons.gridy = 1;
		this.add(e,cons);
		
		cons.gridx = 2;
		cons.gridy = 1;
		this.add(end,cons);
		
		cons.gridx = 1;
		cons.gridy = 2;
		this.add(w,cons);
		
		cons.gridx = 2;
		cons.gridy = 2;
		this.add(weight,cons);
		
		cons.gridx = 1;
		cons.gridy = 3;
		cons.gridwidth = 2;
		this.add(link,cons);
		
		cons.insets = new Insets(0, 5, 5, 0);
		cons.gridx = 1;
		cons.gridy = 4;
		cons.gridwidth = 2;
		this.add(h,cons);
		
		cons.gridx = 1;
		cons.gridy = 5;
		cons.weighty = 1;
		this.add(jcp,cons);
		
		cons.gridx = 1;
		cons.gridy = 6;
		cons.weighty = 0;
		cons.gridwidth = 2;
		this.add(clear,cons);
		
		palette.addMouseListener(this);
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
			if(label_start > count-1 || label_end > count-1|| label_start < 0|| label_end < 0){
				history.append("ERROR:<---Beyond Arrange--->");
			}
			else{
				Line l = new Line(agents.ags.get(label_start).self,agents.ags.get(label_end).self);
				l.label_start = label_start;
				l.label_end = label_end;
				l.weight = w;
				edges.ls.add(l);
				String str = "A Link Added: StartAgent: Agent " + label_start +", EndAgent: Agent " + label_end +",Weight: " + w + " ;\n";
				history.append(str);
			}
			repaint();
		}
		if(e.getSource()==clear){
			history.setText("");
		}

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		count++;
		String str = "Agent" + count +" Added: x = " + x +",y = " +y + " ;\n";
		history.append(str);
		
		agents.ags.add(new Agent(e.getX(),e.getY(),count));
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
