package com.banjo.net.NetFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import com.banjo.net.BaseModules.*;
import com.banjo.net.NetTools.MatrixFactory;



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
	Net net = null;
	MatrixFactory mf = new MatrixFactory();
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu edit = new JMenu("Edit");
	JMenuItem _new = new JMenuItem("new");
	JMenuItem _save = new JMenuItem("save");
	
	JTextField start = new JTextField(2);
	JTextField end = new JTextField(2);
	JTextField weight = new JTextField(2);
	JPanel palette = new JPanel();
	JPanel blank = new JPanel();
	JTextPane his = new JTextPane();
	JButton clear = new JButton("Clear History");
	
	JLabel s = new JLabel("Start point:");
	JLabel e = new JLabel("End point:");
	JLabel w = new JLabel("Weight:");
	JLabel h = new JLabel("History:");
	JLabel f = new JLabel("Functions:");
	JButton link = new JButton("Link");
	JRadioButton undirect = new JRadioButton("Undirect");
	JRadioButton direct = new JRadioButton("Direct");
	ButtonGroup type = new ButtonGroup();
	JButton netDone = new JButton("NetDone!");
	JComboBox<String> functions = new JComboBox<String>();
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
		this.setTitle("NetWork");
		
		file.add(_new);
		file.add(_save);
		menuBar.add(file);
		menuBar.add(edit);
		
        Style def = his.getStyledDocument().addStyle(null, null);//this style define a normal style
        StyleConstants.setFontFamily(def, "verdana");
        StyleConstants.setFontSize(def, 10);
        Style normal = his.addStyle("normal", def);//name "def" to be normal
        
        Style s1 = his.addStyle("red", normal);//"red" style based on the "normal" style ,add color attribute to the "red" style
        StyleConstants.setForeground(s1, Color.RED);
       
        Style s2 = his.addStyle("blue", normal);
        StyleConstants.setForeground(s2, Color.BLUE);
        
		palette.setBackground(Color.white);
        type.add(undirect);
        type.add(direct);
        undirect.setSelected(true);
		functions.addItem("AdjMatrix");
		functions.addItem("ReaMatrix");
		functions.addItem("CocitMatrix");
		functions.addItem("CoupMatrix");
		functions.addActionListener(this);
        his.setParagraphAttributes(normal, true);


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
		
		cons.gridx = 0;//menubar
		cons.gridy = 0;
		cons.weightx = 1;
		cons.weighty = 0;
		cons.gridwidth = 3;
		this.add(menuBar,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.gridwidth = 1;
		cons.gridheight = 10;
		this.add(palette,cons);
		
		//Add the operation control panel
		
		//Add start,end,weight
		cons.insets = new Insets(10, 10, 0, 0);
		cons.gridx = 1;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		this.add(s,cons);
		
		cons.gridx = 2;
		cons.gridy = 1;
		this.add(start,cons);
		
		cons.gridx = 1;
		cons.gridy = 2;
		this.add(e,cons);
		
		cons.gridx = 2;
		cons.gridy = 2;
		this.add(end,cons);
		
		cons.gridx = 1;
		cons.gridy = 3;
		this.add(w,cons);
		
		cons.gridx = 2;
		cons.gridy = 3;
		this.add(weight,cons);
		

		cons.gridx = 1;
		cons.gridy = 4;
		cons.gridwidth = 2;
		this.add(link,cons);
		
		cons.gridx = 1;
		cons.gridy = 5;
		cons.gridwidth = 2;
		this.add(undirect,cons);
		
		cons.gridx = 1;
		cons.gridy = 6;
		cons.gridwidth = 2;
		this.add(direct,cons);
		
		cons.gridx = 1;//NetDone
		cons.gridy = 7;
		this.add(netDone,cons);
		
		cons.gridx = 1;//Functions
		cons.gridy = 8;
		cons.gridwidth = 2;
		this.add(f,cons);
		
		cons.gridx = 1;//Functions list
		cons.gridy = 9;
		cons.gridwidth = 2;
		cons.weightx = 0;
		this.add(functions,cons);
		
		cons.gridx = 1;
		cons.gridy = 10;
		cons.gridwidth = 2;
		cons.weightx = 0;
		this.add(blank,cons);
		

		cons.insets = new Insets(0, 5, 5, 0);
		cons.gridwidth = 1;
		cons.gridx = 0;
		cons.gridy = 11;
		this.add(h,cons);
		
		cons.gridx = 0;
		cons.gridy = 12;
		cons.gridwidth=3;
		cons.weighty = 0;
		this.add(jcp,cons);
		
		cons.gridx = 1;
		cons.gridy = 13;
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
				if(direct.isSelected()){
					l.directLink = true;
				}
				links.ls.add(l);
				String str = "A Link Added: StartAgent: Agent " + (label_start+1) +", EndAgent: Agent " + (label_end+1) +",Weight: " + w + " ;\n";
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
			if(undirect.isSelected()){
				net = new UndirectedNet("MyNet", nodes.ags, links.ls,Net.UNDIRECT_NETWORK);
			}
			else{
				net = new DirectedNet("MyNet", nodes.ags, links.ls,Net.DIRECT_NETWORK);
			}
			 try {
					his.getDocument().insertString(his.getDocument().getLength(),
					          "A Net has builded!\n"+net.toString()+"\n"+net.print(), his.getStyle("normal"));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		if(e.getSource() == functions){
			if(net!=null){
				String s = "";
			switch(functions.getSelectedIndex()){
				case 0:s = "Adjacency Matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.ADJ_MATRIX));break;
				case 1:s = "Reachable matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.REA_MATRIX));break;
				case 2:s = "Cocitation Matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.COCITATION_MATRIX));break;
				case 3:s = "Bibliographic Coupling Matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.COUPLE_MATRIX));break;
			}
			try {
				his.getDocument().insertString(his.getDocument().getLength(),
				         s, his.getStyle("blue"));
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		int y = e.getY()+45;
		count++;
		String str = "Agent " + count +" added: x = " + x +",y = " +y + " ;\n";
		 try {
			his.getDocument().insertString(his.getDocument().getLength(),
			       str, his.getStyle("normal"));
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		nodes.ags.add(new Node(x,y,count));
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
