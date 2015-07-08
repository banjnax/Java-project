import java.awt.Color;
import java.awt.Graphics;

public class Ham {
	Point start;
	Point end;
	Point preEnd;
	Point init;
	double angle = 0.0;
	static int length = 200;
	Leg leg = null;
	public Ham(Point p){
		start = new Point(p.x,p.y);
		init = new Point(p.x,p.y);
		end = new Point(p.x,p.y+length);
		preEnd = new Point(p.x,p.y+length);
		leg = new Leg(start,end);
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.black);
		g.drawOval(start.x-5, start.y-5, 10, 10);
		g.drawLine(start.x, start.y, end.x, end.y);
		g.drawOval(end.x-5, end.y-5, 10, 10);
		leg.paint(g);
		g.setColor(c);
	}
	public void updatePoints(Double an){
		this.angle = an;
		preEnd.x = end.x;
		preEnd.y = end.y;
		end.x = (int) (start.x - length*Math.sin(angle));
		end.y = (int) (start.y + length*Math.cos(angle));
		changeStart(an);
		if(angle>=Math.PI/5) leg.updateFoot_end(new Point(preEnd.x-end.x,preEnd.y-end.y));
		leg.updatePoints(end);
	}
	public void changeStart(Double an){
		int totalL = Ham.length+Leg.length;
		start.x = init.x-(int)(totalL*Math.sin(an/10));
		start.y = init.y + totalL - (int)(totalL*Math.cos(an/10)); 
	}
}
