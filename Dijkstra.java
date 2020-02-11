import java.util.Scanner;
class Dijkstra
{ 
	static void Iterations(int[][] air,int SourcePoint,int a,int[][] airIteration)
	{
		int nextNode=0;
		int lastmin=0;
		for(int z=0;z<a-1;z++)
		{
			
			int min=99999;
			for(int j=0;j<a;j++)
			{
				if(j==SourcePoint || air[SourcePoint][j]<=lastmin)
					continue;
				if(min>air[SourcePoint][j])
				{
					min=air[SourcePoint][j];
					nextNode=j;
				}
			} 
			lastmin=min;
			
			airIteration[z][0]=nextNode;
			for(int j=0;j<a;j++)
			{
				airIteration[z][j+1]=air[SourcePoint][j];
				
			}
			
			for(int j=0;j<a;j++) //relaxation
			{
				if(air[SourcePoint][j] > air[SourcePoint][nextNode]+air[nextNode][j])
					air[SourcePoint][j]=air[SourcePoint][nextNode]+air[nextNode][j];
			}
		}
	}
	
	static void dispArray(int[][] air,String[] airport,int a)
	{
		System.out.print("-");
		System.out.print("\t");
		for(int i=0;i<a;i++)
		{
			System.out.print(airport[i]);
			System.out.print("\t");
		}
		System.out.println();
		
		for(int i=0;i<a;i++)
		{
			System.out.print(airport[i]);
			System.out.print("\t");
			for(int j=0;j<a;j++)
			{
				System.out.printf("%d ",air[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	static void setBaseArray(int[][] air,int a)
	{
		for(int i=0;i<a;i++)
		{
			for(int j=0;j<a;j++)
			{
				if(i==j)
					air[i][j]=0;
				else
					air[i][j]=99999; // setting arbitrarily high 
			}
		}
	}
	
	static void dispIteration(int a,int[][] airIteration)
	{
		for(int i=0;i<a-1;i++)
		{
			for(int j=0;j<a+1;j++)
			{
				System.out.print(airIteration[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	
	
	static int q=0; // q acts as pointer to fill the array airport
	static int initilizer(String name,String[] arr)
	{
		int x=-1;
		for(int i=0;i<arr.length && arr[i]!=null;i++)
		{
			if(name.equals(arr[i]))
			{
				x=i;
				break;
			}
		}
		if(x==-1)
		{
			arr[q]=name;
			x=q;
			q++;
		}
		return x;
	}
	static int dispShortestPath(int[] order,int[][] airIteration,int DestPoint,int SourcePoint,int a)
	{
		
		order[0]=DestPoint;
		int d=1;
		int backTracker=DestPoint;
		for(int i=a-2;i>0;i--)
		{
			if(airIteration[i][backTracker+1]==airIteration[i-1][backTracker+1])
				continue;
			
			else
			{
				order[d]=airIteration[i-1][0];
				d++;
				backTracker=airIteration[i-1][0];
			}
		}
		order[d]=SourcePoint;
		return d;
	}
	
	
	public static void main (String[] args)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter number of airports you want");
		int a=sc.nextInt();  // a-> number of airports
		System.out.println("enter the source node and destination node");
		String start=sc.next();
		String end=sc.next();
		String[] airport = new String[a]; //airport array storing all airports
		int[][] air = new int[a][a]; //two dimensional array to store the distances
		
		setBaseArray(air,a); // setting base by making everything 99999 
		
		System.out.println("Enter all the distances");
		String src;
		String dest;
		int dist;
		int source;
		int destination;
		int k=0;
		while(k<19) // k is compared with the number of entries for the distance (assuming we know the count of entries beforehand)
		{
			src= sc.next();
			dest= sc.next();
			dist=sc.nextInt();
			source=initilizer(src,airport);
			destination=initilizer(dest,airport);
			air[source][destination]=dist;
			air[destination][source]=dist;
			k++;
		}
		
		dispArray(air,airport,a); // displays the 2d array
		
		// finds the position of Source and Destination in airport array
		int SourcePoint=initilizer(start,airport); 
		int DestPoint=initilizer(end,airport);
		System.out.println();
		
		int[][] airIteration = new int[a-1][a+1]; //to store each iteration
		Iterations(air,SourcePoint,a,airIteration); // iterations begins and gets stored in airIteration array
		
		dispIteration(a,airIteration); //displays the iterations
		
		int[] order= new int[a]; //to store the order to find shortest path
		int pos=dispShortestPath(order,airIteration,DestPoint,SourcePoint,a); //stores the shortest path in array order and returns the element counts in it
		
		System.out.println("\nShortest distance between "+start+" and "+end+" => "+air[SourcePoint][DestPoint]);
		System.out.print("Shortest path is "); // print the shortest path from order array
		for(int i=pos;i>-1;i--)
		{
			System.out.print(airport[order[i]]+"->");
		}
		sc.close();
		System.out.println();
	}
}
