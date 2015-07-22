package com.banjo.net.BaseModules;

import Jama.*;

public class BaseMatrix {
	public int row;
	public int col;
	double[][] matrix;
	public Matrix useMatrix;
	public BaseMatrix(int row,int col){
		this.row = row;
		this.col = col;
		this.matrix = new double[row][col];
		this.useMatrix = new Matrix(matrix);
	}
}
