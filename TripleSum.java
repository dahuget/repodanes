/* TripleSum.java
   CSC 225 - Summer 2016
   Assignment 1 - Template for TripleSum
   TripleSum225 Method Completed by: DANA HUGET
   ID: V00860786
   Date: Sept 20, 2016

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java TripleSum

   To conveniently test the algorithm with a large input, create a
   text file containing space-separated integer values and run the program with
	java TripleSum file.txt
   where file.txt is replaced by the name of the text file.

   B. Bird
   
   CSC 225

*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

//Do not change the name of the TripleSum class
public class TripleSum{
	/* TripleSum225()
		The input array A will contain non-negative integers. The function
		will search the input array A for three elements which sum to 225.
		If such a triple is found, return true. Otherwise, return false.
		The triple may contain the same element more than once.
		The function may modify the array A.

		Do not change the function signature (name/parameters).
	*/
	public static boolean TripleSum225(int[] A){
		//create new array of length 226, true if number is in A
		boolean[] arr = new boolean[226];
		//iterate through given array
		for(int i = 0; i < A.length; i++){
			//check for numbers less than 226 
			//(anything higher will be disregarded)
			if(A[i] < 226){
				//set arr at index of number in A to true
				arr[A[i]] = true;
			}
		}
		//nested for loop to find three numbers that add to 225
		for(int i = 1; i < 226; i++){
			for(int j = 1; j < 226; j++){
				for(int k = 1; k < 226; k++){
					//check what numbers add to 225
					if(i + j + k == 225){
						//check to see if those numbers are in array
						if(arr[i] == true && arr[j] == true && arr[k] == true){
							//yay! found the TripleSum225!!
							return true;
						}	
					}
				}
			}
		}
		//if three numbers are not found, return false
		return false;
	}

	/* main()
	   Contains code to test the TripleSum225 function. Nothing in this function
	   will be marked. You are free to change the provided code to test your
	   implementation, but only the contents of the TripleSum225() function above
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);

		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.currentTimeMillis();

		boolean tripleExists = TripleSum225(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("Array %s a triple of values which add to 225.\n",tripleExists? "contains":"does not contain");
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}
