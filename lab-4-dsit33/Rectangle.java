import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Point;
class Rectangle implements Shape{
	private double width;
	private double height;
	private Point topLeft;
	private Color color;

	public Rectangle(double width, double height, Point topLeft, Color color){
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
		this.color = color;
	}

	public double getWidth(){
		return this.width;
	}

	public void setWidth(double width){
		this.width = width;
	}

	public double getHeight(){
		return this.height;
	}

	public void setHeight(double height){
		this.height = height;
	}

	public Point getTopLeft(){
		return this.topLeft;
	}

	public boolean equals(Object other){
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		if (this.width == ((Rectangle)other).getWidth() && this.height == ((Rectangle)other).getHeight() && this.topLeft.x == ((Rectangle)other).getTopLeft().x && this.topLeft.y == ((Rectangle)other).getTopLeft().y && this.color == ((Rectangle)other).getColor()){
			return true;
		}
		else {return false;}
	}

	public Color getColor(){
		return this.color;
	}

	public void setColor(Color c){
		this.color = c;
	}

	public double getArea(){
		return this.width*this.height;
	}

	public double getPerimeter(){
		return 2*this.width + 2*this.height;
	}

	public void translate(Point p){
		this.topLeft.x += p.x;
		this.topLeft.y += p.y;
	}




}