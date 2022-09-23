import java.util.List;
import processing.core.PImage;
import java.util.Optional;

class PotionWitch extends MovableEntity
{
    private int appleLimit;
    private final String MONSTER_ID = "blobMonster";
    private final int MONSTER_ACTION_PERIOD = 700;
    private final int MONSTER_ANIMATION_PERIOD = 700;

    public PotionWitch(String id, int appleLimit, Point position, int actionPeriod, int animationPeriod,
                     List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.appleLimit = appleLimit;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position,
                OreBlob.class);

        if (fullTarget.isPresent() &&
                this.moveTo(world, fullTarget.get(), scheduler))
        {
            OreBlob blob = (OreBlob)fullTarget.get();
            this.transform(world, scheduler, imageStore);

            BlobMonster monster = new BlobMonster(MONSTER_ID, blob.getPosition(), MONSTER_ACTION_PERIOD,
                    MONSTER_ANIMATION_PERIOD, imageStore.getImageList(MONSTER_ID));

            world.removeEntity(blob);
            scheduler.unscheduleAllEvents(blob);

            world.addEntity(monster);
            monster.scheduleActions(scheduler, world, imageStore);
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
        WitchNotFull witchNF = new WitchNotFull(this.id, this.appleLimit, 0,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(witchNF);
        witchNF.scheduleActions(scheduler, world, imageStore);
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