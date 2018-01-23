/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 2 - Template for ChunkMergesort
 * 
 * This template includes some testing code to help verify the implementation.
 * To interactively provide test inputs, run the program with:
 * 
 *     java ChunkMergesort
 * 
 * To conveniently test the algorithm with a large input, create a text file
 * containing space-separated integer values and run the program with:
 * 
 *     java ChunkMergesort file.txt
 * 
 * where file.txt is replaced by the name of the text file.
 * 
 * Miguel Jimenez
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Do not change the name of the ChunkMergesort class
public final class ChunkMergesort {
	
	/**
	 * Use this class to return two lists.
	 * 
	 * Example of use:
	 * 
	 * Chunks p = new Chunks(S1, S2); // where S1 and S2 are lists of integers
	 */
	public static final class Chunks {
		
		private final List<Integer> left;
		private final List<Integer> right;
		
		public Chunks(List<Integer> left, List<Integer> right) {
			this.left = left;
			this.right = right;
		}
		
		public List<Integer> left() {
			return this.left;
		}
		
		public List<Integer> right() {
			return this.right;
		}
	}

	/**
	 * The list containing the integer comparisons in order of occurrence. Use
	 * this list to persist the comparisons; the method report will be invoked
	 * to write a text file containing this information.
	 * 
	 * Example of use (when comparing 1 and 9):
	 * 
	 * Integer[] d = new Integer[2];
	 * d[0] = 1;
	 * d[1] = 9;
	 * this.comparisons.add(d);
	 * 
	 * or just:
	 * 
	 * this.comparisons.add(new Integer[]{1, 9});
	 */
	private final List<Integer[]> comparisons;
	//number of chunks
	private static int c;
	private static int listSize;

	public ChunkMergesort() {
		this.comparisons = new ArrayList<Integer[]>();
	}

	/*
	 *  ChunkMergesort, which takes a list S as its only argument and returns a list.

	 * In chunk mergesort, the recursive algorithm takes advantage of already sorted “chunks” in S,
	 * that is of subsequences of consecutive elements in S that are in increasing order. 	
	*/

	public List<Integer> chunkMergesort(List<Integer> S) {
		if(S.isEmpty()) {
			throw new UnsupportedOperationException("Cannot chunkMergesort empty list");
		} 
		Chunks p = new Chunks(null, null);
		c = 1;
		List<Integer> S1 = new ArrayList<Integer>(S.size());
		List<Integer> S2 = new ArrayList<Integer>(S.size());
		List<Integer> sorted = new ArrayList<Integer>(S.size());
		if(S.size() < 2){
			return S;
		} else{
			//System.out.println("size of list: " + S.size());
			//determine the number of chunks
			for(int i = 0; i<S.size()-1;i++){
				if(S.get(i)>S.get(i+1)){
					c++;
				} this.comparisons.add(new Integer[]{S.get(i), S.get(i+1)});
			}
			//System.out.println("number of chunks: " + c);
			//base case
			if(c < 2){
				return S;
			}
			p = chunkDivide(S, c);
			//recursive calls
			S1 = chunkMergesort(p.left);
			S2 = chunkMergesort(p.right);
			sorted = merge(S1, S2);

		} 
		/* following lines used to debug sorted list
		System.out.println("sorted list: ");
		for(int i = 0; i < sorted.size(); i++){
			System.out.print(sorted.get(i) + " ");
		} */
		return sorted;
	}
	/*
	 * ChunkDivide, which takes list S, and integer c, the number of chunks that S contains, 
	 * and returns lists S1 and S2. (In the case that c is odd, S1 shall contain more chunks than S2).
	 * (remove all elements from S and put them into two sequences, S1 and S2)
	 *
	 * (chunk divide): After dividing, as with traditional mergesort, elements are divided into sequences
	 * S1 and S2. In chunk divide when dividing the sequence into two, instead of containing about the
	 * same number of elements each, we divide according to the number of chunks. That is in S1 and
	 * S2 the number of chunks is to be about the same each.
	*/
	//size of list is greater than 2 (determined in chunkMergesort)
	public Chunks chunkDivide(List<Integer> S, int c) {
		if(S.isEmpty()) {
			throw new UnsupportedOperationException("Cannot divide empty list");
		} else if (S.size()<2){
			throw new UnsupportedOperationException("Cannot divide lists of size 1");
		}
		List<Integer> S1 = new ArrayList<Integer>(S.size());
		List<Integer> S2 = new ArrayList<Integer>(S.size());
		//determine if S1 will have more chunks than S2
		int count;
		if (c%2 == 0){
			count = c/2;
		} else {
			count = c/2 +1;
		}
		int i;
		//add elements to S1
		for(i = 0; i < S.size(); i++){
			S1.add(S.get(i));
			//if the next number is less than current, count chunk
			if(S.get(i)>S.get(i+1)){
				count--;
			} //this.comparisons.add(new Integer[]{S.get(i), S.get(i+1)});
			//after adding half the chunks, exit loop
			if(count == 0){
				break;
			}
		}
		//the remaining elements will be in S2
		for(int j = i+1; j < S.size(); j++){
			S2.add(S.get(j));
		}
		/* the following code was used to determine S1 & S2 divided properly
		System.out.println("Size of S1:" + S1.size());
		System.out.println("Size of S2:" + S2.size());
		
		System.out.print("S1: ");
		for(i =0; i < S1.size(); i++){
			System.out.print(S1.get(i) + " ");
		}
		System.out.print("\nS2:");
		for(i =0; i < S2.size(); i++){
			System.out.print(S2.get(i) + " ");
		}
		System.out.println();
		*/	
		Chunks p = new Chunks(S1, S2);
		return p;
		
	}

	/*
	 * Merge takes lists S1 and S2, and returns a (sorted) list.
	*/
	public List<Integer> merge(List<Integer> S1, List<Integer> S2) {
		if(S1.isEmpty() && S2.isEmpty()) {
			throw new UnsupportedOperationException("Cannot merge empty lists");
		} 
		int n = S1.size() + S2.size();
		List<Integer> S = new ArrayList<Integer>(n);
		
		while(!S1.isEmpty() && !S2.isEmpty()){
			if(S1.get(0) < S2.get(0)){
				this.comparisons.add(new Integer[]{S1.get(0), S2.get(0)});
				S.add(S1.remove(0));
			} 
			else {
				this.comparisons.add(new Integer[]{S1.get(0), S2.get(0)});
				S.add(S2.remove(0));
			} 

		}
		while(!S1.isEmpty()){
			S.add(S1.remove(0));
		}
		while(!S2.isEmpty()){
			S.add(S2.remove(0));
		}
		return S;
	}

	/**
	 * Writes a text file containing all the integer comparisons in order of
	 * ocurrence.
	 * 
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public void report() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("comparisons.txt", false));
		for (Integer[] data : this.comparisons)
			writer.append(data[0] + " " + data[1] + "\n");
		writer.close();
	}

	/**
	 * Contains code to test the chunkMergesort method. Nothing in this method
	 * will be marked. You are free to change the provided code to test your
	 * implementation, but only the contents of the methods above will be
	 * considered during marking.
	 */
	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0) {
			try {
				s = new Scanner(new File(args[0]));
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		} else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of integers:\n");
		}
		List<Integer> inputList = new ArrayList<Integer>();

		int v;
		while (s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputList.add(v);

		s.close();
		System.out.printf("Read %d values.\n", inputList.size());

		long startTime = System.currentTimeMillis();

		ChunkMergesort mergesort = new ChunkMergesort();
		List<Integer> sorted = mergesort.chunkMergesort(inputList);

		
		System.out.println("sorted list: ");
		for(int i = 0; i < sorted.size(); i++){
			System.out.print(sorted.get(i) + " ");
		} 
		System.out.println();

		long endTime = System.currentTimeMillis();
		double totalTimeSeconds = (endTime - startTime) / 1000.0;

		System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
		
		try {
			mergesort.report();
			System.out.println("Report written to comparisons.txt");
		} catch (IOException e) {
			System.out.println("Unable to write file comparisons.txt");
			return;
		}
	}

}
