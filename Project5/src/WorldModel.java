import java.util.*;

import processing.core.PImage;

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   private final int ORE_REACH = 1;

   private final int PROPERTY_KEY = 0;

   private final String BGND_KEY = "background";
   private final int BGND_NUM_PROPERTIES = 4;
   private final int BGND_ID = 1;
   private final int BGND_COL = 2;
   private final int BGND_ROW = 3;

   private final String MINER_KEY = "miner";
   private final int MINER_NUM_PROPERTIES = 7;
   private final int MINER_ID = 1;
   private final int MINER_COL = 2;
   private final int MINER_ROW = 3;
   private final int MINER_LIMIT = 4;
   private final int MINER_ACTION_PERIOD = 5;
   private final int MINER_ANIMATION_PERIOD = 6;

   private final String OBSTACLE_KEY = "obstacle";
   private final int OBSTACLE_NUM_PROPERTIES = 4;
   private final int OBSTACLE_ID = 1;
   private final int OBSTACLE_COL = 2;
   private final int OBSTACLE_ROW = 3;

   private final String ORE_KEY = "ore";
   private final int ORE_NUM_PROPERTIES = 5;
   private final int ORE_ID = 1;
   private final int ORE_COL = 2;
   private final int ORE_ROW = 3;
   private final int ORE_ACTION_PERIOD = 4;

   private final String SMITH_KEY = "blacksmith";
   private final int SMITH_NUM_PROPERTIES = 4;
   private final int SMITH_ID = 1;
   private final int SMITH_COL = 2;
   private final int SMITH_ROW = 3;

   private final String VEIN_KEY = "vein";
   private final int VEIN_NUM_PROPERTIES = 5;
   private final int VEIN_ID = 1;
   private final int VEIN_COL = 2;
   private final int VEIN_ROW = 3;
   private final int VEIN_ACTION_PERIOD = 4;

   private final String TREE_KEY = "tree";
   private final int TREE_ACTION_PERIOD = 17000;

   private final String WITCH_KEY = "witch";
   private final int WITCH_LIMIT = 7;
   private final int WITCH_ACTION_PERIOD = 700;
   private final int WITCH_ANIMATION_PERIOD = 80;

   private final String CAULDRON_KEY = "cauldron";
   private final int CHECKED_ALL = 16;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public Set<Entity> getEntities()
   {return entities; }

   public int getNumRows()
   {return numRows; }

   public int getNumCols()
   {return numCols; }

   public boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
   }

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
      {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (this.withinBounds(newPt) &&
               !this.isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void tryAddEntity(Entity entity)
   {
      if (this.isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }
      entities.add(entity);
      this.addEntity(entity);
   }

   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
         pos.x >= 0 && pos.x < this.numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return this.withinBounds(pos) &&
         this.getOccupancyCell(pos) != null;
   }

   public void mousePressed(Point pressed, ImageStore imageStore, EventScheduler scheduler)
   {
      int count = 0;
      List<Point> visited = new ArrayList<>();

      while(count < 6)
      {
         if (visited.size() >= CHECKED_ALL) {return ;}
         int dx = (int)(Math.random()*7);
         int dy = (int)(Math.random()*7);
         Point trySpot = new Point(Math.abs(pressed.x - dx), Math.abs(pressed.y - dy));

         if(!visited.contains(trySpot))
         {
            if(!isOccupied(trySpot))
            {
               if(count < 3)
               {
                  AppleTree tree = new AppleTree("", trySpot, TREE_ACTION_PERIOD,
                          imageStore.getImageList(TREE_KEY));
                  tryAddEntity(tree);
                  tree.scheduleActions(scheduler, this, imageStore);
                  count++;
               }
               else if(count < 5)
               {
                  tryAddEntity(new Cauldron("", trySpot,
                          imageStore.getImageList(CAULDRON_KEY)));
                  count++;
               }
               else
               {
                  WitchNotFull witch = new WitchNotFull("", WITCH_LIMIT, 0, trySpot,
                          WITCH_ACTION_PERIOD, WITCH_ANIMATION_PERIOD, imageStore.getImageList(WITCH_KEY));
                  tryAddEntity(witch);
                  witch.scheduleActions(scheduler, this, imageStore);
                  count++;
               }
            }
            visited.add(trySpot);
         }

      }

   }

   public Optional<Entity> nearestEntity(List<Entity> entities,
      Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = distanceSquared(nearest.getPosition(), pos);

         for (Entity other : entities)
         {
            int otherDistance = distanceSquared(other.getPosition(), pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   private int distanceSquared(Point p1, Point p2)
   {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public Optional<Entity> findNearest(Point pos,
      Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (entity.getClass() == kind)
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   /*
      Assumes that there is no entity currently occupying the
      intended destination cell.
   */
   public void addEntity(Entity entity)
   {
      if (this.withinBounds(entity.getPosition()))
      {
         this.setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (this.withinBounds(pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
         && this.getOccupancyCell(pos) != null)
      {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.entities.remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos,
      Background background)
   {
      if (this.withinBounds(pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (this.isOccupied(pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Entity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos,
      Entity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }

   public Background getBackgroundCell(Point pos)
   {
      return this.background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos,
      Background background)
   {
      this.background[pos.y][pos.x] = background;
   }

   public boolean processLine(String line,
      ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
         case BGND_KEY:
            return parseBackground(properties, imageStore);
         case MINER_KEY:
            return parseMiner(properties, imageStore);
         case OBSTACLE_KEY:
            return parseObstacle(properties, imageStore);
         case ORE_KEY:
            return parseOre(properties, imageStore);
         case SMITH_KEY:
            return parseSmith(properties, imageStore);
         case VEIN_KEY:
            return parseVein(properties, imageStore);
         }
      }

      return false;
   }


   public boolean parseBackground(String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
            Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         setBackground(pt,
            new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseMiner(String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == MINER_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
            Integer.parseInt(properties[MINER_ROW]));
         Entity entity = new MinerNotFull(properties[MINER_ID],
            Integer.parseInt(properties[MINER_LIMIT]), 0,
            pt,
            Integer.parseInt(properties[MINER_ACTION_PERIOD]),
            Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
            imageStore.getImageList(MINER_KEY));
         tryAddEntity(entity);
      }

      return properties.length == MINER_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
            Integer.parseInt(properties[OBSTACLE_COL]),
            Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = new Obstacle(properties[OBSTACLE_ID],
            pt, imageStore.getImageList(OBSTACLE_KEY));
         tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseOre(String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == ORE_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
            Integer.parseInt(properties[ORE_ROW]));
         Entity entity = new Ore(properties[ORE_ID],
            pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
            imageStore.getImageList(ORE_KEY));
         tryAddEntity(entity);
      }

      return properties.length == ORE_NUM_PROPERTIES;
   }

   public boolean parseSmith(String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == SMITH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
            Integer.parseInt(properties[SMITH_ROW]));
         Entity entity = new Blacksmith(properties[SMITH_ID],
            pt, imageStore.getImageList(SMITH_KEY));
         tryAddEntity(entity);
      }

      return properties.length == SMITH_NUM_PROPERTIES;
   }

   public boolean parseVein(String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == VEIN_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
            Integer.parseInt(properties[VEIN_ROW]));
         Entity entity = new Vein(properties[VEIN_ID],
            pt,
            Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
            imageStore.getImageList(VEIN_KEY));
         tryAddEntity(entity);
      }

      return properties.length == VEIN_NUM_PROPERTIES;
   }

   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!this.processLine(in.nextLine(),imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                  lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
               lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
               lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }
}
