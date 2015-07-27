package com.banjo.net.netframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import com.banjo.net.basemodules.Link;
import com.banjo.net.basemodules.Net;
import com.banjo.net.basemodules.Node;

public class DrawNetwork extends JFrame{
/**
 * @author banjo
 */
	private static final long serialVersionUID = 1L;
	private static int G_WIDTH = 800;
	private static int G_HEIGHT = 700;
	private static int G_X = 400;
	private static int G_Y = 100;
	
	public static boolean hasDiff = false;
	Image vImage=null;
	Image offScreenImage = null;
	Graphics gImage = null;
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu edit = new JMenu("Edit");
	JMenuItem _new = new JMenuItem("New");
	JMenuItem _open = new JMenuItem("Load Net");
	JMenuItem _loaddata = new JMenuItem("Load Data..");
	JMenuItem _save = new JMenuItem("Save Net");
	JMenuItem _savedata = new JMenuItem("Save Data..");
	JMenuItem _clear = new JMenuItem("Clear Palette");
	
	JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);//to clear the different faces to the users for different functions presentation
	
	JPanel hisPart = new JPanel();
	JLabel h = new JLabel("History:");
	static JTextPane his = new JTextPane();
	JScrollPane jcp = new JScrollPane(his);
	JButton clear = new JButton("Clear History");
	
	FileDialog openFileDialog = new FileDialog(this,"Open File",FileDialog.LOAD);
    FileDialog saveFileDialog = new FileDialog(this,"Save File As",FileDialog.SAVE);
    MenuAction ma = new MenuAction();
    int delay = 100;
    Timer timer = new Timer(delay, new TimerAction());//repaint the frame by a timer
    
    DrawGraph drawGraph = new DrawGraph();//have the graph handle
    DrawChart drawChart = new DrawChart(drawGraph);
    
    int paintFlag = 0;
	public static void main(String[] args) {
		new DrawNetwork().launch();
	}
	public void launch(){
		//outline of the frame
		this.setSize(G_WIDTH,G_HEIGHT);
		this.setLocation(G_X,G_Y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new BorderLayout());//new GridBagLayout()
		this.setResizable(false);
		this.setTitle("NetWork");
		
		setComponent();
		
		jtp.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane j = (JTabbedPane)e.getSource();
				if(j.getSelectedIndex() == 0){
					paintFlag = 0;
					repaint();
				}
				else paintFlag = 1;
			}
		});
		
		timer.start();
	}
	public void setComponent(){
		
		//menu part
		file.add(_new);
		file.add(_open);
		file.add(_loaddata);
		file.add(_save);
		file.add(_savedata);
		edit.add(_clear);
		_new.addActionListener(ma);
		_save.addActionListener(ma);
		_open.addActionListener(ma);
		_loaddata.addActionListener(ma);
		_savedata.addActionListener(ma);
		_clear.addActionListener(ma);
		
		menuBar.add(file);
		menuBar.add(edit);
		
		jtp.add(drawGraph.graph,"Graph");
		jtp.add(drawChart.jchartp,"Chart");
		
		//History part : show the operations
		
		//define some style about the history content
        Style def = his.getStyledDocument().addStyle(null, null);//this style define a normal style
        StyleConstants.setFontFamily(def, "verdana");
        StyleConstants.setFontSize(def, 10);
        Style normal = his.addStyle("normal", def);//name "def" to be normal
        
        Style s1 = his.addStyle("red", normal);//"red" style based on the "normal" style ,add color attribute to the "red" style
        StyleConstants.setForeground(s1, Color.RED);
       
        Style s2 = his.addStyle("blue", normal);
        StyleConstants.setForeground(s2, Color.BLUE);
        
		hisPart.setLayout(new GridBagLayout());
		
		h.setHorizontalAlignment(JLabel.LEFT);
        his.setParagraphAttributes(normal, true);
		jcp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jcp.setPreferredSize(new Dimension(100,100));
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				his.setText("");
			}
		});
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 0;
		cons.gridy = 0;
		hisPart.add(h,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 1;
		cons.gridwidth = 2;
		hisPart.add(jcp,cons);
		
		cons.gridx = 1;
		cons.gridy = 2;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		hisPart.add(clear,cons);
		
		this.add(menuBar,BorderLayout.NORTH);
		this.add(jtp,BorderLayout.CENTER);
		this.add(hisPart,BorderLayout.SOUTH);
	}
	public void paint(Graphics g){
		offScreenImage = this.createImage(G_WIDTH, G_HEIGHT);
		gImage = offScreenImage.getGraphics();
        gImage.setColor(gImage.getColor());
        gImage.fillRect(0, 0, G_WIDTH, G_HEIGHT); 
        super.paint(gImage);
//paint the things to be painted in different modules
        if(paintFlag == 0){
	        drawGraph.nodes.paint(gImage);//paint the component in this palette
	        drawGraph.links.paint(gImage);
        }
		
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}
	public static void printInfo(String info,String type){
		try {
			his.getDocument().insertString(his.getDocument().getLength(),
			         info, his.getStyle(type));//get the history handle and add content to it
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void printInfo(String info){//default "normal" style
		try {
			his.getDocument().insertString(his.getDocument().getLength(),
			         info, his.getStyle("normal"));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class TimerAction implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//compare the different part and repaint that
			if(hasDiff){
				repaint();
				hasDiff = false;
			}
		}
		
	}
	private class MenuAction implements ActionListener{//for the above menu include "file", "edit" and so on

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == _save){//file->save, to save as object
				saveFileDialog.setVisible(true);
				String fileName = saveFileDialog.getDirectory()+saveFileDialog.getFile();//open the file select dialog
				
		           if(fileName!=null){
		        	   try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
						if(drawGraph.net != null){
							oos.writeObject(drawGraph.net);
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
			else if(e.getSource() == _open){//open as object
				openFileDialog.setVisible(true);
				String fileName = openFileDialog.getDirectory()+openFileDialog.getFile();
				ObjectInputStream ois = null;
		           if(fileName!=null){
		        	   try {
						ois = new ObjectInputStream(new FileInputStream(fileName));
						try {
							drawGraph.net = (Net)ois.readObject();
							drawGraph.count = drawGraph.net.col;
							
							Iterator<Node> ita = drawGraph.net.nodes.iterator();
							Iterator<Link> itl = drawGraph.net.links.iterator();
							
							while(ita.hasNext()) drawGraph.nodes.ags.add(ita.next());
							while(itl.hasNext()) drawGraph.links.ls.add(itl.next());
							printInfo("Info:A net loaded!\n", "blue");
							//repaint();
							DrawNetwork.hasDiff = true;
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
			else if(e.getSource() == _loaddata){//load data that stored in numbers
				drawGraph.net = null;
				drawGraph.links.ls.removeAll(drawGraph.links.ls);
				drawGraph.nodes.ags.removeAll(drawGraph.nodes.ags);
				drawGraph.count = 0;
				//repaint();
				DrawNetwork.hasDiff = true;
				printInfo("Info:The palette is cleared!\n","blue");
				openFileDialog.setVisible(true);
				String fileName = openFileDialog.getDirectory()+openFileDialog.getFile();
				try {
		            Scanner in = new Scanner(new File(fileName));
	        		
	        		int width = drawGraph.palette.getWidth();
	        		int height = drawGraph.palette.getHeight();
	        		Random r =new Random();
	        		
		            while (in.hasNextLine()) {
		                String[] str = in.nextLine().trim().split(",");
		        		int start = Integer.parseInt(str[0]);
		        		int end = Integer.parseInt(str[1]);
		        		int weight = Integer.parseInt(str[2]);
		        		int direct = Integer.parseInt(str[3]);
		        		
		        		int e_x,e_y,s_x,s_y;
		        		Node n1,n2;
		        		Link l;
		        		
		        		if(start == end){
		        			e_x = 15+r.nextInt(width-30);
			        		e_y = 15+r.nextInt(height-50)+drawGraph.W_Y;
		        			n1 = new Node(e_x, e_y, start);
		        			drawGraph.nodes.ags.add(n1);
		        			drawGraph.count++;
		        		}
		        		else{
		        			
			        		if(!drawGraph.nodes.findNode(start)){
			        			s_x = 15+r.nextInt(width-30);
				        		s_y = 15+r.nextInt(height-50)+drawGraph.W_Y;
			        			n1 = new Node(s_x, s_y, start);
			        			drawGraph.nodes.ags.add(n1);
			        			drawGraph.count++;
			        		}
			        		if(!drawGraph.nodes.findNode(end)){
			        			e_x = 15+r.nextInt(width-30);
				        		e_y = 15+r.nextInt(height-50)+drawGraph.W_Y;
			        			n2 = new Node(e_x, e_y, end);
			        			drawGraph.nodes.ags.add(n2);
			        			drawGraph.count++;
			        		}
			        		
			        		l = new Link(drawGraph.nodes.getNode(start).self, drawGraph.nodes.getNode(end).self);
			        		l.label_start = start;
			        		l.label_end = end;
			        		l.weight = weight;
			        		if(direct == 1){
			        			l.directLink = true;
			        		}
			        		
			        		if(!drawGraph.links.findLink(start, end))
			        			drawGraph.links.ls.add(l);
		        		}
		            }
		            in.close();
		            printInfo("Info:Data loaded!\n","blue");
		           // repaint();
		           DrawNetwork.hasDiff = true;
		        } catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        }
				
			}
			else if(e.getSource() == _savedata){
	
				saveFileDialog.setVisible(true);
				String fileName = saveFileDialog.getDirectory()+saveFileDialog.getFile();//open the file select dialog
				
		           if(fileName!=null){
		        	   try {
						FileOutputStream fos = new FileOutputStream(fileName);
						ArrayList<Integer> savedNodes = new ArrayList<Integer>();
						String newline = System.getProperty("line.separator");
						if(drawGraph.nodes != null){
							for(int i=0;i<drawGraph.links.ls.size();i++){
								String s = "";
								Link l = drawGraph.links.ls.get(i);
								int start = l.label_start;
								int end = l.label_end;
								int w = l.weight;
								int type;
								if(l.directLink)
									type = 1;
								else type = 0;
								s = start+","+end+","+w+","+type+newline;
								fos.write(s.getBytes());
								if(!savedNodes.contains(start))
									savedNodes.add(start);
								if(!savedNodes.contains(end))
									savedNodes.add(end);
							}
							for(int i = 0;i<drawGraph.nodes.ags.size();i++){
								if(!savedNodes.contains(drawGraph.nodes.ags.get(i).number)){
									String s = drawGraph.nodes.ags.get(i).number+","+drawGraph.nodes.ags.get(i).number+","+0+","+0;
									fos.write(s.getBytes());
									if(i<drawGraph.nodes.ags.size()-1)
										fos.write(newline.getBytes());
								}
							}
							fos.flush();
							fos.close();
							printInfo( "Info:A net data saved!"+fileName+"\n","red");
						}
						else{
							printInfo( "Warning:Nothing saved!\n", "red");
							fos.close();
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
			else if(e.getSource() == _clear || e.getSource() == _new){//clear the history content
				drawGraph.net = null;
				drawGraph.links.ls.removeAll(drawGraph.links.ls);
				drawGraph.nodes.ags.removeAll(drawGraph.nodes.ags);
				drawGraph.count = 0;
				printInfo("Info:The palette is cleared!\n","blue");
				//repaint();
				DrawNetwork.hasDiff = true;
			}
			else{
				System.out.println("YOU CAN ADD ANTHER MENU HERE!!");
			}
		}
		
	}
}
