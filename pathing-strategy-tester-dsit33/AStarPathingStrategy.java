import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{
   private double hCost(Point pt1, Point pt2)
   {
      double dSquared = Math.pow(pt2.x - pt1.x, 2) + Math.pow(pt2.y - pt1.y, 2);
      return Math.sqrt(dSquared);
   }

   public List<Point> computePath(Point start, Point end,
                                  Predicate<Point> canPassThrough,
                                  BiPredicate<Point, Point> withinReach,
                                  Function<Point, Stream<Point>> potentialNeighbors)
   {
      /* Does not check withinReach.  Since only a single step is taken
       * on each call, the caller will need to check if the destination
       * has been reached.
       */
      List<Point> path = new ArrayList<>();
      List<Point> visited = new ArrayList<>();
      PriorityQueue<Node> queue = new PriorityQueue<>();


      visited.add(start);
      queue.add(new Node(start, null, 0));
      int gCost = 0;

      while(!queue.isEmpty())
      {
         Node current = queue.poll();
         if(withinReach.test(current.getPoint(), end))
         {
            path.add(current.getPoint());
            while(current.getParent().getPoint() != start)
            {
               path.add(current.getParent().getPoint());
               current = current.getParent();
            }
            Collections.reverse(path);
            return path;
         }
         List<Point> neighbors = potentialNeighbors.apply(current.getPoint()).collect(Collectors.toList());
         for(Point pt : neighbors)
         {
            if(!visited.contains(pt) && canPassThrough.test(pt))
            {
               visited.add(pt);
               queue.add(new Node(pt, current, gCost + hCost(pt, end)));
            }
         }
         gCost++;

      }
      return path;
   }
}