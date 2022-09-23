class Point{
	private double x;
	private double y;
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public double getRadius(){
		double distance = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
		return distance;
	}

	public double getAngle(){
		if (this.x < 0){
			if (this.y == 0.0) {return Math.PI;}
			else return -1*Math.asin(this.y/this.getRadius());
		}
		else return Math.asin(this.y/this.getRadius());
	}

	public Point rotate90(){
		if (this.x <= 0 && this.y <= 0){
			return new Point(-1*this.x, this.y);
		}
		if (this.x <= 0 && this.y >= 0){
			return new Point(this.x, -1*this.y);
		}
		if (this.x >= 0 && this.y >= 0){
			return new Point(-1*this.x, this.y);
		}
		if (this.x >= 0 && this.y <= 0){
			return new Point(this.x, -1*this.y);
		}
		return new Point(0, 0);
	}

	public boolean equals(Point other){
		if (this.x == other.x && this.y == other.y){
			return true;
		}
		return false;
	}
}