import java.awt.Color;
import java.awt.Graphics;


public class Ground {
	Color color = Color.gray;
	int loc_x;
	int loc_y;
	int length = 200;
	public Ground(Point x){
		loc_x = x.x;
		loc_y = x.y;
	}
	public Ground(Point x,Color c){
		this.color = c;
		loc_x = x.x;
		loc_y = x.y;
	}
	public void paint(Graphics g){
		Color c =  g.getColor();
		g.setColor(color);
		g.drawRect(loc_x, loc_y,length, 10);
		g.setColor(c);
	}
}
