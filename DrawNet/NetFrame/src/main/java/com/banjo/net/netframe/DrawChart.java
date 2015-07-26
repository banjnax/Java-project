package com.banjo.net.netframe;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class DrawChart {
	JPanel jchartp = new JPanel();
	ChartPanel chartp = null;
	public DrawChart(){
		jchartp.setLayout(new BorderLayout());
		test();
	}
	public void test(){
		//构造DataSet
        DefaultCategoryDataset DataSet = new DefaultCategoryDataset();
        DataSet.addValue(300, "number", "apple");
        DataSet.addValue(400, "number", "barara");
        DataSet.addValue(250, "number", "pear");
        DataSet.addValue(330, "number", "milk");
        DataSet.addValue(420, "number", "cheese");
        //创建柱形图
        JFreeChart chart = ChartFactory.createBarChart3D("Catogram",
                "Fruit", "Sale", DataSet, PlotOrientation.VERTICAL, 
                false, false, false);
       //用来放置图表
       chartp = new ChartPanel(chart);
       jchartp.add(chartp, BorderLayout.CENTER);
	}
}
