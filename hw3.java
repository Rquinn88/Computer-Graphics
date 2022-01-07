import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.lang.*;
import java.awt.Color;

public class hw3 
{
	
	static int	WIDTH = 1024;
	static int	HEIGHT = 768;
	
	static int[][]	r = new int[WIDTH][HEIGHT];
	static int[][]	g = new int[WIDTH][HEIGHT];
	static int[][]	b = new int[WIDTH][HEIGHT];
	
	static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	// For display
	static JFrame frame;
	static JLabel label;
	
public static void main (String[] args) 
{
	Scanner in = new Scanner(System.in);
	int choice;
	boolean cont = true;
	int ln = 0;
	
	while(cont = true){
		
	System.out.printf("\n1 - line\n2 - Circle\n3 - Square\n4 - Save Drawing\n5 - Exit Program\n");
	choice = in.nextInt();
		
		if(ln == 0)	
		background(Color.WHITE);
		
		if(choice == 1){
		drawline();
		showimage();
		ln ++;
		}
	
		if(choice == 2){
		drawcircle();
		showimage();
		ln ++;
		}
		
		if(choice == 3){
		drawsquare();
		showimage();
		ln ++;
		}
		
		if(choice == 4){
		writefile("NewDrawing.PNG");
		System.out.printf("\nYour drawing has been written to file, its name is 'NewDrawing.PNG");
		System.exit(0);

		}
		
		if(choice == 5)
		System.exit(0);
	}
		
}

public static void drawline()
{
	//y = mx + b
	Scanner in = new Scanner(System.in);
	int x0, y0, x1, y1;
		
	System.out.printf("Insert Coordinates:\nX0 = ");
		x0 = in.nextInt();
		
	System.out.printf("X1 = ");
		x1 = in.nextInt();
		
	System.out.printf("Y0 = ");
		y0 = in.nextInt();
		
	System.out.printf("Y1 = ");
		y1 = in.nextInt();

	int dx, dy, eq, x, y, temp0, temp1;
	
	if(x0 > x1){
		temp0 = x0;
		x0 = x1;
		x1 = temp0;
	}
	
	if(y0 > y1){
		temp1 = y0;
		y0 = y1;
		y1 = temp1;
	}
	
	dx = x1 - x0;
	dy = y1 - y0;
	
	x = x0;
	y = y0;
	
	eq = 2 * dy - dx;
	
	while(x < x1)
	{
		if(eq >= 0){
			colorpixel(x,y);
			y ++;
			eq = eq + 2 * dy - 2 * dx;
		}
		
		else{
			colorpixel(x,y);
			eq = eq + 2 * dy;
		}
		
		x ++;
	}
}

public static void drawcircle()
{
	//(x-x0)^2 + (y-y0)^2 = r2
	
	int xcen, ycen, radius;
	 
	Scanner in = new Scanner(System.in);
		
	System.out.printf("Insert Coordinates for Center:\nX Coordinate for Center = ");
		xcen = in.nextInt();
		
	System.out.printf("Y Coordinate for Center = ");
		ycen = in.nextInt();
		
	System.out.printf("Radius = ");
		radius = in.nextInt();

	int radius2 = radius * radius;
	
	int x = 0;
	int y = (int)Math.sqrt(radius2 - 1);
	
	colorpixel(xcen, ycen + radius);
	colorpixel(xcen, ycen - radius); 
	colorpixel(xcen + radius, ycen);
	colorpixel(xcen - radius, ycen);

	for (x = 1; x <= radius; x++){
	
	eightplotx(xcen, ycen, x, y);
	eightplotx(xcen, ycen, y, x);
	
	x++;
	y = (int)(Math.sqrt(radius2 - x * x) + .5);

	}

} 

public static void drawsquare()
{
	//(x-x0)^2 + (y-y0)^2 = r2
	
	int xcen, ycen, midtoside;
	 
	Scanner in = new Scanner(System.in);
		
	System.out.printf("Insert Coordinates for Center:\nX Coordinate for Center = ");
		xcen = in.nextInt();
		
	System.out.printf("Y Coordinate for Center = ");
		ycen = in.nextInt();
		
	System.out.printf("Distance from Center to Side = ");
		midtoside = in.nextInt();
	
	int x = 1;
	int y = midtoside;
	
	colorpixel(xcen, ycen + midtoside);
	colorpixel(xcen, ycen - midtoside); 
	colorpixel(xcen + midtoside, ycen);
	colorpixel(xcen - midtoside, ycen);

	for (x = 1; x <= midtoside; x++){
	
	eightplotx(xcen, ycen, x, y);
	eightplotx(xcen, ycen, y, x);
	
	x++;

	}

} 
  
public static void colorpixel(int x, int y)
	{
		image.setRGB(x,y, (r[0][0]) | (g[0][0]) | b[0][0]);
	}

public static void eightplotx(int xcen, int ycen, int x, int y)  
	{  
		colorpixel(xcen + x, ycen + y);
		colorpixel(xcen + x, ycen - y);
		colorpixel(xcen - x, ycen + y);
		colorpixel(xcen - x, ycen - y);
		
	}  

public static void background(Color c)
	{
		int x, y;
		int color = c.getRGB();
		
		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++)
				image.setRGB(x, y, color);
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
			label.setIcon(new ImageIcon(image));

	}
	
	public static void writefile(String name)
	{
		try
		{
			File outputfile = new File(name);
			ImageIO.write(image, "png", outputfile);
		}
		catch (IOException e)
		{
			System.out.printf ("HALP!!! I can't write it!!\n");
		}
	}
}



