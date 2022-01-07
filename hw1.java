import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;


public class hw1 {
	
	static int	WIDTH = 1024;
	static int	HEIGHT = 768;

	/*
	 * Arrays containing the RGB data
	 */
	static int[][]	r = new int[WIDTH][HEIGHT];
	static int[][]	g = new int[WIDTH][HEIGHT];
	static int[][]	b = new int[WIDTH][HEIGHT];

	// Where Java stores images
	static BufferedImage	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);


	// For display
	static JFrame frame;
	static JLabel label;

public static void main (String args[]) {
	
	Scanner in = new Scanner(System.in);
	int choice;
	
	System.out.printf("Options:\n0 - Show Original Image\n1 - Greyscale\n2 - Swap R->G G->B B->R\n3 - Flip Image\n4 - Invert Image\n");
	choice = in.nextInt();
	
	switch (choice){
		case 0:
			getimage("grass.png");
			showimage();
			break;
		case 1:
			getimage("grass.png");
			greyscale();
			showimage();
			break;
		case 2:
			getimage("grass.png");
			swap();
			showimage();
			break;
		case 3:
			getimage("grass.png");
			flip();
			showimage();
			break;
		case 4:
			getimage("grass.png");
			invert();
			showimage();
			break;
	}

}

public static void makeimage()
	{
		int x, y;

		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++)
				image.setRGB(x,y, (r[x][y] << 16) | (g[x][y] << 8) | b[x][y]);
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
			label.setIcon(new ImageIcon("grass.png"));

	}

public static void getimage(String name)
	{
		int x, y;
		int color;
		int k;

		try
		{
			image = ImageIO.read(new File("grass.png"));

			for (x=0; x<WIDTH; x++)
				for (y=0; y<HEIGHT; y++)
				{
					k = image.getRGB(x, y);
					// bit masking, the usual
					r[x][y] = (k & 0xff0000) >> 16;
					g[x][y] = (k & 0xff00) >> 8;
					b[x][y] = k & 0xff;
				}
		}
		catch (IOException e)
		{
			System.out.printf ("HALP!!! I can't read it!!\n");
		}

			
	}
	public static void greyscale()
	{
		int x, y;
		int avg = ((r[1][1] + g[1][1] + b[1][1]) / 3);
		
		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++)

			image.setRGB(x,y, (r[avg][avg]) | (g[avg][avg]) | b[avg][avg]);
			
	}
	public static void swap()
	{
		int x, y;
		
		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++)
				image.setRGB(x,y, (g[x][y] << 16) | (b[x][y] << 8) | r[x][y]);
	}
	
	public static void flip()
	{
		int x, y;
		int end = WIDTH - 1;
		
		int[][]	nr = new int[end][HEIGHT];
		int[][]	ng = new int[end][HEIGHT];
		int[][]	nb = new int[end][HEIGHT];
		
		for (x=0; x < WIDTH; x++)
			for (y=0; y < HEIGHT; y++)
			
				image.setRGB(x,y, (r[end - x][y] << 16) | (g[end - x][y] << 8) | b[end - x][y]);
				//image.setRGB((end - x), y, image.getRGB(x, y));
				//image.setRGB(x,y, (g[x][y] << 16) | (b[x][y] << 8) | r[x][y]);
	}
	
		public static void invert()
	{
		int x, y;
		int end = HEIGHT - 1;
		
		int[][]	nr = new int[WIDTH][end];
		int[][]	ng = new int[WIDTH][end];
		int[][]	nb = new int[WIDTH][end];
		
		for (y=0; y<HEIGHT; y++)
			for (x=0; x< WIDTH; x++)
				
				image.setRGB(x,y, (r[(x)][end - y] << 16) | (g[(x)][end - y] << 8) | (b[(x)][end - y]));
			
	}

}

	