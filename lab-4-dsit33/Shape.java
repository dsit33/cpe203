import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.lang.Math;

interface Shape{
	
	Color getColor();

	void setColor(Color c);

	double getArea();

	double getPerimeter();

	void translate(Point p);
}