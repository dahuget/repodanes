
package shorteshpath;
/* ShortestPath.java
   CSC 226 - Spring 2017
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPath
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

/*
 * Edited by Dana Huget, V00860786 for CSC226 Assignment 4 Mar 14, 2017
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.*;


//Do not change the name of the ShortestPath class
public class ShortestPath{
	/*
	 * Vertex node class
	*/
	public static class Vertex{
		//vertex distance
		public int dist, id;
		//parent of vertex
		public Vertex parent;
		public boolean visited;

		//constructor, params distance and parent vertex
		public Vertex(int id, int dist){
			this.dist = dist;
			this.id = id;
			this.parent = null;
			this.visited = false;
		}
	}	

	/*
	 * Comparator for Vertex node
	*/
	public static class VertComp implements Comparator<Vertex>{
		@Override
		public int compare(Vertex vert1, Vertex vert2){
			//compare edge weights/vertex distance
			if(vert1.dist < vert2.dist){ //if edge 1 is less than edge 2 == -1
				return -1;
			} else if(vert1.dist > vert2.dist){ //if edge 1 is greater than edge 2 == 1
				return 1;
			} else{ //return 0 because equal
				return 0; 
			}
		}
	}
	
	public static int numVerts;
	//array to keep track of vertices
	public static Vertex[] verts;



    //TODO: Your code here   
	/* ShortestPath(G)
		Given an adjacency matrix for graph G, calculates and stores the shortest paths to all the
                vertces from the source vertex.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static void ShortestPath(int[][] G, int source){
		numVerts = G.length;
		verts = new Vertex[numVerts];
		 //TODO: Your code here DIJKSTRA using min-priority queue
		
		//creating a vertex comparator object
		Comparator<Vertex> comp = new VertComp();
		//initializing a priority queue with vertex comparator, set of unsettled vertices
		Queue<Vertex> vPQ = new PriorityQueue<Vertex>(numVerts, comp);

		//for each vertex v in Graph:
		for(int i = 0; i < numVerts; i++){
			if(i == source){ //vertex is source
				verts[i] = new Vertex(i, 0);
			} else {
				verts[i] = new Vertex(i, 99999);
				//add vertex to queue of vertices unsettled distances
				vPQ.add(verts[i]);				
			}
		}
		

		Vertex curr;
		//while Queue is not empty: ...Node with the least distance will be selected first
		while(vPQ.size() != 0){
			//remove and return best vertex
			curr = vPQ.poll();
			//for each neighbor v of u: ...where v is still in Q
			for(int i = 0; i < numVerts; i++){
				//from the root node to the neighbor node v if it were to go through u
				if(G[curr.id][i] != 0){   
					//A shorter path to v has been found
					if(curr.dist > (verts[i].dist + G[curr.id][i])){
						//update distance ie "relaxing"
						verts[curr.id].dist = verts[i].dist + G[curr.id][i];
						//update parent
						verts[curr.id].parent = verts[i];
					} 
				}	 
			} 
			verts[curr.id].visited = true; 
		} 
	}
        /* Format for printing paths = 
The<space>path<space>from<space>0<space>to<space>2<space>is:<space>0<space>-->
<space>2<space>and<space>the<space>total<space>distance<space>is<space>:<space>9<newline>
        */
    static void PrintPaths(int source){
       //TODO: Your code here
       int totalDist = 0;          
       //trace and reverse the path before outputting any of it.
    	//print out the path, related to the parent
		for(int i = 0; i < numVerts; i++){
			System.out.printf("The path from 0 to %d is: 0 ", i);
			//stack holds path from source to destination 	
			Stack<Vertex> path = findPath(source, i);
			while(!path.isEmpty()){ 
				//returns the first vertex in path
				Vertex v = path.pop();
				//add distance of vertex
				totalDist+=v.dist;
				if(v.id != 0){
					System.out.printf("--> %d ", v.id);
				}					
			}
			System.out.printf("and the total distance is : %d\n", totalDist);
			totalDist = 0;
		}
    }
    /*
	 * helper method to find path from source to destination vertex
	 * returns a vertex Stack representation of path
    */
	private static Stack<Vertex> findPath(int source, int dest){
		//stack to keep track of path backwards from destination to path
		Stack<Vertex> path = new Stack<Vertex>();
		//add destination to stack
		path.push(verts[dest]);
		while(source != dest){
			//add parent of destination to stack
			path.push(verts[dest].parent);
			//System.out.printf("\nverts[dest %d].parent.id = %d\n", dest, verts[dest].parent.id);
			//update destination to its parent
			dest = verts[dest].parent.id;
		}

		return path;
	}	
	/* main()
	   Contains code to test the ShortestPath function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args) throws FileNotFoundException{
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
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			ShortestPath(G, 0);
                        PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}





