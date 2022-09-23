class Point{
	private double x;
	private double y;
	private double z;
	public Point(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public double getZ(){
		return this.z;
	}

	public Point scale(double percent)
	{
		return new Point(x*percent, y*percent, z*percent);
	}

	public Point translate(double xDelta, double yDelta)
	{
		return new Point(x + xDelta, y + yDelta, z);
	}

	public boolean equals(Object other){
		if (other == null){return false;}
		
		return this.x == ((Point)other).x && this.y == ((Point)other).y && this.z == ((Point)other).z;

	}
}