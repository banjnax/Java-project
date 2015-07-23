package com.banjo.net.nettools;

import Jama.Matrix;

import com.banjo.net.basemodules.Net;

public class MatrixFactory {
	public static int ADJ_MATRIX = 1;
	public static int REA_MATRIX = 2;
	public static int COCITATION_MATRIX = 3;
	public static int COUPLE_MATRIX = 4;

	public Matrix getMatrix(Net net,int type){
		switch(type){
			case 1:return getAdj(net);
			case 2:return getRea(net);
			case 3:return getCocitation(net);
			case 4:return getCouple(net);
			default:return getRea(net);
		}
	}
	public Matrix getAdj(Net net){//adjacency matrix
		return net.adjMatrix.useMatrix;
	}
	public Matrix getRea(Net net){//reachable matrix
		return net.reaMatrix.useMatrix;
	}
	public Matrix getCocitation(Net net){//cocitation matrix
		Matrix m = net.reaMatrix.useMatrix;
		Matrix t_m = net.reaMatrix.useMatrix.transpose();
		return m.times(t_m);
	}
	public Matrix getCouple(Net net){//bibliographic coupling matrix
		Matrix m = net.reaMatrix.useMatrix;
		Matrix t_m = net.reaMatrix.useMatrix.transpose();
		return t_m.times(m);
	}
}
