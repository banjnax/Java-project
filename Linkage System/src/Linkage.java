import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

public class Linkage extends JFrame{
	private static final long serialVersionUID = 1L;
	static int C_WIDTH=600;
	static int C_HEIGHT=600;
	static int Size_x=200;
	static int Siez_y=30;
	Image vImage=null;//双缓冲消除闪烁的虚拟图片
	Image offScreenImage = null;
	Graphics gImage = null;
	Belly belly = null;
	Ground ground = null;
	Ground ground1 =null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Linkage().launch();
	}
	public void launch(){
		int count=0;
		int Dflag =1,flag=1;
		double angle = 0;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(C_WIDTH, C_HEIGHT);
		this.setLocation(Size_x, Siez_y);
		this.setVisible(true);
		belly = new Belly(new Point(300,100));
		ground = new Ground(new Point(150,450),Color.red);
		ground1 = new Ground(new Point(80,430),Color.red);
		while(true){
			if(count==49){
				flag=-flag;
				count=0;
			}
			count++;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			angle = angle+flag*0.02;
			belly.updatePoints(angle);
			
			//System.out.print(angle +" ");
		}
	}
	public void paint(Graphics g){
		// 在重绘函数中实现双缓冲机制
		offScreenImage = this.createImage(C_WIDTH, C_HEIGHT);
        // 获得截取图片的画布
		gImage = offScreenImage.getGraphics();
        // 获取画布的底色并且使用这种颜色填充画布,如果没有填充效果的画，则会出现拖动的效果
        gImage.setColor(gImage.getColor());
        gImage.fillRect(0, 0, C_WIDTH, C_HEIGHT); // 有清楚上一步图像的功能，相当于gImage.clearRect(0, 0, WIDTH, HEIGHT)     
        // 调用父类的重绘方法，传入的是截取图片上的画布，防止再从最底层来重绘     
        super.paint(gImage);
		
		belly.paint(gImage);
		ground.paint(gImage);
		ground1.paint(gImage);
		// 将接下来的图片加载到窗体画布上去，才能考到每次画的效果    
		this.setBackground(Color.white);
        g.drawImage(offScreenImage, 0, 0, null);     
	}

}
