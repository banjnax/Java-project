package com.banjo.test;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;


public class Mouse_Test extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JButton b = null;
	JTextField tf = null;
	public static void main(String[] args){
		new Mouse_Test().launch();
	}
	public void launch(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(100, 100);
		this.setSize(300, 300);
		tf = new JTextField(10);
		tf.setHorizontalAlignment(JTextField.CENTER);
		b = new JButton("Test");
		b.addActionListener(this);
		b.setSize(70,40);
		b.setLocation(100,30);
		setVisible(true);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(tf,BorderLayout.WEST);
		p.add(b,BorderLayout.EAST);
		this.add(p);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b){
			System.out.println(tf.getText());
		}
	}
	
}
