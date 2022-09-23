import java.util.List;
import processing.core.PImage;
import java.util.Optional;

class WitchNotFull extends MovableEntity
{
    private int appleLimit;
    private int appleCount;

    public WitchNotFull(String id, int appleLimit, int appleCount, Point position, int actionPeriod, int animationPeriod,
                     List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.appleLimit = appleLimit;
        this.appleCount = appleCount;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.position,
                BadApple.class);

        if (!notFullTarget.isPresent() ||
                !this.moveTo(world, notFullTarget.get(), scheduler) ||
                !this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public boolean transform(WorldModel world,
                             EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.appleCount >= this.appleLimit)
        {
            WitchFull witch = new WitchFull(this.id, this.appleLimit,
                    this.position, this.actionPeriod, this.animationPeriod,
                    this.images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(witch);
            witch.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (world.adjacent(this.position, target.getPosition()))
        {
            this.appleCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos))
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