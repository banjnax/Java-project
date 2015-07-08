import java.awt.Color;
import java.awt.Graphics;


public class Foot {
	Point start;
	Point end;
	static int length = 70;
	public Foot(Point fEnd,Point lEnd){
		start = new Point(lEnd.x,lEnd.y);
		this.end = new Point(fEnd.x,fEnd.y); 
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.green);
		g.drawOval(start.x-5, start.y-5, 10, 10);
		g.drawLine(start.x, start.y, end.x, end.y);
		g.drawOval(end.x-5, end.y-5, 10, 10);
		g.setColor(c);
	}
	public void updatePoints(Point lEnd){
		start.x = lEnd.x;
		start.y = lEnd.y;
	}
}
