import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Sudoku extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JTextField[][] tfs = new JTextField[9][9];
	int[][] tf_num = new int[9][9];
	JPanel p = new JPanel();
	JPanel pr = new JPanel();
	JPanel pru = new JPanel();
	JButton test = null;
	JRadioButton level1 = new JRadioButton("Easy");
	JRadioButton level2 = new JRadioButton("Middle");
	JRadioButton level3 = new JRadioButton("Hard");
	ArrayList<JTextField> invalidJT = new ArrayList<JTextField>();
	JTextField resArea = new JTextField();
	ButtonGroup bg = new ButtonGroup();//make sure that only one of button can be choosed
	int[][] easy = new int[][] {{1,0,0,8,3,0,0,0,2},
								{5,7,0,0,0,1,0,0,0},
								{0,0,0,5,0,9,0,6,4},
								{7,0,4,0,0,8,5,9,0},
								{0,0,3,0,1,0,4,0,0},
								{0,5,1,4,0,0,3,0,6},
								{3,6,0,7,0,4,0,0,0},
								{0,0,0,6,0,0,0,7,9},
								{8,0,0,0,5,2,0,0,3},
								};
	int[][] middle = new int[][]{{0,0,7,2,3,8,0,0,0},
								{0,6,0,7,0,0,0,5,0},
								{0,0,0,4,0,0,0,0,2},
								{9,0,0,0,0,0,8,6,7},
								{1,0,0,0,0,0,0,0,3},
								{6,4,8,0,0,0,0,0,5},
								{7,0,0,0,0,3,0,0,0},
								{0,2,0,0,0,5,0,3,0},
								{0,0,0,2,7,4,9,0,0},
								};
	int[][] hard = new int[][]{{0,0,9,0,0,0,0,0,2},
							{0,0,0,3,1,0,8,0,0},
							{2,0,8,6,0,0,0,0,0},
							{0,5,0,0,2,0,0,0,1},
							{0,0,0,4,0,8,0,0,0},
							{6,0,0,0,9,0,0,4,0},
							{0,0,7,0,8,5,0,0,0},
							{1,0,0,0,0,0,2,0,0},
							{8,0,0,0,5,2,0,0,3},
							};
	SBlock SBs = null;
	public boolean res = false;
	public static void main(String[] args){
		new Sudoku();
	}
	public Sudoku(){
		this.setLocation(200, 200);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		p.setLayout(new GridLayout(9,9));
		pr.setLayout(new BorderLayout());
		pru.setLayout(new GridLayout(3,1));
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				JTextField tf =  new JTextField(2);
				tf.setHorizontalAlignment(JTextField.CENTER);
				this.tfs[i][j]= tf;
				p.add(tf);
				tf_num[i][j]=0;
			}
		}
		SBs = new SBlock(this);
		SBs.paintBlock();
		this.add(p,BorderLayout.CENTER);
		this.setVisible(true);
		test = new JButton("Test");
		resArea.setBackground(Color.RED);
		pr.add(test,BorderLayout.CENTER);
		pr.add(resArea,BorderLayout.SOUTH);
		pru.add(level1);
		pru.add(level2);
		pru.add(level3);
		pr.add(pru,BorderLayout.NORTH);
		this.add(pr,BorderLayout.EAST);
		bg.add(level1);
		bg.add(level2);
		bg.add(level3);
		
		test.addActionListener(this);
		level1.addActionListener(this);
		level2.addActionListener(this);
		level3.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==test){
			getNum();
			SBs.splitSudoku();

			printNum();
			printSBs();
			findInvalidJT();
			//printInvalid();
			if(invalidJT.size()==0){
				resArea.setBackground(Color.GREEN);
			}
			else{
				resArea.setBackground(Color.RED);
				markJT(invalidJT);
			}
		//	boolean res = verifyCheck();
			//System.out.println("Result:"+res);
		}
		if(e.getSource()==level1){
			System.out.println("Easy Level!");
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++){
					this.tfs[i][j].setText("");
					if(this.easy[i][j]!=0)
						this.tfs[i][j].setText(this.easy[i][j]+"");
				}
		}
		if(e.getSource()==level2){
			System.out.println("Middle Level!");
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++){
					this.tfs[i][j].setText("");
					if(this.middle[i][j]!=0)
						this.tfs[i][j].setText(this.middle[i][j]+"");
				}
		}
		if(e.getSource()==level3){
			System.out.println("hard Level!");
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++){
					this.tfs[i][j].setText("");
					if(this.hard[i][j]!=0)
						this.tfs[i][j].setText(this.hard[i][j]+"");
				}
		}
	}/*
	public ArrayList<JTextField> findInvalidJT(){
		//For NUll value
		int i=0,j=0,k=0,l=0,flag=0;;
		for(i=0;i<9;i++){
			for(j=0;j<9;j++){
				if(this.tf_num[i][j]==0)
					invalidJT.add(tfs[i][j]);
			}
		}
		//For duplicate number
		//For H
		for(i=0;i<9;i++){
			int[] temM = new int[9];
			int[] visited = new int[]{0,0,0,0,0,0,0,0,0};
			for(j=0;j<9;j++){
				flag=0;
				temM[flag++]=j;
				visited[j]=1;
				for(k=0;k<9;k++){
					if(tf_num[i][j]!=tf_num[i][k]||visited[k]==1) continue;
					else{
						visited[k]=1;
						temM[flag++]=j;
					}
				}
				if(flag>1){
					for(l=0;l<flag;l++) {
						if(!invalidJT.contains(tfs[i][temM[l]]))invalidJT.add(tfs[i][temM[l]]);
					}
				}
			}
		}
		//For V
		for(i=0;i<9;i++){
			int[] temM = new int[9];
			int[] visited = new int[]{0,0,0,0,0,0,0,0,0};
			for(j=0;j<9;j++){
				flag=0;
				temM[flag++]=j;
				visited[j]=1;
				for(k=0;k<9;k++){
					if(tf_num[j][i]!=tf_num[k][i]||visited[k]==1) continue;
					else{
						visited[k]=1;
						temM[flag++]=j;
					}
				}
				if(flag>1){
					for(l=0;l<flag;l++) {
						if(!invalidJT.contains(tfs[temM[l]][i]))invalidJT.add(tfs[temM[l]][i]);
					}
				}
			}
			//For C...
		}
		return invalidJT;
	}
	*/
	public void findNullValue(){
		int i=0,j=0;
		for(i=0;i<9;i++){
			for(j=0;j<9;j++){
				if(this.tf_num[i][j]==0)
					invalidJT.add(tfs[i][j]);
			}
		}
	}
	public ArrayList<JTextField> findInvalidJT(){
		removeMark();
		findNullValue();
		findDupicate();
		return invalidJT;
	}
	public void removeMark(){
		Iterator<JTextField> it = invalidJT.iterator();
		while(it.hasNext()) it.next().setBackground(Color.WHITE);
		invalidJT.removeAll(invalidJT);
	}
	public void findDupicate(){
		//findDupicate_H(SBs.htfs);
		//findDupicate_V(SBs.vtfs);
		findDupicate_C(SBs.ctfs);
	}
	public void findDupicate_H(int[][] p_sb){
		int i=0,j=0,k=0,l=0,flag=0;
		for(i=0;i<9;i++){
			int[] temM = new int[9];
			int[] visited = new int[]{0,0,0,0,0,0,0,0,0};
			for(j=0;j<9;j++){
				flag=0;
				temM[flag++]=j;
				if(visited[j]==1) continue;
				else{
					visited[j]=1;
					for(k=0;k<9;k++){
						if(p_sb[i][j]!=p_sb[i][k]||visited[k]==1) continue;
						else{
							visited[k]=1;
							temM[flag++]=j;
						}
					}
				}
				if(flag>1){
					for(l=0;l<flag;l++) {
						if(!invalidJT.contains(tfs[i][temM[l]])) invalidJT.add(tfs[i][temM[l]]);
					}
				}
			}
		}
	}
	public void findDupicate_V(int[][] p_sb){
		int i=0,j=0,k=0,l=0,flag=0;
		for(i=0;i<9;i++){
			int[] temM = new int[9];
			int[] visited = new int[]{0,0,0,0,0,0,0,0,0};
			for(j=0;j<9;j++){
				flag=0;
				temM[flag++]=j;
				if(visited[j]==1) continue;
				else{
					visited[j]=1;
					for(k=0;k<9;k++){
						if(p_sb[i][j]!=p_sb[i][k]||visited[k]==1) continue;
						else{
							visited[k]=1;
							temM[flag++]=j;
						}
					}
				}
				if(flag>1){
					for(l=0;l<flag;l++) {
						if(!invalidJT.contains(tfs[temM[l]][i])) invalidJT.add(tfs[temM[l]][i]);
					}
				}
			}
		}
	}
	public void findDupicate_C(int[][] p_sb){
		int i=0,j=0,k=0,l=0,flag=0;
		for(i=0;i<9;i++){
			int[] temM = new int[9];
			int[] visited = new int[]{0,0,0,0,0,0,0,0,0};
			for(j=0;j<9;j++){
				flag=0;
				temM[flag++]=j;
				if(visited[j]==1) continue;
				else{
					visited[j]=1;
					for(k=0;k<9;k++){
						if(p_sb[i][j]!=p_sb[i][k]||visited[k]==1) continue;
						else{
							visited[k]=1;
							temM[flag++]=j;
						}
					}
				}
				if(flag>1){
					for(l=0;l<flag;l++) {
						int x=3*(i/3)+temM[l]/3;
						int y=3*(i%3)+temM[l]%3;
						if(!invalidJT.contains(tfs[x][y])) {
							invalidJT.add(tfs[x][y]);
							System.out.println("TEST:::::::::"+Integer.parseInt(tfs[x][y].getText()));
						}
					}
				}
			}
		}
	}
	public void markJT(ArrayList<JTextField> invalid){
		Iterator<JTextField> it = invalid.iterator();
		while(it.hasNext()) it.next().setBackground(Color.RED);
	}
	public void getNum(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(!tfs[i][j].getText().trim().equals(""))
					tf_num[i][j]=Integer.parseInt(tfs[i][j].getText());
				else
					tf_num[i][j]=0;
			}
		}
	}
	public void printNum(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(tf_num[i][j]+" ");
			}
			System.out.println();
		}
	}
	public void printSBs(){
		//SBs.printH();
		SBs.printV();
		//SBs.printC();
	}
	public void printInvalid(){
		Iterator<JTextField> it = this.invalidJT.iterator();
		while(it.hasNext()){
			JTextField tf = it.next();
			if(tf.getText().trim().equals(""))
			System.out.print(" "+ 0);
			else
				System.out.print(" "+Integer.parseInt(tf.getText()));
		}
		System.out.println();
	}
	
	/*
	public boolean verifyCheck(){
		boolean h_r = verifyB(SBs.htfs);
		boolean v_r = verifyB(SBs.vtfs);
		boolean c_r = verifyB(SBs.ctfs);
		return h_r&v_r&c_r;
	}
	public boolean verifyB(int [][] bs){
		boolean res=true;
		for(int i=1;i<10;i++){
			for(int j=0;j<9;j++){
				boolean flag = false;
				for(int k=0;k<9;k++){
					if(i==bs[j][k]) flag=true;
				}
				if(!flag) return false;
			}
		}
		return res;
	}
	*/
	
}
