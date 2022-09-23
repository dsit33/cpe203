import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Point;
class WorkSpace{
	private List<Shape> shapes;

	public WorkSpace(){
		this.shapes = new LinkedList<Shape>();
	}

	public WorkSpace(List<Shape> shapes){
		this.shapes = shapes;
	}

	public void add(Shape shape){
		this.shapes.add(shape);
	}

	public Shape get(int index){
		return this.shapes.get(index);
	}

	public int size(){
		return this.shapes.size();
	}

	public List<Circle> getCircles(){
		List<Circle> circles = new LinkedList<>();
		for (Shape shape : this.shapes){
			if (shape instanceof Circle){
				circles.add((Circle)shape);
			}
		}
		return circles;
	}

	public List<Rectangle> getRectangles(){
		List<Rectangle> rectangles = new LinkedList<>();

		for (Shape shape : this.shapes){
			if (shape instanceof Rectangle){
				rectangles.add((Rectangle)shape);
			}
		}
		return rectangles;
	}

	public List<Triangle> getTriangles(){
		List<Triangle> triangles = new LinkedList<>();

		for (Shape shape : this.shapes){
			if (shape instanceof Triangle){
				triangles.add((Triangle)shape);
			}
		}
		return triangles;
	}

	public List<Shape> getShapesByColor(Color color){
		List<Shape> list = new LinkedList<>();
		for (Shape shape : this.shapes){
			if (shape.getColor() == color)
				list.add(shape);
		}

		return list;
	}

	public double getAreaOfAllShapes(){
		double area = 0;
		List<Circle> circles = this.getCircles();
		List<Rectangle> rectangles = this.getRectangles();
		List<Triangle> triangles = this.getTriangles();

		for (Circle circle : circles){
			area += circle.getArea();
		}

		for (Rectangle rectangle : rectangles){
			area += rectangle.getArea();
		}

		for (Triangle triangle : triangles){
			area += triangle.getArea();
		}
		return area;
	}

	public double getPerimeterOfAllShapes(){
		double perim = 0;
		List<Circle> circles = this.getCircles();
		List<Rectangle> rectangles = this.getRectangles();
		List<Triangle> triangles = this.getTriangles();

		for (Circle circle : circles){
			perim += circle.getPerimeter();
		}

		for (Rectangle rectangle : rectangles){
			perim += rectangle.getPerimeter();
		}

		for (Triangle triangle : triangles){
			perim += triangle.getPerimeter();
		}
		return perim;
	}





}