import java.util.List;
import processing.core.PImage;
import java.util.Optional;

class WitchFull extends MovableEntity
{
    private int appleLimit;

    public WitchFull(String id, int appleLimit, Point position, int actionPeriod, int animationPeriod,
                     List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.appleLimit = appleLimit;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position,
                Cauldron.class);

        if (fullTarget.isPresent() &&
                this.moveTo(world, fullTarget.get(), scheduler))
        {
            this.transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        PotionWitch pwitch = new PotionWitch(this.id, this.appleLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(pwitch);
        pwitch.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (world.adjacent(this.position, target.getPosition()))
        {
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