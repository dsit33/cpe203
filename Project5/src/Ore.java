import java.util.List;
import processing.core.PImage;
import java.util.Random;

class Ore extends ActiveEntity
{
	private final String BLOB_KEY = "blob";
    private final String BLOB_ID_SUFFIX = " -- blob";
    private final int BLOB_PERIOD_SCALE = 4;
    private final int BLOB_ANIMATION_MIN = 50;
    private final int BLOB_ANIMATION_MAX = 150;

    public Ore(String id, Point position, int actionPeriod, List<PImage> images)
    {
    	super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
      Point pos = position;  // store current position before removing

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      OreBlob blob = new OreBlob(id + BLOB_ID_SUFFIX,
         pos, actionPeriod / BLOB_PERIOD_SCALE,
         BLOB_ANIMATION_MIN +
            rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
         imageStore.getImageList(BLOB_KEY));

      world.addEntity(blob);
      blob.scheduleActions(scheduler, world, imageStore);
   }
}