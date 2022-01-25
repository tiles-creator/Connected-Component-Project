/*@author:  Teresa Iles
 * COSC 3304 Algorithm Design and Analysis
 * Instructor:  Dr. Lawrence Osborne
 * Date: 3/10/2021
 *
 * Assignment:  Programming Project 1
 *
 * This program reads in a bitmap of n x n pixels.  The bitmap will be constructed of 0's and 1's, where 0 = white
 * pixel, and 1 = black pixel.  The program will compute the size of the largest connected component of black
 * pixels in the bitmap.
 *
 * The pixels will be stored in an array of size n * n.
 *
 * We will use the weighted union-find data type to store the points and connect them, as needed.
 *
 * Using Dr. Java, at the command prompt, type:
 *  > run MaxConnectedBlackPixels bitmap_filename.bmp
 *
 * The bitmap file must contain:
 * n   // the size of the n x n matrix
 * 1 1 0 0 1  // pixel values of 1 or 0, separated by a space, each row represents a row in the bitmap grid
 * 0 0 1 1 1
 * ...
 *
 *
 */

import java.util.Arrays;

public class MaxConnectedBlackPixels
{

  public static void main(String [] args) throws Exception
  {
    In in = new In(args[0]);

    // read n, which is the size of the n x n pixel bitmap
    int n = in.readInt();

    // Create the pixel grid.  Since it is composed of 0's and 1's, I will use the byte data type
    byte[][] pixelGrid = new byte[n][n];

    // I decided to track if there are no black pixels so that the program would print a correct result in this case.
    // If there are no black pixels, the maxCount prints out 1 because there is still a root for each element in the UF tree.
    boolean noBlackPixels = true;

    // Read bitmap data into the 2D pixelGrid array
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        {
             pixelGrid[i][j] = in.readByte();
             if (pixelGrid[i][j] == 1)
               noBlackPixels = false;
        }

    // Print the bitmap data
    for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
        StdOut.print(pixelGrid[i][j] + " ");
      StdOut.println();
    }

    // If there are no black pixels, we can skip doing all this work.
    if (!noBlackPixels)
    {
       // Use a weighted UF data structure to store black pixel connections
       // This data structure is a one-dimensional array, representing the 2-dimensional pixel grid
       // At first, each array holds the value of itself, each link or pixel is it's own component
       // As pixels get linked, the UF will show store the connections
       WeightedQuickUnionUF pixelsUF = new WeightedQuickUnionUF(n*n);

       // For loop to check all pixels for neighbors horizontally and vertically
       // Adjusts pixelsUF according to connections
       for (int i = 0; i < n; i++)  // Iterate through rows
       {
         // Check for horizontal connections across the row
         // If two neighboring pixels are black, connect their components in the union-find array
         for (int j = 0; j < n-1; j++)
            if (pixelGrid[i][j] == 1 && pixelGrid[i][j+1] == 1)
            {
               int p = i * n + j;        // Identify the pixelGrid[i][j] element position in the pixelsUF[] array
               int q = i * n + j + 1;   // Identify the pixelGrid[i][j+1] element position in the pixelsUF[] array
               pixelsUF.union(p, q);    // Link the two components because they are horizontal neighbors with
                                        // black pixels
            }

         // Check for vertical connections across two rows
         if (i != 0)   // Cannot do this check on first row
         {
            // If two vertically adjacent (same column) pixels are black, connect their components in the union-find array
            for (int j = 0; j < n; j++)
              if (pixelGrid[i][j] == 1 && pixelGrid[i-1][j] == 1)
              {
                 int p = j + i * n;      // Identify the pixelGrid[i][j] element position in the pixelsUF[] array
                 int q = j + (i-1) * n;  // Identify the pixelGrid[i+1][j] element position in the pixelsUF[] array
                 pixelsUF.union(p, q);  // Link the two components because they are vertical neighbors with
                                        // black pixels
              }
          }
      }  // End main for loop

       // Create an array called count, where the index of count is the canonical root value of the element, being
       // analyzed.  The values stored in counts[] hold the number of elements linked together at that root.
       int [] count = new int[n * n];
       int root;

       // Iterate through all the components of pixelsUF to determine which one has the most elements
       // Add one to count[root] each time an element k links so that element root.
       for (int k = 0; k < (n * n); k++)
       {
          root = pixelsUF.find(k);
          count[root]++;
        }

       // maxCount stores the largest value of connected components from the count[] array
       int maxCount = 0;

       // Iterate through the array of counts to find the max and print the max
       for (int k = 0; k < (n * n); k++)
       {
         if (count[k] > maxCount)
           maxCount = count[k];
       }

      // StdOut.println("Number of components: " + pixelsUF.count());
      StdOut.println("The size of the largest connected component of black pixels is: " + maxCount);

    } // End of "if (!noBlackPixels)" statement

    if (noBlackPixels)
       StdOut.println("There are no black pixels. The size of the largest connected component of black pixels is: 0");


  }  // End main method
}  // End program
