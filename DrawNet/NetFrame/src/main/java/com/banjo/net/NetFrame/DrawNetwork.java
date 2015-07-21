package com.banjo.net.NetFrame;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import com.banjo.net.BaseModules.*;



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
	
	Nodes nodes = new Nodes();
	Links links = new Links();
	Net net;
	
	JTextField start = new JTextField(2);
	JTextField end = new JTextField(2);
	JTextField weight = new JTextField(2);
	JPanel palette = new JPanel();
	JTextPane his = new JTextPane();
	JButton clear = new JButton("Clear History");
	
	JLabel s = new JLabel("Start point:");
	JLabel e = new JLabel("End point:");
	JLabel w = new JLabel("Weight:");
	JLabel h = new JLabel("History:");
	JButton link = new JButton("Link");
	JButton netDone = new JButton("NetDone!");
	JScrollPane jcp = new JScrollPane(his);
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
		
        Style def = his.getStyledDocument().addStyle(null, null);//this style define a normal style
        StyleConstants.setFontFamily(def, "verdana");
        StyleConstants.setFontSize(def, 10);
        Style normal = his.addStyle("normal", def);//name "def" to be normal
        
        Style s1 = his.addStyle("red", normal);//"red" style based on the "normal" style ,add color attribute to the "red" style
        StyleConstants.setForeground(s1, Color.RED);
       
        Style s2 = his.addStyle("green", normal);
        StyleConstants.setForeground(s2, Color.GREEN);
        his.setParagraphAttributes(normal, true);
		
		
		palette.setBackground(Color.white);

		e.setHorizontalAlignment(JLabel.RIGHT);
		s.setHorizontalAlignment(JLabel.RIGHT);
		w.setHorizontalAlignment(JLabel.RIGHT);
		h.setHorizontalAlignment(JLabel.LEFT);
		
		start.setHorizontalAlignment(JTextField.CENTER);
		end.setHorizontalAlignment(JTextField.CENTER);
		weight.setHorizontalAlignment(JTextField.CENTER);
		jcp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		his.setPreferredSize(new Dimension(100,100));
		
		
		palette.addMouseListener(this);
		link.addActionListener(this);
		netDone.addActionListener(this);
		clear.addActionListener(this);
		
		GridBagConstraints cons = new GridBagConstraints();
		//Add the palette
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.gridwidth = 1;
		cons.gridheight = 8;
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
		
		cons.gridx = 1;
		cons.gridy = 4;
		this.add(netDone,cons);
		
		cons.insets = new Insets(0, 5, 5, 0);
		cons.gridx = 1;
		cons.gridy = 5;
		cons.gridwidth = 2;
		this.add(h,cons);
		
		cons.gridx = 1;
		cons.gridy = 6;
		cons.weighty = 1;
		this.add(jcp,cons);
		
		cons.gridx = 1;
		cons.gridy = 7;
		cons.weighty = 0;
		cons.gridwidth = 2;
		this.add(clear,cons);
	}
	public void paint(Graphics g){
		offScreenImage = this.createImage(G_WIDTH, G_HEIGHT);
		gImage = offScreenImage.getGraphics();
        gImage.setColor(gImage.getColor());
        gImage.fillRect(0, 0, G_WIDTH, G_HEIGHT); 
        super.paint(gImage);
		
		this.nodes.paint(gImage);
		this.links.paint(gImage);
		
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
				 try {
					his.getDocument().insertString(his.getDocument().getLength(),
					          "ERROR:<---Beyond Arrange--->\n", his.getStyle("red"));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				Link l = new Link(nodes.ags.get(label_start).self,nodes.ags.get(label_end).self);
				l.label_start = label_start;
				l.label_end = label_end;
				l.weight = w;
				links.ls.add(l);
				String str = "A Link Added: StartAgent: Agent " + label_start +", EndAgent: Agent " + label_end +",Weight: " + w + " ;\n";
				 try {
					his.getDocument().insertString(his.getDocument().getLength(),
					          str, his.getStyle("normal"));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			repaint();
		}
		if(e.getSource() == netDone){
			net = new UndirectedNet("MyNet", nodes.ags, links.ls);
			 try {
					his.getDocument().insertString(his.getDocument().getLength(),
					          "A Net has builded!\n"+net.print(), his.getStyle("green"));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		if(e.getSource()==clear){
			his.setText("");
		}

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		count++;
		String str = "Agent " + count +" added: x = " + x +",y = " +y + " ;\n";
		 try {
			his.getDocument().insertString(his.getDocument().getLength(),
			       str, his.getStyle("normal"));
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		nodes.ags.add(new Node(e.getX(),e.getY(),count));
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
