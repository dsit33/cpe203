import processing.core.PImage;
import java.util.List;
import java.util.Random;
abstract class ActiveEntity extends Entity
{
	protected int actionPeriod;
	protected Random rand = new Random();
	public ActiveEntity(String id, Point position, List<PImage> images, int actionPeriod)
	{
		super(id, position, images);
		this.actionPeriod = actionPeriod;
	}
	void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
	{
		scheduler.scheduleEvent(this,
				new Activity(this, world, imageStore),
				actionPeriod);
	}
	abstract void executeActivity(WorldModel worldModel, ImageStore imageStore, EventScheduler scheduler);

	public int getActionPeriod(){return actionPeriod;}
}