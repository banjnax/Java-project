import java.awt.Color;
import java.awt.Graphics;

public class Leg {
	Point start;
	Point end;
	static int length = 150;
	Foot foot = null;
	public Leg(Point hStart,Point hEnd){
		start = new Point(hEnd.x,hEnd.y);
		end = new Point(hStart.x,hStart.y+Ham.length+Leg.length);
		foot = new Foot(new Point(hStart.x-Foot.length,start.y+Leg.length),end);
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		foot.updatePoints(end);
		g.setColor(Color.red);
		g.drawOval(start.x-5, start.y-5, 10, 10);
		g.drawLine(start.x, start.y, end.x, end.y);
		g.drawOval(end.x-5, end.y-5, 10, 10);
		foot.paint(g);
		g.setColor(c);
	}
	public void updatePoints(Point hEnd){
		start = hEnd;
		calEnd();
		foot.updatePoints(end);
	}
	public void calEnd(){//根据leg与foot的长度不变并且foot的end点不变来列方程求得
		double a1 = -2*start.x;
		double b1 = -2*start.y;
		double a2 = -2*foot.end.x;
		double b2 = -2*foot.end.y;
		double k1 = Leg.length*Leg.length - start.x*start.x - start.y*start.y;
		double k2 = Foot.length*Foot.length - foot.end.x*foot.end.x - foot.end.y*foot.end.y; 
		double c1 = a1 - a2;
		double d1 = b1 - b2;
		double f1 = k1 - k2;
		double a = 1+c1*c1/(d1*d1);
		double b = a1 - 2*f1*c1/(d1*d1) - b1*c1/d1;
		double c = f1*f1/(d1*d1) + b1*f1/d1 - k1;
		double delta = b*b-4*a*c;
		double end_x = (int)((-b+Math.sqrt(delta))/(2*a));
		double end_y = (int)((f1 - c1*end_x)/d1);
		end.x = (int)end_x;
		end.y = (int)end_y;
	}
	public void updateFoot_end(Point delta){
		foot.end.x = foot.end.x-delta.x;
		foot.end.y = foot.end.y-delta.y;
	}
}
