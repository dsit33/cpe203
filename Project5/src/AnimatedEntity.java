import java.util.List;
import processing.core.PImage;
abstract class AnimatedEntity extends ActiveEntity
{
	protected int animationPeriod;
	public AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
	{
		super(id, position, images, actionPeriod);
		this.animationPeriod = animationPeriod;
	}
	int getAnimationPeriod()
	{return animationPeriod; }
	void nextImage()
	{imageIndex = (imageIndex + 1) % images.size(); }

	void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
	{
		super.scheduleActions(scheduler, world, imageStore);
		scheduler.scheduleEvent(this, new Animation(this, 0),
				this.getAnimationPeriod());
	}
}