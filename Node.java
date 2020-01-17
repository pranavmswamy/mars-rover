public class Node {
	public int pathCost;
	public Node parent;
	public int state;
	public int value;
	public int row, col;
	
	public Node(int state, int value, Node parent, int pathCost, int row, int col)
	{
		this.parent = parent;
		this.pathCost = pathCost;
		this.value = value;
		this.state = state;
		this.row = row;
		this.col = col;
	}	
}
