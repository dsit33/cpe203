import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Point;
class Circle implements Shape{
	private double radius;
	private Point center;
	private Color color;

	public Circle(double radius, Point center, Color color){
		this.radius = radius;
		this.center = center;
		this.color = color;
	}

	public Color getColor(){
		return this.color;
	}

	public void setColor(Color c){
		this.color = c;
	}

	public double getArea(){
		return Math.PI*Math.pow(this.radius,2);
	}

	public double getPerimeter(){
		return 2*Math.PI*this.radius;
	}

	public void translate(Point p){
		this.center.x += p.x;
		this.center.y += p.y;
	}

	public double getRadius(){
		return this.radius;
	}

	public void setRadius(double r){
		this.radius = r;
	}

	public Point getCenter(){
		return this.center;
	}

	public boolean equals(Object other){
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		if (this.radius == ((Circle)other).getRadius() && this.center.x == ((Circle)other).getCenter().x && this.center.y == ((Circle)other).getCenter().y && this.color == ((Circle)other).getColor()){
			return true;
		}
		else {return false;}
	}

}