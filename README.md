# Connected-Component-Project
  
 @author:  Teresa Iles
 COSC 3304 Algorithm Design and Analysis
 Instructor:  Dr. Lawrence Osborne
 Date: 3/10/2021
  
 Assignment:  Programming Project 1
 
 Objective:
 The purpose of this program is to compute the  compute the size of the largest connected
 component of black pixels in an n × n bitmap B[1 .. n, 1 .. n].
  
 Dependencies:  
 These are libraries from the website for this course: https://algs4.cs.princeton.edu/code/
 In.java, StdIn.java, StdOut.java, WeightedQuickUnionUF.java
 
 Input file information:
 The bitmap file to be analyzed will have the following format:\
     - The first line of the file is the number n, which specifies the n x n size of the bitmap.  
     - The next n lines will have n elements that will be either zeros or ones separated by a space. 
     - The zeros represent white pixels, the ones represent black pixels. 
  
 Here is an example input file for a 5x5 bitmap.\
 5\
 1 1 0 0 1\
 0 0 1 1 1\
 1 0 1 1 0\
 0 1 0 1 1\
 0 0 0 0 1
  
 The following files will be provided to test the program:
   - bitmapTest1.bmp
   - bitmapTest2.bmp
   - bitmapTest3.bmp
 
 Expected output:
 The program will print to the console the line:  
 "The size of the largest connected component of black pixels is: " \
 with the appropriate number instead of the empty blank.\ 
 The program can handle the case of zero black pixels and will print:\
 "There are no black pixels. The size of the largest connected component of black pixels is: 0"\
 Note: When I was testing the program, I had it print the bitmap and the number of components.  I have \
 decided to leave these lines in the program, but they are commented out.
 
 How to run this program:
 Using Dr. Java, open my program file MaxConnectedBlackPixels.java. \ 
 Additionally, load the WeightedQuickUnionUF.java class from the booksite into the project pane, so that the program can use it.\
 It can be found at:  https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/WeightedQuickUnionUF.java.html \
 Compile the program.\
 Go to the "Interactions" pane at the bottom of the screen.\
 At the command prompt (>), type: run MaxConnectedBlackPixels bitmap_filename.bmp \  
 Be sure to use the filename for the file you want to test in place of "bitmap_filename.bmp" in the line above.\
 Your command to run might look like this, if you use one of my files:\
  > run MaxConnectedBlackPixels bitmapTest1.bmp    
 
 Algorithm design:\
 The pixels will be stored in an array of size n x n called pixelGrid[n][n]. This 2D array will store zeros and ones that represent
 the bitmap. I will create a weighted union find data structure to process the points in pixelGrid[n][n] and connect them if black pixels touch.
 The weighted union find data structure is called pixelUF[].  It is a 1D array of size n * n.  Each element in the pixelUF[n x n] array 
 represents a point on the grid (pixelGrid[n][n], or a pixel on the bitmap.  It stores information that tells us if that pixel is part 
 of a tree, and what the root of that tree is.  If the pixel is part of a tree, that means it is connected to all the other pixels in 
 that tree.  We will only connect pixels that are black and are linked horizontally or vertically in the bitpmap.   
 We will also create an array called count[] of size n * n.  This array will store how many times a point/pixel is the root of 
 a tree in the pixelUF.  The count value stored at its index in count[] will be the number of pixels that make up the component.
 The component with the most pixels pointing to it as the root will be the largest component.  After creating count[] by iterating through
 pixelUF, we will iterate through count to find the max value, which is the solution to the problem.
  
 The pixels are joined together in pixelsUF by converting the 2D indexes into their 1D index equivalent.
    -We call the function union(p, q)
    -When we find horizontal connections, p = i * n + j and q = i * n + (j + 1).
    -When we find vertical connections, p = j + i * n and q = j + (i - 1) * n 
  
 Once the pixelsUF is built, then we need to figure out which pixel is a root with the most elements in its tree.
 I created a count[] array to hold a count of the number of times that an element is found to be the root of another element in the UF.
 For example, let's say that point (0,0) is linked to point (0, 1) and point (1, 0), making a group of three pixels linked together.
 And, let's say that point(0,0) is the root of these connected elements in the UF.  Then count[point(0,0)] will be equal to three
 because (0,0) will have itself as the root, (0,1) will have point (0,0) as the root, and (1, 0) will have (0, 0) as the root.
 Of course, our index is not point(0, 0), it is just 0 in this case.  And, in general it is an index that points to the specified grid
 point (x, y).  The count[] array as the same index numbering as the 1D array in the data structure pixelsUF.
 
 Now that the count[] array has been created, we iterate through this array to find the largest value for count. We set maxCount = 0, then 
 go into a for loop to iterate.  Compare count[k] to maxCount.  If count[k] is greater than maxCount, update maxCount with this new value.
 After going through all the elements in count[], maxCount tells us the largest number of black pixels linked together.
 
