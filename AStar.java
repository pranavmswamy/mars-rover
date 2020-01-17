import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {
	
	int totalRows;
	int totalCols;
	int elevationLimit;
	int[][] terrain;
	int targetRow, targetCol;
	int landingCol, landingRow;
	boolean[][] visited;
	boolean[][] presentInQueue;
	
	public boolean isValidEdge(int i, int j, int elevationOfParent)
	{
		if(i<0 || i>= totalRows || j<0 || j>=totalCols)
			return false;
		if(Math.abs(terrain[i][j] - elevationOfParent) > elevationLimit)
			return false;
		
		return true;
	}
	
	int min(int x, int y)
	{
		return x<y?x:y;
	}
	
	public int calculateHeuristic(int sourceNodeRow, int sourceNodeCol, int goalNodeRow, int goalNodeCol)
	{	
		return Math.abs(sourceNodeRow-goalNodeRow)+Math.abs(sourceNodeCol-goalNodeCol)+Math.abs(terrain[sourceNodeRow][sourceNodeCol] - terrain[goalNodeRow][goalNodeCol]); // 3d manhattan distance heuristic
	}
	
	public int setGoalValue(int i, int j)
	{
		if(i==landingRow && j==landingCol)
			return 2;
		if(i==targetRow && j==targetCol)
			return 1;
		return 0;
	}
	
	public AStar(int landingRow, int landingCol, int targetRow, int targetCol, int [][] terrain, int elevationLimit, int totalRows, int totalCols)
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
		
		for(int i = 0; i<totalRows; i++)
		{
			for(int j=0;j<totalCols; j++)
				System.out.format("%4d", terrain[i][j]);
			System.out.println();
		}
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
	
	public StringBuilder startAStar()
	{
		PriorityQueue<Node> aStarQ = new PriorityQueue<Node>(new PathCostCompare());
		StringBuilder path;
		
		Node root = new Node(2,terrain[landingRow][landingCol],null,0+calculateHeuristic(landingRow,landingCol,targetRow,targetCol),landingRow,landingCol);
		aStarQ.add(root);
		presentInQueue[landingRow][landingCol] = true;
		
		while(aStarQ.isEmpty() == false)
		{
			Node n = aStarQ.poll();
			
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
					for(Node k : aStarQ)
					{
						if(k.row == n.row-1 && k.col == n.col-1)
						{
								k.pathCost = min(k.pathCost, n.pathCost+14+Math.abs(n.value - terrain[n.row-1][n.col-1])+(calculateHeuristic(n.row-1,n.col-1,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row-1,n.col-1),terrain[n.row-1][n.col-1],n,n.pathCost+14+Math.abs(n.value - terrain[n.row-1][n.col-1])+(calculateHeuristic(n.row-1,n.col-1,targetRow,targetCol)),n.row-1,n.col-1));
					presentInQueue[n.row-1][n.col-1] = true;
				}
			}
				
			if(isValidEdge(n.row-1 ,n.col,n.value) == true && visited[n.row-1][n.col] == false)
				if(presentInQueue[n.row-1][n.col] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row-1 && k.col == n.col)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10+Math.abs(n.value - terrain[n.row-1][n.col])+(calculateHeuristic(n.row-1,n.col,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row-1,n.col),terrain[n.row-1][n.col],n,n.pathCost+10+Math.abs(n.value - terrain[n.row-1][n.col])+(calculateHeuristic(n.row-1,n.col,targetRow,targetCol)),n.row-1,n.col));
					presentInQueue[n.row-1][n.col] = true;
				}
				
			if(isValidEdge(n.row-1 ,n.col+1,n.value) == true && visited[n.row-1][n.col+1] == false)
				if(presentInQueue[n.row-1][n.col+1] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row-1 && k.col == n.col+1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+14+Math.abs(n.value - terrain[n.row-1][n.col+1])+(calculateHeuristic(n.row-1,n.col+1,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row-1,n.col+1),terrain[n.row-1][n.col+1],n,n.pathCost+14+Math.abs(n.value - terrain[n.row-1][n.col+1])+(calculateHeuristic(n.row-1,n.col+1,targetRow,targetCol)),n.row-1,n.col+1));
					presentInQueue[n.row-1][n.col+1] = true;
				}
				
			if(isValidEdge(n.row ,n.col+1,n.value) == true && visited[n.row][n.col+1] == false)
				if(presentInQueue[n.row][n.col+1] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row && k.col == n.col+1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10+Math.abs(n.value - terrain[n.row][n.col+1])+(calculateHeuristic(n.row,n.col+1,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row,n.col+1),terrain[n.row][n.col+1],n,n.pathCost+10+Math.abs(n.value - terrain[n.row][n.col+1])+(calculateHeuristic(n.row,n.col+1,targetRow,targetCol)),n.row,n.col+1));
					presentInQueue[n.row][n.col+1] = true;
				}
				
			if(isValidEdge(n.row+1 ,n.col+1,n.value) == true && visited[n.row+1][n.col+1] == false)
				if(presentInQueue[n.row+1][n.col+1] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row+1 && k.col == n.col+1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+14+Math.abs(n.value - terrain[n.row+1][n.col+1])+(calculateHeuristic(n.row+1,n.col+1,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row+1,n.col+1),terrain[n.row+1][n.col+1],n,n.pathCost+14+Math.abs(n.value - terrain[n.row+1][n.col+1])+(calculateHeuristic(n.row+1,n.col+1,targetRow,targetCol)),n.row+1,n.col+1));
					presentInQueue[n.row+1][n.col+1] = true;
				}
				
				
			if(isValidEdge(n.row+1 ,n.col,n.value) == true && visited[n.row+1][n.col] == false)
				if(presentInQueue[n.row+1][n.col] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row+1 && k.col == n.col)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10+Math.abs(n.value - terrain[n.row+1][n.col])+(calculateHeuristic(n.row+1,n.col,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row+1,n.col),terrain[n.row+1][n.col],n,n.pathCost+10+Math.abs(n.value - terrain[n.row+1][n.col])+(calculateHeuristic(n.row+1,n.col,targetRow,targetCol)),n.row+1,n.col));
					presentInQueue[n.row+1][n.col] = true;
				}
				
			if(isValidEdge(n.row+1 ,n.col-1,n.value) == true && visited[n.row+1][n.col-1] == false)
				if(presentInQueue[n.row+1][n.col-1] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row+1 && k.col == n.col-1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+14+Math.abs(n.value - terrain[n.row+1][n.col-1])+(calculateHeuristic(n.row+1,n.col-1,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row+1,n.col-1),terrain[n.row+1][n.col-1],n,n.pathCost+14+Math.abs(n.value - terrain[n.row+1][n.col-1])+(calculateHeuristic(n.row+1,n.col-1,targetRow,targetCol)),n.row+1,n.col-1));
					presentInQueue[n.row+1][n.col-1] = true;
				}
				
			if(isValidEdge(n.row ,n.col-1,n.value) == true && visited[n.row][n.col-1] == false)
				if(presentInQueue[n.row][n.col-1] == true)
				{
					for(Node k : aStarQ)
					{
						if(k.row == n.row && k.col == n.col-1)
						{
								k.pathCost = min(k.pathCost,n.pathCost+10+Math.abs(n.value - terrain[n.row][n.col-1])+(calculateHeuristic(n.row,n.col-1,targetRow,targetCol)));
								break;
						}
					}
				}
				else
				{
					aStarQ.add(new Node(setGoalValue(n.row,n.col-1),terrain[n.row][n.col-1],n,n.pathCost+10+Math.abs(n.value - terrain[n.row][n.col-1])+(calculateHeuristic(n.row,n.col-1,targetRow,targetCol)),n.row,n.col-1));		
					presentInQueue[n.row][n.col-1] = true;
				}
			
		}
		
		path = new StringBuilder("FAIL\n");
		return path;
	}

}
