import java.util.List;
import processing.core.PImage;
import java.util.Optional;

class OreBlob extends MovableEntity
{
    private final String QUAKE_KEY = "quake";
    private final String QUAKE_ID = "quake";
    private final int QUAKE_ACTION_PERIOD = 1100;
    private final int QUAKE_ANIMATION_PERIOD = 100;

	public OreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
      super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
      Optional<Entity> blobTarget = world.findNearest(
         position, Vein.class);
      long nextPeriod = actionPeriod;

      if (blobTarget.isPresent())
      {
         Point tgtPos = blobTarget.get().getPosition();

         if (this.moveTo(world, blobTarget.get(), scheduler))
         {
            AnimatedEntity quake = new Quake(QUAKE_ID, tgtPos,
               imageStore.getImageList(QUAKE_KEY), QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);

            world.addEntity(quake);
            nextPeriod += actionPeriod;
            quake.scheduleActions(scheduler, world, imageStore);
         }
      }

      scheduler.scheduleEvent(this,
         new Activity(this, world, imageStore),
         nextPeriod);
   }

   public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
   {
      if (world.adjacent(position, target.getPosition()))
      {
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);
         return true;
      }
      else
      {
         Point nextPos = this.nextPosition(world, target.getPosition());

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }


}