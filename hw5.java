import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class hw5
{
		static int	WIDTH = 1024;
		static int	HEIGHT = 768;
		static int maxsphere = 20;
		
		static int xo, yo, zo, zd, numshperes;
		static int x1, y1, z1, r1, g1, b1;
		static Double rad1;
		
		static int[] x = new int[maxsphere];
		static int[] y = new int[maxsphere];
		static int[] z = new int[maxsphere];
		static int[] r = new int[maxsphere];
		static int[] g = new int[maxsphere];
		static int[] b = new int[maxsphere];
		static Double[] rad = new Double[maxsphere];
		
		static int[][]	rv = new int[WIDTH][HEIGHT];
		static int[][]	gv = new int[WIDTH][HEIGHT];
		static int[][]	bv = new int[WIDTH][HEIGHT];
		
		static BufferedImage	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
		static JFrame frame;
		static JLabel label;
		
		static sphere[] sphere = new sphere[maxsphere];
		
	public static void main (String args[]) 
	{
		read();
		
		sort();
		
		for(int i = 1;i <= numshperes; i++)
			draw(sphere[i], xo, yo, zo, zd);
		
		makeimage();
		
		showimage();
		
	}
	
	//Sort Spheres by z point value
	public static void sort() {
		
		for(int i = 1;i <= numshperes; i++) 
			for (int j = 1; j < (numshperes); j++) {

            int result = sphere[i].compareTo(sphere[j]);
			
			System.out.printf("sphere[i]: %d - sphere[j]: %d = %d\n", i, j, result);
			
            if (result < 0) {
				//switch sphere[i] with sphere[j]
                sphere temp = sphere[j];
                sphere[j] = sphere[i];
                sphere[i] = temp;
            }
		
       }
	   
	   //Print Sorted Sphere Values
	   for(int i = 1;i <= numshperes; i++){
			System.out.printf("Sorted Spheres: \n %d, %d, %d, %d, %d, %d, %f\n",
			sphere[i].center.x,
			sphere[i].center.y,
			sphere[i].center.z,
			sphere[i].r,
			sphere[i].g,
			sphere[i].b,
			sphere[i].radius);
	   }
	}
	
	//Read from config.txt file
	public static void read() {
	
	try {
		
		File file = new File("C:\\Users\\rquin\\Downloads\\config.txt");
	
		Scanner find = new Scanner(file); 
		Scanner s = new Scanner(file);
		
		find.next();
		xo = find.nextInt();
		yo = find.nextInt();
		zo = find.nextInt();
		find.next();
		zd = find.nextInt();
		find.next();
		numshperes = find.nextInt();
		
		
		System.out.printf("%d, %d, %d, %d, %d\n", xo, yo, zo, zd, numshperes);
			
		for(int i = 1;i <= numshperes;i++){
			
			find.next();
			
			x[i] = find.nextInt();
			y[i] = find.nextInt();
			z[i] = find.nextInt();
			
			find.next();
			
			r[i] = find.nextInt();
			g[i] = find.nextInt();
			b[i] = find.nextInt();
			
			find.next();
			
			rad[i] = find.nextDouble();
	
			sphere[i] = new sphere(x[i], y[i], z[i], r[i], g[i], b[i], rad[i]);
			
			System.out.printf("Unsorted Spheres:\n %d, %d, %d, %d, %d, %d, %f\n",
			sphere[i].center.x,
			sphere[i].center.y,
			sphere[i].center.z,
			sphere[i].r,
			sphere[i].g,
			sphere[i].b,
			sphere[i].radius);
		}
			
	}
	 catch (FileNotFoundException file){
		System.out.printf("File Not Found");
	}
		
  }
  
  //Draw sphere to screen
  public static void draw(sphere s, int xo, int yo, int zo, int zd)
  {
		double x = s.center.x;
		double y = s.center.y;
		double z = s.center.z;
		double radius = s.radius;
		int r = s.r;
		int g = s.g;
		int b = s.b;
	
		for (int yd = 0; yd < HEIGHT; yd++)
			
			for (int xd = 0; xd < WIDTH; xd++){
				
				
				double dx = xd - xo;
				double dy = yd - yo;
				double dz = zd - zo;
				
				double A = (dx*dx) + (dy*dy) + (dz*dz);
				double B = (2*dx) * (xo - x) + (2*dy) * (yo - y) + (2*dz) * (zo - z);
				double C = (x*x) + (y*y) + (z*z) + (xo*xo) + (yo*yo) + (zo*zo) + ((-2) * ((x * xo) + (y * yo) + (z * zo))) - (radius * radius);
				
				if ((B*B) - (4 * A * C) >= 0){
					
					xd = (int)xo + (int)(dx);
					yd = (int)yo + (int)(dy);
					
					rv[xd][yd] = r;
					gv[xd][yd] = g;
					bv[xd][yd] = b;
				}
				
				
			}
	
  }
  
  public static void makeimage()
	{
		int x, y;

		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++)
				image.setRGB(x,y, (rv[x][y] << 16) | (gv[x][y] << 8) | bv[x][y]);
	}
  
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
			label.setIcon(new ImageIcon("sphere.png"));
	}
  
}
	
