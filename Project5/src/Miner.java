import java.util.List;
import processing.core.PImage;

abstract class Miner extends MovableEntity
{
    protected int resourceLimit;

    public Miner(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod,
                 List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }
}