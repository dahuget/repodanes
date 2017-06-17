/* MWST.java
   CSC 225 - Spring 2012
   Assignment 5 - Template for a Minimum Weight Spanning Tree algorithm

   Modified by Dana Huget, V00860786
   Prim's lazy Algorithm Implementation 
   Programming Assignment 2
   Feb 24, 2017 - CSC 226-A01
   
   The assignment is to implement the mwst() method below, using any of the algorithms
   studied in the course (Kruskal, Prim-Jarnik or Baruvka). The mwst() method computes
   a minimum weight spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in O(mlog(n)) time
   on a graph with n vertices and m edges.

   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java MWST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
    java MWST graphs.txt
	
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
    3
	0 1 0
	1 0 2
	0 2 0
	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   B. Bird - 03/11/2012
*/

import java.util.Scanner;
import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;

public class MWST{

	/*
	 * 
	*/
	public static class Edge{
		//edge weight
		public int weight;
		//unexplored endpoint of edge, vertex going to
		public int end;

		//constructor, params weight and endpoint vertex
		public Edge(int w, int e){
			this. weight = w;
			this. end = e;
		}
	}

	/*
	 * Edge Weight Comparator class that overrides Java Comparator
	*/
	public static class edgeWeightComp implements Comparator<Edge>{
		@Override
		public int compare(Edge edge1, Edge edge2){
			//compare edge weights
			if(edge1.weight < edge2.weight){ //if edge 1 is less than edge 2 == -1
				return -1;
			} else if(edge1.weight > edge2.weight){ //if edge 1 is greater than edge 2 == 1
				return 1;
			} else{ //return 0 because equal
				return 0; 
			}
		}
	}

	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all edges in a minimum weight spanning tree.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int mwst(int[][] G){
		int numVerts = G.length;

		/* Find a minimum weight spanning tree by any method */
		/* (You may add extra functions if necessary) */
		
		/* Add the weight of each edge in the minimum weight spanning tree
		   to totalWeight, which will store the total weight of the tree.
		*/
		int totalWeight = 0;
		
		//creating and edge comparator object
		Comparator<Edge> comp = new edgeWeightComp();
		//initializing a priority queue with edge comparator
		Queue<Edge> ePQ = new PriorityQueue<Edge>(1, comp);
		//array to keep track of visited vertices
		boolean[] visited = new boolean[numVerts];
		
		//populate queue with edges of starting vertex
		edgeQueue(G, 0, ePQ);

		//go through all edges in queue
		while(ePQ.size() != 0){ //aka while queue is not empty
			Edge temp = ePQ.poll(); //retrieve and remove min weight edge
			if(!visited[temp.end]){ //vertex has not been visited
				//mark current vertex as visited
				visited[temp.end] = true;
				//add edge weight to MWST total
				totalWeight += temp.weight;
				//add all edges of current vertex to queue
				edgeQueue(G, temp.end, ePQ);
			}
		}
		return totalWeight;
	}
	/*
	 * helper method that adds the edges of a given vertex to 
	 * the current edge priority queue & returns the priority queue
	*/
	private static Queue<Edge> edgeQueue(int[][] G, int vertex, Queue<Edge> ePQ){
		int numVerts = G.length;
		//populate queue with edges of given vertex in graph G
		for(int i = vertex; i < numVerts; i++){
			if(G[vertex][i] != 0){ //there is an edge coming from vertex!
				//create Edge object with params weight and other endpoint
				Edge e = new Edge(G[vertex][i], i);
				//add edge to priority queue
				ePQ.add(e); 
			}
		}
		return ePQ;
	}

	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(!s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				G[i] = new int[n];
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			if (!isConnected(G)){
				System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
				continue;
			}
			int totalWeight = mwst(G);
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
				
		}
	}

	/* isConnectedDFS(G, covered, v)
	   Used by the isConnected function below.
	   You may modify this, but nothing in this function will be marked.
	*/
	static void isConnectedDFS(int[][] G, boolean[] covered, int v){
		covered[v] = true;
		for (int i = 0; i < G.length; i++)
			if (G[v][i] > 0 && !covered[i])
				isConnectedDFS(G,covered,i);
	}
	   
	/* isConnected(G)
	   Test whether G is connected.
	   You may modify this, but nothing in this function will be marked.
	*/
	static boolean isConnected(int[][] G){
		boolean[] covered = new boolean[G.length];
		for (int i = 0; i < covered.length; i++)
			covered[i] = false;
		isConnectedDFS(G,covered,0);
		for (int i = 0; i < covered.length; i++)
			if (!covered[i])
				return false;
		return true;
	}

}