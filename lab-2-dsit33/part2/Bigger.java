class Bigger{

	public static double whichIsBigger(Circle circle, Rectangle rect, Polygon poly){
		double circPerim = circle.perimeter();
		double rectPerim = rect.perimeter();
		double polyPerim = poly.perimeter();

		if (circPerim > rectPerim && circPerim > polyPerim){
			return circPerim;
		}

		if (rectPerim > circPerim && rectPerim > polyPerim){
			return rectPerim;
		}

		if (polyPerim > circPerim && polyPerim > rectPerim){
			return polyPerim;
		}

		return circPerim;
	}
}