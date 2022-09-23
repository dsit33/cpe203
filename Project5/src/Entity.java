import java.util.List;
import processing.core.PImage;
abstract class Entity
{
	protected Point position;
	protected List<PImage> images;
	protected int imageIndex;
	protected String id;

	public Entity(String id, Point position, List<PImage> images)
	{
		this.id = id;
		this.position = position;
		this.images = images;
	}

	Point getPosition()
	{return position; }
	void setPosition(Point p)
	{position = p; }
	PImage getCurrentImage()
	{return images.get(imageIndex);}
}