import java.util.ArrayList;

public class BFS {
	
	int totalRows;
	int totalCols;
	int elevationLimit;
	int[][] terrain;
	int targetRow, targetCol;
	int landingCol, landingRow;
	boolean[][] visited;
	
	public BFS(int landingRow, int landingCol, int targetRow, int targetCol, int [][] terrain, int elevationLimit, int totalRows, int totalCols)
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
	
	public StringBuilder startBFS()
	{
		ArrayList<Node> bfsQ = new ArrayList<Node>();
		StringBuilder path;
		
		Node root = new Node(2,terrain[landingRow][landingCol],null,0,landingRow,landingCol);
		visited[landingRow][landingCol] = true;
		bfsQ.add(root);
		
		//start exploring all 8 nodes.
		while(bfsQ.isEmpty() == false)
		{
			Node n = bfsQ.get(0);
			bfsQ.remove(0);
			
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
			
			
			if(isValidEdge(n.row-1 ,n.col-1,n.value) == true && visited[n.row-1][n.col-1] == false)
			{
				visited[n.row-1][n.col-1] = true;
				bfsQ.add(new Node(setGoalValue(n.row-1,n.col-1),terrain[n.row-1][n.col-1],n,n.pathCost+1,n.row-1,n.col-1));
			}
				
			if(isValidEdge(n.row-1 ,n.col,n.value) == true && visited[n.row-1][n.col] == false)
			{
				visited[n.row-1][n.col] = true;
				bfsQ.add(new Node(setGoalValue(n.row-1,n.col),terrain[n.row-1][n.col],n,n.pathCost+1,n.row-1,n.col));
			}
			
			if(isValidEdge(n.row-1 ,n.col+1,n.value) == true && visited[n.row-1][n.col+1] == false)
			{
				visited[n.row-1][n.col+1] = true;
				bfsQ.add(new Node(setGoalValue(n.row-1,n.col+1),terrain[n.row-1][n.col+1],n,n.pathCost+1,n.row-1,n.col+1));
			}
				
			if(isValidEdge(n.row ,n.col+1,n.value) == true && visited[n.row][n.col+1] == false)
			{
				visited[n.row][n.col+1] = true;
				bfsQ.add(new Node(setGoalValue(n.row,n.col+1),terrain[n.row][n.col+1],n,n.pathCost+1,n.row,n.col+1));
			}
			
			if(isValidEdge(n.row+1 ,n.col+1,n.value) == true && visited[n.row+1][n.col+1] == false)
			{
				visited[n.row+1][n.col+1] = true;
				bfsQ.add(new Node(setGoalValue(n.row+1,n.col+1),terrain[n.row+1][n.col+1],n,n.pathCost+1,n.row+1,n.col+1));
				
			}
			
			if(isValidEdge(n.row+1 ,n.col,n.value) == true && visited[n.row+1][n.col] == false)
			{
				visited[n.row+1][n.col] = true;
				bfsQ.add(new Node(setGoalValue(n.row+1,n.col),terrain[n.row+1][n.col],n,n.pathCost+1,n.row+1,n.col));
			}
				
			if(isValidEdge(n.row+1 ,n.col-1,n.value) == true && visited[n.row+1][n.col-1] == false)
			{
				visited[n.row+1][n.col-1] = true;
				bfsQ.add(new Node(setGoalValue(n.row+1,n.col-1),terrain[n.row+1][n.col-1],n,n.pathCost+1,n.row+1,n.col-1));
			}
				
			if(isValidEdge(n.row ,n.col-1,n.value) == true && visited[n.row][n.col-1] == false)
			{
				visited[n.row][n.col-1] = true;
				bfsQ.add(new Node(setGoalValue(n.row,n.col-1),terrain[n.row][n.col-1],n,n.pathCost+1,n.row,n.col-1));
				
			}
		}
		
		path = new StringBuilder("FAIL\n");
		return path;
	}

}
