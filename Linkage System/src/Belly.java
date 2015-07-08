import java.awt.Color;
import java.awt.Graphics;

public class Belly {
	Ham left;
	Ham right;
	Point location;
	Point init;
	public Belly(Point loc){ 
		this.location = new Point(loc.x,loc.y);
		this.init = new Point(loc.x,loc.y);
		left = new Ham(loc);
	//	right = new Ham(loc);
	}
	public void updatePoints(Double an){
		changeStart(an);
		left.updatePoints(an);
	//	right.updatePoints(an+0.2);
	}
	public void paint(Graphics g){
		Color c= g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(location.x-30, location.y-20,60, 40);
		left.paint(g);
		//right.paint(g);
		g.setColor(c);
	}
	public void changeStart(Double an){
		int totalL = Ham.length+Leg.length;
		location.x = init.x-(int)(totalL*Math.sin(an/10));
		location.y = init.y + totalL - (int)(totalL*Math.cos(an/10)); 
	}
}
