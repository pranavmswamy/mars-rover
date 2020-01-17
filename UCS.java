import java.util.Comparator;
import java.util.PriorityQueue;

public class UCS {

	int totalRows;
	int totalCols;
	int elevationLimit;
	int[][] terrain;
	int targetRow, targetCol;
	int landingCol, landingRow;
	boolean[][] visited;
	boolean[][] presentInQueue;
	
	public UCS(int landingRow, int landingCol, int targetRow, int targetCol, int [][] terrain, int elevationLimit, int totalRows, int totalCols)
	{
		this.totalRows = totalRows;
		this.totalCols = totalCols;
		this.elevationLimit = elevationLimit;
		this.targetRow = targetRow;
		this.targetCol = targetCol;
		this.terrain = terrain;
		this.landingCol = landingCol;
		this.landingRow = landingRow;
		visited = new boolean[totalRows][totalCols];
		presentInQueue = new boolean[totalRows][totalCols];
	}
	
	public boolean isValidEdge(int i, int j, int elevationOfParent)
	{
		if(i<0 || i>= totalRows || j<0 || j>=totalCols)
			return false;
		if(Math.abs(terrain[i][j] - elevationOfParent) > elevationLimit)
			return false;
		
		return true;
	}
	
	public int setGoalValue(int i, int j)
	{
		if(i==landingRow && j==landingCol)
			return 2;
		if(i==targetRow && j==targetCol)
			return 1;
		return 0;
	}
	
	class PathCostCompare implements Comparator<Node>
	{
		@Override
		public int compare(Node n1, Node n2) {
			if (n1.pathCost < n2.pathCost) return -1; 
	        if (n1.pathCost > n2.pathCost) return 1; 
	        else return 0;
		}
		
	}
	
	int min(int x, int y)
	{
		return x<y?x:y;
	}
	
	public StringBuilder startUCS()
	{
		PriorityQueue<Node> ucsQ = new PriorityQueue<Node>(new PathCostCompare());
		StringBuilder path;
		
		Node root = new Node(2,terrain[landingRow][landingCol],null,0,landingRow,landingCol);
		ucsQ.add(root);
		presentInQueue[landingRow][landingCol] = true;
		
		while(ucsQ.isEmpty() == false)
		{
			Node n = ucsQ.poll();
			
			if(n.state == 1)
			{
				
				path = new StringBuilder();
				while(n != null ) 
				{	
					path.insert(0, " " + n.col + "," + n.row);
					n = n.parent;
				}
				path.deleteCharAt(0);
				path.append('\n');
				return path;
			}
			
			visited[n.row][n.col] = true;
			
			if(isValidEdge(n.row-1 ,n.col-1,n.value) == true && visited[n.row-1][n.col-1] == false)
			{
				if(presentInQueue[n.row-1][n.col-1] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row-1 && k.col == n.col-1)
						{
								k.pathCost = min(k.pathCost, n.pathCost+14);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row-1,n.col-1),terrain[n.row-1][n.col-1],n,n.pathCost+14,n.row-1,n.col-1));
					presentInQueue[n.row-1][n.col-1] = true;
				}
			}
				
			if(isValidEdge(n.row-1 ,n.col,n.value) == true && visited[n.row-1][n.col] == false)
				if(presentInQueue[n.row-1][n.col] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row-1 && k.col == n.col)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row-1,n.col),terrain[n.row-1][n.col],n,n.pathCost+10,n.row-1,n.col));
					presentInQueue[n.row-1][n.col] = true;
				}
			
			if(isValidEdge(n.row-1 ,n.col+1,n.value) == true && visited[n.row-1][n.col+1] == false)
				if(presentInQueue[n.row-1][n.col+1] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row-1 && k.col == n.col+1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+14);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row-1,n.col+1),terrain[n.row-1][n.col+1],n,n.pathCost+14,n.row-1,n.col+1));
					presentInQueue[n.row-1][n.col+1] = true;
				}
				
			if(isValidEdge(n.row ,n.col+1,n.value) == true && visited[n.row][n.col+1] == false)
				if(presentInQueue[n.row][n.col+1] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row && k.col == n.col+1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row,n.col+1),terrain[n.row][n.col+1],n,n.pathCost+10,n.row,n.col+1));
					presentInQueue[n.row][n.col+1] = true;
				}
				
			if(isValidEdge(n.row+1 ,n.col+1,n.value) == true && visited[n.row+1][n.col+1] == false)
				if(presentInQueue[n.row+1][n.col+1] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row+1 && k.col == n.col+1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+14);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row+1,n.col+1),terrain[n.row+1][n.col+1],n,n.pathCost+14,n.row+1,n.col+1));
					presentInQueue[n.row+1][n.col+1] = true;
				}
				
			if(isValidEdge(n.row+1 ,n.col,n.value) == true && visited[n.row+1][n.col] == false)
				if(presentInQueue[n.row+1][n.col] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row+1 && k.col == n.col)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row+1,n.col),terrain[n.row+1][n.col],n,n.pathCost+10,n.row+1,n.col));
					presentInQueue[n.row+1][n.col] = true;
				}
				
			if(isValidEdge(n.row+1 ,n.col-1,n.value) == true && visited[n.row+1][n.col-1] == false)
				if(presentInQueue[n.row+1][n.col-1] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row+1 && k.col == n.col-1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+14);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row+1,n.col-1),terrain[n.row+1][n.col-1],n,n.pathCost+14,n.row+1,n.col-1));
					presentInQueue[n.row+1][n.col-1] = true;
				}
			if(isValidEdge(n.row ,n.col-1,n.value) == true && visited[n.row][n.col-1] == false)
				if(presentInQueue[n.row][n.col-1] == true)
				{
					for(Node k : ucsQ)
					{
						if(k.row == n.row && k.col == n.col-1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10);
								break;
						}
					}
				}
				else
				{
					ucsQ.add(new Node(setGoalValue(n.row,n.col-1),terrain[n.row][n.col-1],n,n.pathCost+10,n.row,n.col-1));
					presentInQueue[n.row][n.col-1] = true;
				}
					
		}	
		path = new StringBuilder("FAIL\n");
		return path;
	}
}
