import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Point;
import java.lang.Object;
class Triangle implements Shape{
	private Point a;
	private Point b;
	private Point c;
	private Color color;

	public Triangle(Point a, Point b, Point c, Color color){
		this.a = a;
		this.b = b;
		this.c = c;
		this.color = color;
	}

	public Point getVertexA(){
		return this.a;
	}

	public Point getVertexB(){
		return this.b;
	}

	public Point getVertexC(){
		return this.c;
	}

	public boolean equals(Object other){
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		if(this.a.x == ((Triangle)other).getVertexA().x && this.a.y == ((Triangle)other).getVertexA().y && this.b.x == ((Triangle)other).getVertexB().x && this.b.y == ((Triangle)other).getVertexB().y && this.c.x == ((Triangle)other).getVertexC().x && this.c.y == ((Triangle)other).getVertexC().y){
			return true;
		}
		else {return false;}
	}

	public Color getColor(){
		return this.color;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public double getArea(){
		return Math.abs((this.a.x*(this.b.y-this.c.y) + this.b.x*(this.c.y-this.a.y) + this.c.x*(this.a.y-this.b.y))/2);
	}

	public double getPerimeter(){
		double total = 0;
		total += distance(this.a, this.b);
		total += distance(this.b, this.c);
		total += distance(this.c, this.a);

		return total;
	}

	private double distance(Point p1, Point p2){
		double distanceSquared = Math.pow((p1.x - p2.x),2) + Math.pow((p1.y - p2.y), 2);
		double distance = Math.sqrt(distanceSquared);

		return distance;
	}

	public void translate(Point p){
		this.a.x += p.x;
		this.a.y += p.y;

		this.b.x += p.x;
		this.b.y += p.y;

		this.c.x += p.x;
		this.c.y += p.y;
	}

}