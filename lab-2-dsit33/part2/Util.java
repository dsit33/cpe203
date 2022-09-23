class Util{
	public static double perimeter(Circle circle){
		return 2*Math.PI*circle.getRadius();
	}

	public static double perimeter(Rectangle rect){
		double longSide = rect.getBottomRight().getX() - rect.getTopLeft().getX();
		double shortSide = rect.getTopLeft().getY() - rect.getBottomRight().getY();

		return 2*(longSide + shortSide);
	}

	public static double perimeter(Polygon poly){
		double perim = 0;
		Point anchor = poly.getPoints().get(poly.getPoints().size()-1);
		for (Point point : poly.getPoints()){
			perim += distance(anchor, point);
			anchor = point;
		}
		
		/*int perim = 0;
		for (int i = 0; i <= poly.getPoints().size() - 2; i++){
			perim += distance(poly.getPoints().get(i), poly.getPoints().get(i+1));
		}
		perim += distance(poly.getPoints().get(poly.getPoints().size()-1), poly.getPoints().get(0));*/

		return perim;
	}

	private static double distance(Point p1, Point p2){
		double d = Math.hypot(p1.getX()-p2.getX(), p1.getY()-p2.getY());
		/*double calcX = Math.pow(p1.getX() - p2.getX(), 2);
		double calcY = Math.pow(p1.getY() - p2.getY(), 2);

		return Math.sqrt(calcX + calcY);*/
		return d;
	}
}