class Node implements Comparable
{
	private Point point;
	private Node parent;
	private double fScore;

	public Node(Point point, Node parent, double fScore)
	{
		this.point = point;
		this.parent = parent;
		this.fScore = fScore;
	}

	public Point getPoint(){return point;}

	public Node getParent(){return parent;}

	public double getFScore(){return fScore;}

	public int compareTo(Object o)
	{
		if(getClass() == o.getClass())
		{
			Node n = (Node)o;
			if (this.fScore > n.getFScore()){return 1;}
			if (this.fScore < n.getFScore()){return -1;}
		}
		return 0;
	}
}