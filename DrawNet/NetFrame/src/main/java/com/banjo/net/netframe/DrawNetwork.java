package com.banjo.net.netframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

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
import javax.xml.bind.ValidationException;

import com.banjo.net.basemodules.DirectedNet;
import com.banjo.net.basemodules.Link;
import com.banjo.net.basemodules.Links;
import com.banjo.net.basemodules.Net;
import com.banjo.net.basemodules.Node;
import com.banjo.net.basemodules.Nodes;
import com.banjo.net.basemodules.UndirectedNet;
import com.banjo.net.nettools.MatrixFactory;

public class DrawNetwork extends JFrame{

	private static final long serialVersionUID = 1L;
	private static int G_WIDTH = 800;
	private static int G_HEIGHT = 800;
	private static int G_X = 400;
	private static int G_Y = 100;
	
	Image vImage=null;
	Image offScreenImage = null;
	Graphics gImage = null;
	public int count=0;
	
	Nodes nodes = new Nodes();
	Links links = new Links();
	Net net = null;
	MatrixFactory mf = new MatrixFactory();
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu edit = new JMenu("Edit");
	JMenuItem _new = new JMenuItem("New");
	JMenuItem _open = new JMenuItem("Open");
	JMenuItem _save = new JMenuItem("Save");
	JMenuItem _loaddata = new JMenuItem("Load Data..");
	JMenuItem _clear = new JMenuItem("Clear Palette");
	
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
	JLabel f = new JLabel("Matrixes:");
	JButton link = new JButton("Link");
	JButton unlink = new JButton("Unlink");
	
	JRadioButton undirect = new JRadioButton("Undirect");
	JRadioButton direct = new JRadioButton("Direct");
	ButtonGroup type = new ButtonGroup();
	JButton netDone = new JButton("NetDone!");
	JComboBox<String> functions = new JComboBox<String>();
	JScrollPane jcp = new JScrollPane(his);
	FileDialog openFileDialog = new FileDialog(this,"Open File",FileDialog.LOAD);
    FileDialog saveFileDialog = new FileDialog(this,"Save File As",FileDialog.SAVE);
    
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
		file.add(_open);
		file.add(_loaddata);
		file.add(_save);
		edit.add(_clear);
		_save.addActionListener(new MenuAction());
		_open.addActionListener(new MenuAction());
		_loaddata.addActionListener(new MenuAction());
		_clear.addActionListener(new MenuAction());
		
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
		functions.addActionListener(new ListMenuAction());
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
		
		palette.addMouseListener(new MouseAction());
		palette.addMouseMotionListener(new MouseAction());
		link.addActionListener(new ButtonAction());
		unlink.addActionListener(new ButtonAction());
		netDone.addActionListener(new ButtonAction());
		clear.addActionListener(new ButtonAction());
		
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
		cons.gridwidth = 1;
		this.add(link,cons);
		
		cons.insets = new Insets(10, 0, 0, 0);
		cons.gridx = 2;
		cons.gridy = 4;
		cons.gridwidth = 1;
		this.add(unlink,cons);
		cons.insets = new Insets(10, 10, 0, 0);
		
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
		
		cons.insets = new Insets(5, 0, 0, 0);
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

		nodes.paint(gImage);
		links.paint(gImage);
		
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}
	public void printInfo(String info,String type){
		try {
			his.getDocument().insertString(his.getDocument().getLength(),
			         info, his.getStyle(type));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printInfo(String info){
		try {
			his.getDocument().insertString(his.getDocument().getLength(),
			         info, his.getStyle("normal"));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==link){
				int label_start = Integer.parseInt(start.getText());
				int label_end = Integer.parseInt(end.getText());
				int w = Integer.parseInt(weight.getText());
				if(w==0){
					try {
						throw new ValidationException("ERROR:<---Weight:Value valid--->\n");
					} catch (ValidationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(label_start > count || label_end > count|| label_start < 1|| label_end < 1) printInfo( "ERROR:<---Beyond Arrange--->\n", "red");
				
				else {
					Link l = new Link(nodes.getNode(label_start).self,nodes.getNode(label_end).self);
					l.label_start = label_start;
					l.label_end = label_end;
					l.weight = w;
					if(direct.isSelected()){
						l.directLink = true;
					}
						links.ls.add(l);
					String str = "A Link Added: StartAgent: Agent " + label_start +", EndAgent: Agent " + label_end +",Weight: " + w + " ;\n";
					printInfo(str);
				}
				repaint();
			}
			else if(e.getSource() == unlink){
				boolean flag = false;
				int label_start = Integer.parseInt(start.getText());
				int label_end = Integer.parseInt(end.getText());
				if(label_start > count || label_end > count|| label_start < 1|| label_end < 1) printInfo("ERROR:<---Beyond Arrange--->\n","red");
				else{
					for(int i = 0;i<links.ls.size();i++){
						Link l = links.ls.get(i);
						if(l.label_start==label_start && l.label_end==label_end){
							links.ls.remove(i);
							flag = true;
							break;
						}
					}
					if(!flag) printInfo( "Info:No edge to be removed(No such edge in the net)!\n","red");
					else {
						repaint();
						printInfo( "Info:Unlink an edge!\n","blue");
					}
				}
			}
			else if(e.getSource() == netDone){
					if(undirect.isSelected()){
						net = new UndirectedNet("MyNet", nodes.ags, links.ls,Net.UNDIRECT_NETWORK);
					}
					else{
						net = new DirectedNet("MyNet", nodes.ags, links.ls,Net.DIRECT_NETWORK);
					}
					printInfo("A Net has builded!\n"+net.toString()+"\n"+net.print());
			}
			else if(e.getSource()==clear){
				his.setText("");
			}
			else{
				System.out.println("--------------");
			}
			
		}
		
	}
	
	private class ListMenuAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == functions){
				if(net!=null){
					String s = "";
				switch(functions.getSelectedIndex()){
					case 0:s = "Adjacency Matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.ADJ_MATRIX));break;
					case 1:s = "Reachable matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.REA_MATRIX));break;
					case 2:s = "Cocitation Matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.COCITATION_MATRIX));break;
					case 3:s = "Bibliographic Coupling Matrix:\n" + net.printMatrix(mf.getMatrix(net, MatrixFactory.COUPLE_MATRIX));break;
				}
				repaint();
				printInfo(s,"blue");
			}
			}
		}
		
	}
	
	private class MenuAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == _save){
				saveFileDialog.setVisible(true);
				String fileName = saveFileDialog.getDirectory()+saveFileDialog.getFile();
				
		           if(fileName!=null){
		        	   try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
						if(net != null){
							oos.writeObject(net);
							oos.flush();
							oos.close();
							printInfo( "Info:A net saved!"+fileName+".txt\n","red");
						}
						else{
							printInfo( "Warning:Nothing saved!\n", "red");
							oos.close();
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		                  
		          }
		           
			}
			else if(e.getSource() == _open){
				openFileDialog.setVisible(true);
				String fileName = openFileDialog.getDirectory()+openFileDialog.getFile();
				ObjectInputStream ois = null;
		           if(fileName!=null){
		        	   try {
						ois = new ObjectInputStream(new FileInputStream(fileName));
						try {
							net = (Net)ois.readObject();
							count = net.col;
							
							Iterator<Node> ita = net.nodes.iterator();
							Iterator<Link> itl = net.links.iterator();
							
							while(ita.hasNext()) nodes.ags.add(ita.next());
							while(itl.hasNext()) links.ls.add(itl.next());
							printInfo("Info:A net loaded!\n", "blue");
							repaint();
							
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							printInfo("Error:Not object file!\n", "red");
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	   finally{
		        		  if(ois != null)
							try {
								ois.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	   }
		                  
		          }
			}
			else if(e.getSource() == _loaddata){
				net = null;
				links.ls.removeAll(links.ls);
				nodes.ags.removeAll(nodes.ags);
				count = 0;
				repaint();
				printInfo("Info:The palette is cleared!\n","blue");
				openFileDialog.setVisible(true);
				String fileName = openFileDialog.getDirectory()+openFileDialog.getFile();
				try {
		            Scanner in = new Scanner(new File(fileName));
	        		
	        		int width = palette.getWidth();
	        		int height = palette.getHeight();
	        		Random r =new Random();
	        		
		            while (in.hasNextLine()) {
		                String[] str = in.nextLine().trim().split(",");
		        		int start = Integer.parseInt(str[0]);
		        		int end = Integer.parseInt(str[1]);
		        		int weight = Integer.parseInt(str[2]); 
		        		
		        		if(!nodes.findNode(start)){
		        			int s_x = 15+r.nextInt(width-30);
			        		int s_y = 15+r.nextInt(height-50);
		        			Node n1 = new Node(s_x, s_y, start);
			        		nodes.ags.add(n1);
			        		count++;
		        		}
		        		if(!nodes.findNode(end)){
		        			int e_x = 15+r.nextInt(width-30);
			        		int e_y = 15+r.nextInt(height-50);
		        			Node n2 = new Node(e_x, e_y, end);
			        		nodes.ags.add(n2);
			        		count++;
		        		}
		        		
		        		Link l = new Link(nodes.getNode(start).self, nodes.getNode(end).self);
		        		l.label_start = start;
		        		l.label_end = end;
		        		l.weight = weight;
		        		
		        		if(!links.findLink(start, end))
		        			links.ls.add(l);
		            }
		            in.close();
		            printInfo("Info:Data loaded!\n","blue");
		            repaint();
		        } catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        }
				
			}
			else if(e.getSource() == _clear){
				net = null;
				links.ls.removeAll(links.ls);
				nodes.ags.removeAll(nodes.ags);
				count = 0;
				printInfo("Info:The palette is cleared!\n","blue");
				repaint();
			}
			else{
				System.out.println("YOU CAN ADD ANTHER MENU HERE!!");
			}
		}
		
	}
	
	private class MouseAction implements MouseListener,MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		
			}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
			int y = e.getY()+45;
			
			if(nodes.getNode(x, y)==null){
				count++;
				String str = "Agent " + count +" added: x = " + x +",y = " +y + " ;\n";
				printInfo(str);
				
				nodes.ags.add(new Node(x,y,count));
			}
			else{
				Node n = nodes.getNode(x, y);
				n.self_color = Color.orange;
			}
			repaint();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
			int y = e.getY()+45;
			if(nodes.getNode(x, y)!=null){
				nodes.getNode(x, y).self_color = Color.green;
			}
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
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
			int y = e.getY()+45;
			// TODO Auto-generated method stub
			if(nodes.getNode(x, y)!=null){
				Node n = nodes.getNode(x, y);
				n.self.x = x;
				n.self.y = y;
				for(int i=0;i<links.ls.size();i++){
					if(links.ls.get(i).label_start == n.number){
						links.ls.get(i).start.x = x;
						links.ls.get(i).start.y = y;
					}
					if(links.ls.get(i).label_end == n.number){
						links.ls.get(i).end.x = x;
						links.ls.get(i).end.y = y;
					}
				}
				repaint();
			}
			
		}
		@Override
		public void mouseMoved(MouseEvent e) {
		
		}
		
	}

}
