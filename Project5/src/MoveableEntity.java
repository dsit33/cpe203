import java.util.List;
import processing.core.PImage;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

abstract class MovableEntity extends AnimatedEntity
{
	public MovableEntity(String id, Point position, List<PImage> images,
		int actionPeriod, int animationPeriod)
	{
		super(id, position, images, actionPeriod, animationPeriod);
	}

	abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
	
	public Point nextPosition(WorldModel world, Point destPos)
    {
      AStarPathingStrategy a = new AStarPathingStrategy();
      Predicate<Point> canPassThrough = pt -> (world.withinBounds(pt) && !world.isOccupied(pt));
      BiPredicate<Point, Point> withinReach = (pt1, pt2) -> world.adjacent(pt1, pt2);
      Function<Point, Stream<Point>> potentialNeighbors = point -> a.CARDINAL_NEIGHBORS.apply(point).filter(canPassThrough);

      if(a.computePath(position, destPos, canPassThrough, withinReach, potentialNeighbors).isEmpty())
      {return position;}
      else
      {
          List<Point> path = a.computePath(position, destPos, canPassThrough, withinReach, potentialNeighbors);
          return path.get(0);
      }
    }
}