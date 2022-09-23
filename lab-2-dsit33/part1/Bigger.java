class Bigger{

	public static double whichIsBigger(Circle circle, Rectangle rect, Polygon poly){
		double circPerim = Util.perimeter(circle);
		double rectPerim = Util.perimeter(rect);
		double polyPerim = Util.perimeter(poly);

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