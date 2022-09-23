import java.util.List;
import processing.core.PImage;
import java.util.Random;
import java.util.Optional;
class Vein extends ActiveEntity
{
    private final String ORE_KEY = "ore";
    private final String ORE_ID_PREFIX = "ore -- ";
    private final int ORE_CORRUPT_MIN = 5000;
    private final int ORE_CORRUPT_MAX = 6000;


    public Vein(String id, Point position, int actionPeriod, List<PImage> images)
    {
    	super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
      Optional<Point> openPt = world.findOpenAround(position);

      if (openPt.isPresent())
      {
         Ore ore = new Ore(ORE_ID_PREFIX + id, openPt.get(), ORE_CORRUPT_MIN +
               rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN), imageStore.getImageList(ORE_KEY));
         world.addEntity(ore);
         ore.scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this,
         new Activity(this, world, imageStore),
         actionPeriod);

    }


}