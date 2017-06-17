package SelectMedian;
import java.util.*;

/**
 *
 * @author Rahnuma Islam Nishat
 * January 20, 2017
 * CSC 226 - Spring 2017
 */
public class SelectMedian {

    /*
    takes an integer array A and an integer k as argument, 
    and returns the kth integer in A when sorted in an 
    ascending order. Your task is to write the body of the 
    LinearSelect function. You may assume that the input array 
    A will always conform to the specification (containing no 
    negative values). 
    */

    /*
     * Input: An array A of n non-negative integers.
     * Output: The kth integer.
    */
    public static int LinearSelect(int[] A, int k){
        //case if A is empty
        if(A == null){
            return -1;
        }

        int n = A.length;
        //base case
        if(n == 1){
        	return A[0];
        }
        //Let L, E, G be empty arrays
        int[] L = new int[n]; int[] E = new int[n]; int[] G = new int[n]; 
        //int variables for array lengths
        int le = 0; int eq = 0; int gr = 0; //piv = 0;

        //creates an array of the medians
        int[] X = pickPivot(A);

        //select the median of the medians
        int p = X[(X.length-1)/2];

        //divides all elements of A
        for(int i = 0; i < n; i++){
        	if(A[i] < p){ //the elements smaller than p go in L
        		L[le] = A[i]; le++;
        	} else if (A[i] == p){ //the elements equal to p go in E
        		E[eq] = A[i]; eq++;
        	} else{ //the elements larger than p go in G
        		G[gr] = A[i]; gr++;
        	}
        }
        //recursive calls
        if(k <= le){ 
            //k is smaller than pivot
        	int[] arr = new int [le];
            System.arraycopy( L, 0, arr, 0, le);
        	return LinearSelect(arr, k);
        } else if (k <= (le + eq)){
            //k is equal to pivot
        	return p;
        } else{
            //k is bigger than pivot
        	int[] arr = new int[gr];
            System.arraycopy( G, 0, arr, 0, gr);
        	return LinearSelect(arr, k-le-eq);
        }
    } 

    /*
     *
     * helper method to populate an array with the medians
     * this array is returned so that a median pivot can be chosen
    
     * Input: An array A of n non-negative integers.
     * Output: An array of the medains of A 
    */
    private static int[] pickPivot(int[] A){

        int n = A.length;
        int[] X = new int[n];
        int piv = 0;

        //check to see if A can divide evenly into arrays of size 5
        int mod = A.length % 5; int i = 0;
        if(mod != 0){
            int[] temp = new int[mod];
            for( ; i < mod % 5; i++){
                temp[i] = A[i];
            }
            Arrays.sort(temp);
            //add median of temp to X
            X[piv] = temp[(temp.length-1)/2];
            piv++;
        }
        // Divide remainder of A into sequences S of size 5 each
        int[] S = new int[5]; int j = 0;
        for( ; i < n; i++){
            S[j] = A[i]; 
            j++;

            if(j == 5){
                Arrays.sort(S);
                //add median of S to X
                X[piv] = S[2]; 
                piv++;              
                //clear S
                j = 0;
                S = new int[5];
            }
        } 

        //create a new array for medians with correct length
        int[]medians = new int[piv];
        System.arraycopy(X, 0, medians, 0, piv); //src, 0, dest, 0, src. length
       
        return medians;
    }

    public static void main(String[] args) {
        int[] A = {50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59};
        
        int[] temp = new int[A.length];
        System.arraycopy(A, 0, temp, 0, A.length); //src, 0, dest, 0, src. length
        Arrays.sort(temp);
        System.out.print("A sorted: ");
        for(int num = 0; num < temp.length; num++){
            System.out.print(temp[num] + " ");
        }
        System.out.println();
        System.out.println("The median weight is " + LinearSelect(A, A.length/2));

        int[] B = {3, 75, 12, 20};
        System.out.println("The median weight is " + LinearSelect(B, B.length/2));

        int[] C = {1, 2, 4, 5, 3};
        System.out.println("The median weight is " + LinearSelect(C, (C.length+1)/2));

        int[] D = {75};
        System.out.println("The median weight is " + LinearSelect(D, D.length/2));

        int[] E = {100, 54, 49, 49, 48, 49, 56, 52, 51, 52, 22, 59,
                    99, 96, 97, 55, 34, 22, 66, 33, 44, 66, 77, 88,
                    11, 54, 49, 49, 48, 49, 56, 52, 51, 52, 9, 59,
                    10, 54, 49, 49, 48, 49, 56, 52, 51, 52, 22, 59,
                    10, 54, 49, 49, 48, 49, 56, 52, 51, 52, 22, 59,
                    10, 54, 49, 49, 48, 49, 56, 52, 51, 52, 22, 59,
                    7, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59,
                    50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59,
                    50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 11, 59,
                    12, 54, 49, 49, 2, 49, 56, 52, 51, 52, 50, 59,
                    50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 16, 59,
                    10, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59,
                    99, 96, 97, 55, 34, 22, 66, 33, 44, 66, 77, 88,
                    99, 96, 97, 55, 34, 22, 66, 33, 44, 66, 77, 88,
                    99, 96, 97, 55, 34, 22, 66, 33, 44, 66, 77, 88,
                    99, 96, 97, 55, 34, 22, 66, 33, 44, 66, 77, 88,
                    93, 94, 95, 96, 97, 98, 99, 100};
        
        temp = new int[E.length];
        System.arraycopy(E, 0, temp, 0, E.length); //src, 0, dest, 0, src. length
        Arrays.sort(temp);
        System.out.print("E's length = " + E.length);
        System.out.println(" & E's median should be: " + temp[temp.length/2]);

        System.out.println("The median weight is " + LinearSelect(E, E.length/2));
    }
    
}
