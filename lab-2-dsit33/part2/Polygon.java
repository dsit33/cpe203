import java.util.List;
class Polygon{
	private List<Point> points;

	public Polygon(List<Point> points){
		this.points = points;
	}

	public List<Point> getPoints(){
		return this.points;
	}

	public double perimeter(){
		double perim = 0;
		Point anchor = this.getPoints().get(this.getPoints().size()-1);
		for (Point point : this.getPoints()){
			perim += distance(anchor, point);
			anchor = point;
		}
		
		return perim;
	}

	private double distance(Point p1, Point p2){
		double d = Math.hypot(p1.getX()-p2.getX(), p1.getY()-p2.getY());
		return d;
	}
}