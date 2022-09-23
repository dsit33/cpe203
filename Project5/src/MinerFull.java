import java.util.List;
import processing.core.PImage;
import java.util.Optional;


class MinerFull extends Miner
{
	public MinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod,
      List<PImage> images)
	{
		super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
	}

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
      Optional<Entity> fullTarget = world.findNearest(position,
         Blacksmith.class);

      if (fullTarget.isPresent() &&
         this.moveTo(world, fullTarget.get(), scheduler))
      {
         this.transform(world, scheduler, imageStore);
      }
      else
      {
         scheduler.scheduleEvent(this,
            new Activity(this, world, imageStore),
            actionPeriod);
      }
    }

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
      Miner miner = new MinerNotFull(id, resourceLimit, 0,
         position, actionPeriod, animationPeriod, images);

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      world.addEntity(miner);
      miner.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
      if (world.adjacent(position, target.getPosition()))
      {
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