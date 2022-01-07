import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;


public class hw4
{
	// Number of pixels
	static int	WIDTH = 1024;
	static int	HEIGHT = 768;

	static int[][]	r = new int[WIDTH][HEIGHT];
	static int[][]	g = new int[WIDTH][HEIGHT];
	static int[][]	b = new int[WIDTH][HEIGHT];
	
	// Where Java stores images
	static BufferedImage	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	// For display
	static JFrame frame;
	static JLabel label;

	
	
	public static void main (String args[])
	{
			hit();
			makeimage();
			showimage();
	}
	
	public static void hit(){
		
	//sphere variables
	double spc, xs, ys, zs;
	double xc = 700;
	double yc = 200;
	double zc = 1000;
	double spr = 100;

	//ray variables
	double xo = 512;
	double yo = 384;
	double zo = -400;
	//double xd = 700;
	//double yd = 700;	these have to change to reach the legnth / height
	double zd = 700;
	
	//dx = xo + xd * t;
	//y = yo + yd * t;
	//z = zo + zd * t;
	

	//equation variables
	//double A = (dx*dx + dy*dy + dz*dz);
	//double B = 2*dx * (xo - xc) + 2*dy * (yo - yc) + 2*dz * (zo - zc);
	//double C = (xc*xc) + (yc*yc) + (zc*zc) + (xo*xo) + (yo*yo) + (zo*zo) + ((-2) * ((xc * xo) + (yc * yo) + (zc * zo))) - (spr * spr);
	
	// given this: A * (t*t) + B * t + C == 0, solve for t
	
	// t = (B*B) - (4 * A * C);
		
		for (int yd = 0; yd < HEIGHT; yd++)
			
			for (int xd = 0; xd < WIDTH; xd++){
				
				double dx = xd - xo;
				double dy = yd - yo;
				double dz = zd - zo;
				
				double A = (dx*dx + dy*dy + dz*dz);
				double B = (2*dx) * (xo - xc) + (2*dy) * (yo - yc) + (2*dz) * (zo - zc);
				double C = (xc*xc) + (yc*yc) + (zc*zc) + (xo*xo) + (yo*yo) + (zo*zo) + ((-2) * ((xc * xo) + (yc * yo) + (zc * zo))) - (spr * spr);
				
				//solve for t 
				
			//substituting discriminant formula for t: A * (((B*B) - (4 * A * C))*((B*B) - (4 * A * C))) + B * ((B*B) - (4 * A * C)) + C == 0
				
				if ((B*B) - (4 * A * C) >= 0){
					
					double t1 = (-1 * B) - Math.sqrt(B*B - 4 * A * C) / 2 * A;
					double t2 = (-1 * B) + Math.sqrt(B*B - 4 * A * C) / 2 * A;
					
					xd = (int)xo + (int)(t1 * dx);
					yd = (int)yo + (int)(t1 * dy);
					
					int xd2 = (int)xo + (int)(t2* dx);
					int yd2 = (int)yo + (int)(t2 * dy);
					
					//System.out.printf("if 2: x = %s\ny = %s",xd,yd);
					g[xd][yd] = 255;
					g[xd2][yd2] = 255;
				}
			
	
	
	}
	}

	// Convert R[][] G[][] B[][] into an imagebuffer
	public static void makeimage()
	{
		int x, y;

		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++)
				image.setRGB(x,y, (r[x][y] << 16) | (g[x][y] << 8) | b[x][y]);
				
	}

	// Display the image
	public static void showimage()
	{
		if(frame==null)
		{
		       frame=new JFrame();
		       frame.setTitle("Results");
		       frame.setSize(image.getWidth(), image.getHeight());
		       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		       label=new JLabel();
		       label.setIcon(new ImageIcon(image));
		       frame.getContentPane().add(label,BorderLayout.CENTER);
		       frame.setLocationRelativeTo(null);
		       frame.pack();
		       frame.setVisible(true);
		}
		else 
			label.setIcon(new ImageIcon("grass.png"));

	}


}

