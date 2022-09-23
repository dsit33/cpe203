import java.util.List;
import processing.core.PImage;
class Quake extends AnimatedEntity
{
	private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

	public Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
	{
		super(id, position, images, actionPeriod, animationPeriod);
	}

   public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
   {
   	scheduler.scheduleEvent(this,
        new Activity(this, world, imageStore),
        this.actionPeriod);
	   scheduler.scheduleEvent(this,
			   new Animation(this, QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
   }
}