package com.banjo.net.BaseModules;

public class BaseMatrix {
	public int row;
	public int col;
	int[][] matrix;
	public BaseMatrix(int row,int col){
		this.row = row;
		this.col = col;
		this.matrix = new int[row][col];
	}
}
