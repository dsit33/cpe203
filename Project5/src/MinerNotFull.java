import java.util.List;
import processing.core.PImage;
import java.util.Optional;

class MinerNotFull extends Miner
{
	private int resourceCount;

	public MinerNotFull(String id, int resourceLimit, int resourceCount, Point position, int actionPeriod, int animationPeriod,
      List<PImage> images)
	{
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
        this.resourceCount = resourceCount;
	}

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
      Optional<Entity> notFullTarget = world.findNearest(position,
         Ore.class);

      if (!notFullTarget.isPresent() ||
         !this.moveTo(world, notFullTarget.get(), scheduler) ||
         !this.transform(world, scheduler, imageStore))
      {
         scheduler.scheduleEvent(this,
            new Activity(this, world, imageStore),
            actionPeriod);
      }
    }

    public boolean transform(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
    {
      if (resourceCount >= resourceLimit)
      {
         Miner miner = new MinerFull(id, resourceLimit,
            position, actionPeriod, animationPeriod, images);

         world.removeEntity(this);
         scheduler.unscheduleAllEvents(this);

         world.addEntity(miner);
         miner.scheduleActions(scheduler, world, imageStore);

         return true;
      }

      return false;
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
      if (world.adjacent(position, target.getPosition()))
      {
         resourceCount += 1;
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