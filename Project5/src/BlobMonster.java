import java.util.ArrayList;
import java.util.List;
import processing.core.PImage;

import java.util.Optional;

class BlobMonster extends MovableEntity
{

    public BlobMonster(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        List<Entity> targets = new ArrayList<>();
        if(world.findNearest(position, MinerFull.class).isPresent())
        {
            Entity monsterTarget1 = world.findNearest(position, MinerFull.class).get();
            targets.add(monsterTarget1);
        }
        if(world.findNearest(position, MinerNotFull.class).isPresent())
        {
            Entity monsterTarget2 = world.findNearest(
                position, MinerNotFull.class).get();
            targets.add(monsterTarget2);
        }

        Optional<Entity> finalTarget = world.nearestEntity(targets, position);

        if (finalTarget.isPresent())
        {
            if (this.moveTo(world, finalTarget.get(), scheduler))
            {world.removeEntity(finalTarget.get());}
        }

        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                actionPeriod);
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