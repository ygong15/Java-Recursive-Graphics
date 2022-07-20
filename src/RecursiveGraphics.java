/* File: RecursiveGraphics.java
 * Author: YuChen Gong (working with FuCheng Zhu)
 * Purpose: Finished version for PS5 Problem 6
 */

import java.awt.Color;
import java.awt.Canvas;
import java .awt.Graphics;
import javax.swing.JFrame;
import java.util.Random;

public class RecursiveGraphics extends Canvas  {

    private static final int windowSize = 800;     // height and width of window in pixels
     
    
    // Problem 6.0.1:  Go to the paint method below (right above the main method) and try various depths to see the effect. 
    //   Nothing else to do but to try this out to get a feeling for recursive graphics. 
    
    
    private void drawHTree(int depth, Graphics g) {
        drawHTreeHelper(windowSize/2, windowSize/2, windowSize/2, true, depth, g);
    }

    // (x,y) is center of line of length s
    private void drawHTreeHelper(double x, double y, double s, boolean horizontal, int depth, Graphics g) {
 
	if(depth == 0)
            return;
        if(horizontal) {
            drawLine(x-s/2, y, x+s/2, y, g);
            drawHTreeHelper(x-s/2, y, s*(2.0/3.0),false, depth-1, g);     // alternate horizontal and vertical
            drawHTreeHelper(x+s/2, y, s*(2.0/3.0), false, depth-1, g);    // center next line at endpoint of previous
        }                                                                 // and smaller by 2/3. 
        else {  // vertical
            drawLine(x, y-s/2, x, y+s/2, g);
            drawHTreeHelper(x, y-s/2, s*(2.0/3.0), true, depth-1, g);
            drawHTreeHelper(x, y+s/2, s*(2.0/3.0), true, depth-1, g);
        }
        
    }

   // Problem 6.0.2:  Again, go to the paint method below and try various depths to see the effect. 
   //   Nothing else to do but to try this out to get a feeling for recursive graphics.
    
   // Generalization of an H Tree in which can have any number of branches, with
   //    a fixed delta (how much smaller to make it)  for each length and each angle. 

   private void drawTree(int depth, Graphics g) {
	drawTreeHelper(windowSize/2, windowSize*0.85, windowSize/4, Math.PI/2, depth, g);
   }

    // You could try changing these two parameters if you feel adventurous!
    
   private double[] lenDelta = {2.0/3, 2.0/3 };             // multiply length of branch by this factor each time
   private double[] thetaDelta = {Math.PI/5, -Math.PI/5};   // add this delta to angle each time -- in radians
    
    private void drawTreeHelper(double x0, double y0, double len, double angle, int depth, Graphics g) {
       if(depth == 0) {
           return;
       }
       
       // draw lines
       
       double x1 = x0 - len * Math.cos(angle);
       double y1 = y0 - len * Math.sin(angle);
 
       drawLine(x0,y0,x1,y1,g);
       
       for (int i = 0; i < lenDelta.length; ++i) { 
          drawTreeHelper(x1, y1, len * lenDelta[i], angle + thetaDelta[i], depth-1,g);
       }
   }



    //  Problem 6.1    Your Turn!
    //  For this one, you have to figure out how to do the recursive case only.
  
    private void drawFractalLine(double x1, double y1, double x5, double y5, int depth, Graphics g) {
       if (depth <= 1) {
	    drawLine(x1, y1, x5, y5, g);
       }
       else {	  
           
           double deltaX = x5 - x1;     // distance between the two x's
           double deltaY = y5 - y1;     // distance between the two y's
           
           double x2 = x1 + deltaX/3.0;
           double y2 = y1 + deltaY/3.0;

	   double x4 = x5 - deltaX/3.0;
           double y4 = y5 - deltaY/3.0;
	   
           double x3 = ((x1 + x5)/2.0 - (Math.sqrt(3.0)/6.0) * (y1 - y5));
           double y3 = ((y1 + y5)/2.0 - (Math.sqrt(3.0)/6.0) * (x5 - x1));
	   

	   
           // connect all the dots!  You have to call the method recursively on
	   // each of the line segments:
	   //                          (x3,y3) 
	   //                           /  \
	   //                          /    \   
	   //       (x1,y1) --- (x2,y2)     (x4,y4) --  (x5,y5) 

           // Your code here
           drawFractalLine(x1, y1, x2, y2, depth-1, g);
           drawFractalLine(x2, y2, x3, y3, depth-1, g);
           drawFractalLine(x3, y3, x4, y4, depth-1, g);
           drawFractalLine(x4, y4, x5, y5, depth-1, g);


       }
   }

    // Problem 6.2      Supposing you have a square with left corner (x,y) and each side of length s
    //                  Now draw the square with fractal lines instead of normal lines!
    //                  Draw them in the order top, right, bottom, left sides (clockwise from top).

    //                  Note: This is not recursive, it just calls drawFractalLines 4 times. 
   
    private void drawFractalSquare(double x, double y, double s, int depth, Graphics g) {
	
    // Your code here
    drawFractalLine(x, y, x+s, y, depth, g);
    drawFractalLine(x+s, y, x+s, y+s, depth, g);
    drawFractalLine(x+s, y+s, x, y+s, depth, g);
    drawFractalLine(x, y+s, x, y, depth, g);

    }   
   
    


    // Problem 6.3: Draw a Sierpinski Triangle.    The basic method uses drawFilledPolygon, which
    //      draws a filled shape enclosed by the points in X and Y.
    //      The three points are (X[0],Y[0]), (X[1],Y[1]), (X[2],Y[2]). 
    

    
    private void drawSierpinskiTriangle(double X[], double Y[], int depth, Graphics g) {
		
	if (depth==0) {
	    return;
	}
	
        g.setColor(Color.BLACK); 

    // Draw a filled triangle using the points in X and Y
    drawFilledPolygon(X, Y, g);

	// Now calculate the midpoints in each side, as described in the video
	
	double y_mid = (Y[0] + Y[2])/2;
	double x_left = (X[2] + X[0])/2;
	double x_right = (X[1]+ X[0])/2;
	
        g.setColor(Color.WHITE);
        double[] mid_X = {x_left,x_right,X[0]};
        double [] mid_Y = {y_mid,y_mid,Y[2]};
        
	
        // Now recursively fill in middle sub-triangle in white
        drawFilledPolygon(mid_X, mid_Y, g);
	
        // Draw the remaining sub-triangles recursively, doing something like this 3 times:
        double [] left_X = {x_left, X[0], X[2]};
        double [] left_Y = {y_mid, Y[2], Y[2]};
        double [] top_x = {X[0], x_right, x_left};
        double [] top_y = {Y[0], y_mid, y_mid};
        double [] right_x = {x_right, X[1], X[0]};
        double [] right_y = {y_mid, Y[1], Y[1]};
	
      drawSierpinskiTriangle( left_X , left_Y, depth-1, g);       
      drawSierpinskiTriangle(top_x, top_y, depth-1, g);
      drawSierpinskiTriangle(right_x, right_y, depth-1, g);   

    }
  

 
    // Problem 6.4:

    // Draw a Sierpinski Carpet with upper-left corner (x,y) and side of length s
    //     Imagine the S Carpet is a TicTacToe board: draw the whole board blue, then
    //     draw the center square in green, then a circle in red, then recursively draw S Carpets 1/3 the size in
    //     each of the other squares.

    // You can modify the color of the next thing to be drawn on g by using
    //       	g.setColor(Color.BLACK);
    //          g.setColor(Color.WHITE);
    //	        g.setColor(Color.RED);
    //	        g.setColor(Color.GREEN);
    //	        g.setColor(Color.BLUE);
    // Just put one of these lines before the call to a drawing method. 
    
    private void drawSierpinskiCarpet(double X[], double Y[], int depth, Graphics g) {

    // your code here
    if (depth == 0){
        return;
    }
        g.setColor(Color.BLUE);
        drawFilledPolygon(X, Y, g);
        g.setColor(Color.GREEN);
        double S = (X[1] - X[0])*(1.0/3.0);
        double [] mid_x = {X[0]+S, X[1]-S, X[1]-S, X[0]+S};
        double [] mid_y = {Y[0]+S, Y[0]+S, Y[2]-S, Y[2]-S};
        drawFilledPolygon(mid_x, mid_y, g);
        g.setColor(Color.RED);
        drawFilledCircle(X[0]+S, Y[0]+S, S, g);
        
        double[] up_left_x = {X[0], X[0]+S, X[0]+S, X[0]};
        double[] up_left_y = {Y[0], Y[0], Y[0]+S, Y[0]+S};

        double[] up_mid_x = {X[0]+S, X[1]-S, X[1]-S, X[0]+S};
        double[] up_mid_y = {Y[0], Y[0], Y[0]+S, Y[0]+S};

        double[] up_right_x = {X[1]-S, X[1], X[1], X[1]-S};
        double[] up_right_y = {Y[0], Y[0], Y[0]+S, Y[0]+S};

        double[] mid_left_x = {X[0], X[0]+S, X[0]+S, X[0]};
        double[] mid_left_y = {Y[0]+S, Y[0]+S, Y[3]-S, Y[3]-S};

        double[] mid_right_x = {X[1]-S, X[1], X[1], X[1]-S};
        double[] mid_right_y = {Y[1]+S, Y[1]+S, Y[2]-S, Y[2]-S};

        double[] down_left_x = {X[0], X[0]+S, X[0]+S, X[0]};
        double[] down_left_y = {Y[3]-S, Y[3]-S, Y[3], Y[3]};

        double[] down_mid_x = {X[0]+S, X[1]-S, X[1]-S, X[0]+S};
        double[] down_mid_y = {Y[3]-S, Y[3]-S, Y[3], Y[3]};

        double[] down_right_x = {X[1]-S, X[1], X[1], X[1]-S};
        double[] down_right_y = {Y[2]-S, Y[2]-S, Y[2], Y[2]};

        drawSierpinskiCarpet(up_left_x, up_left_y, depth-1, g);
        drawSierpinskiCarpet(up_mid_x, up_mid_y, depth-1, g);
        drawSierpinskiCarpet(up_right_x, up_right_y, depth-1, g);
        drawSierpinskiCarpet(mid_left_x, mid_left_y, depth-1, g);
        drawSierpinskiCarpet(mid_right_x, mid_right_y, depth-1, g);
        drawSierpinskiCarpet(down_left_x, down_left_y, depth-1, g);
        drawSierpinskiCarpet(down_mid_x, down_mid_y, depth-1, g);
        drawSierpinskiCarpet(down_right_x, down_right_y, depth-1, g);
    }

      

    /*****    These are the methods can can be used to draw on the pop-up window.  
     *****    They are just wrappers around methods from Graphics, which expect ints in units of pixels. 
     *****/

    // my version of round, using doubles and ints
    
    private int round(double x) {
	return (int) (x+0.5);
    }

    private int[] round(double x[]) {
	int[] x1 = new int[x.length];
	for (int i = 0; i < x.length; ++i)
	    x1[i] = round(x[i]);
	return x1;
    }

    private double avg(double x, double y) {
	return (x+y)/2.0;
    }

    //    drawLine(x1,y1,x2,y2,g);  draw a line from (x1,y1) to (x2,y2) on g
    //    this is just a wrapper around the Graphics method (which expects ints in units of pixels)

    public void drawLine(double x1, double y1, double x2, double y2, Graphics g) {
	g.drawLine(round(x1),round(y1),round(x2), round(y2));
    }

    //    drawCircle(x,y,x2,y2,g);  draw a circle with a bounding box with upper-left corner (x,y) and side s

    public void drawCircle(double x, double y, double s, Graphics g) {
	g.drawOval(round(x),round(y),round(s),round(s));
    }

    //    drawFilledCircle(x,y,x2,y2,g); draw a circle with a bounding box with upper-left corner (x,y) and side s 

     public void drawFilledCircle(double x, double y, double s, Graphics g) {
	g.fillOval(round(x),round(y),round(s),round(s));
    }   
    
    //    drawPoly(x,y,g); draw a polygon connecting the points (x[0],y[0]), (x[1],y[1]), ...
    
    public void drawPolygon(double X[], double Y[], Graphics g) {
	if (X.length != Y.length) {
	    throw new IllegalArgumentException ("drawPolygon expects two arrays of same length!");
	}
        g.drawPolygon(round(X),round(Y),X.length); 
    }

    //    drawFilledPoly(x,y,g); draw a filled polygon connecting the points (x[0],y[0]), (x[1],y[1]), ...
    
    public void drawFilledPolygon(double X[], double Y[], Graphics g) {
	if (X.length != Y.length) {
	    throw new IllegalArgumentException ("drawFilledPolygon expects two arrays of same length!");
	}
      	g.fillPolygon(round(X),round(Y),X.length); 
    }




   
    /*****      Must override the paint method from Graphics.
     *****      Here is where you put anything you want to draw on
     *****      the pop-up window. You must refer to the Graphics class
     *****      g in your method calls.
     *****
     *****      These start the drawings in an appropriate place in the window; you should
     *****      not have to change anything here except for the maximum recursion depth. 
     *****
     *****      WHEN YOU SUBMIT YOUR SOLUTION, LEAVE ALL OF THESE COMMENTED OUT EXCEPT FOR LAST ONE;
     *****      WHEN WE RUN YOUR PROGRAM IT SHOULD DISPLAY THE S CARPET. 
     *****      THE GRADER WILL UNCOMMENT AND TRY THE REST AS WELL. 
     *****/ 
    
    public void paint(Graphics g) {
        //drawHTree(3,g);       // Problem 6.0.1
	// drawTree(3,g);        // Problem 6.0.2

	 //drawFractalLine(windowSize*0.1, windowSize*0.3, windowSize*0.8, windowSize*0.6, 2, g);     // Problem 6.1	                                                                        
	//drawFractalSquare(windowSize*0.5,windowSize* 0.5,windowSize* 0.2, 2, g);                // Problem 6.2


	//drawSierpinskiTriangle(new double[] { windowSize*0.5,   windowSize, 0          },     // Problem 6.3
                               // new double[] { windowSize*0.134, windowSize, windowSize },
       		                 // 7,  g);        
	
	
	 drawSierpinskiCarpet(new double[] { 0, windowSize, windowSize, 0          },          // Problem 6.4
	                     new double[] { 0, 0,          windowSize, windowSize },
	                     5,  g);           
 
    }
    


    // The main method just creates the window and renders whatever
    // is in the paint method above.  Don't change this!
    
    public static void main(String[] args) {
    
      JFrame frame = new JFrame("CS 112 Recursive Graphics");
      Canvas canvas = new RecursiveGraphics(); 
      canvas.setSize(windowSize,windowSize);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setBackground(Color.WHITE);
      frame.add(canvas);
      frame.pack();
      frame.setVisible(true);
      
    }
}
