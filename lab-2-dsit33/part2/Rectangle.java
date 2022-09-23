class Rectangle{
	private Point topLeft;
	private Point bottomRight;

	public Rectangle(Point topLeft, Point bottomRight){
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public Point getTopLeft(){
		return this.topLeft;
	}

	public Point getBottomRight(){
		return this.bottomRight;
	}

	public double perimeter(){
		double longSide = this.getBottomRight().getX() - this.getTopLeft().getX();
		double shortSide = this.getTopLeft().getY() - this.getBottomRight().getY();

		return 2*(longSide + shortSide);
	}
}