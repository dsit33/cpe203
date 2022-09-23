import java.util.List;
import processing.core.PImage;
import java.util.Random;
import java.util.Optional;
class AppleTree extends ActiveEntity
{
    private final String APPLE_KEY = "apple";

    public AppleTree(String id, Point position, int actionPeriod, List<PImage> images)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(position);

        if (openPt.isPresent())
        {
            BadApple apple = new BadApple(id, openPt.get(),
                    imageStore.getImageList(APPLE_KEY));
            world.addEntity(apple);
        }

        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                actionPeriod);

    }


}