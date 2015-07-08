public class SBlock {
	Sudoku sd = null;
	int[][] htfs = null;
	int[][] vtfs = null;
	int[][] ctfs = null;
	public SBlock(Sudoku su){
		this.sd = su;
		splitSudoku();
	}
	public void  splitSudoku(){
		htfs = spHor();
		vtfs = spVer();
		ctfs = spCro();
	}
	public int[][] spHor(){
		int[][] HJT =new int[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				HJT[i][j] =this.sd.tf_num[i][j];
			}
		}
		return HJT;
	}
	public int[][] spVer(){
		int[][] HJT =new int[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				HJT[i][j]=this.sd.tf_num[j][i];
			}
		}
		return HJT;
	}
	public int[][] spCro(){
		int[][] HJT =new int[9][9];
		int k=0,l=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				l=0;
				for(int x=3*i;x<3*i+3;x++){
					for(int y=3*j;y<3*j+3;y++){
						HJT[k][l++] = this.sd.tf_num[x][y];//还原公式为：x=x0+j/3,y=y0+j%3,x0=3*(i/3),y0=3*i%3
					}
				}
				k++;
			}
		}
		return HJT;
	}
	public void printH(){
		for(int i=0;i<9;i++){
			System.out.print("第"+i+"行：");
			for(int j=0;j<9;j++){
				System.out.print(htfs[i][j]);
			}
			System.out.println();
		}
	}
	public void printV(){
		for(int i=0;i<9;i++){
			System.out.print("第"+i+"列：");
			for(int j=0;j<9;j++){
				System.out.print(vtfs[i][j]);
			}
			System.out.println();
		}
	}
	public void printC(){
		for(int i=0;i<9;i++){
			System.out.print("第"+i+"块：");
			for(int j=0;j<9;j++){
				System.out.print(ctfs[i][j]);
			}
			System.out.println();
		}
	}
	public void paintBlock(){
		
	}
}
