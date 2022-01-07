//This assignment creates a greenscreen where a chosen foreground image is placed on top of a chosen background image

// Various imports
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class hw6
{
	// Number of pixels
	static int	WIDTH = 1024;
	static int	HEIGHT = 768;

	//Arrays containing the RGB data for the foreground image
	static int[][]	fr = new int[WIDTH][HEIGHT];
	static int[][]	fg = new int[WIDTH][HEIGHT];
	static int[][]	fb = new int[WIDTH][HEIGHT];

	// Arrays containing the RGB data for the background image
	static int[][]	br = new int[WIDTH][HEIGHT];
	static int[][]	bg = new int[WIDTH][HEIGHT];
	static int[][]	bb = new int[WIDTH][HEIGHT];

	// Where Java stores images
	static BufferedImage	
	image0 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	static BufferedImage	
	image1 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	static BufferedImage	
	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	// For display
	static JFrame frame;
	static JLabel label;


	public static void main (String args[])
	{
		String fore;
		String back;
		String out;
		Scanner in = new Scanner(System.in);
	
		System.out.printf("Enter Foreground image name: ");
		fore = in.next();
		
		System.out.printf("Enter Background image name: ");
		back = in.next();
		
		System.out.printf("Enter Output image name: ");
		out = in.next();
		
		getimage(fore, back);
		
		greenscreen();

		// Display the image
		showimage();

		// Write it to a file
		writefile(out);
	}
	
	//Method to implement the greenscreen
	public static void greenscreen()
	{
		int x, y;
		
		for (y=0; y<HEIGHT; y++)
			for (x=0; x<WIDTH; x++){
				
				image.setRGB(x,y, (fr[x][y] << 16) | (fg[x][y] << 8) | fb[x][y]);
				
				//Best values for the images that were tested 
				//These may be less accurate for different images
				if (fr[x][y] >= 0 && fr[x][y] <= 20 &&
					fb[x][y] >= 58 && fb[x][y] <= 63 &&
					fg[x][y] >= 160 && fg[x][y] <= 180){
					
					image.setRGB(x,y, (br[x][y] << 16) | (bg[x][y] << 8) | bb[x][y]);
					
				}
		}
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
			label.setIcon(new ImageIcon(image));

	}

	// Read a PNG from a file
	public static void getimage(String name0, String name1)
	{
		int x, y;
		int color;
		int k;

		try
		{
			image0 = ImageIO.read(new File(name0));
			for (x=0; x<WIDTH; x++)
				for (y=0; y<HEIGHT; y++)
				{
					k = image0.getRGB(x, y);
					// bit masking, the usual
					fr[x][y] = (k & 0xff0000) >> 16;
					fg[x][y] = (k & 0xff00) >> 8;
					fb[x][y] = k & 0xff;
				}

			image1 = ImageIO.read(new File(name1));
			for (x=0; x<WIDTH; x++)
				for (y=0; y<HEIGHT; y++)
				{
					k = image1.getRGB(x, y);
					// bit masking, the usual
					br[x][y] = (k & 0xff0000) >> 16;
					bg[x][y] = (k & 0xff00) >> 8;
					bb[x][y] = k & 0xff;
				}
				
		}
		catch (IOException e)
		{
			System.out.printf ("HALP!!! I can't read it!!\n");
		}

			
	}

	// And writing it
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
