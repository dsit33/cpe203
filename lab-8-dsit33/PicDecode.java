import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.stream.*;

public class PicDecode
{
  private static final int X_COOR = 0;
  private static final int Y_COOR = 1;
  private static final int Z_COOR = 2;

	private static void processFile(String fileName, List<Point> points) throws FileNotFoundException
  {
  	File file = new File(fileName);
    if (!file.exists()){throw new FileNotFoundException();}
    Scanner s = new Scanner(file);
    while (s.hasNextLine())
    {
      processLine(s.nextLine(), points);
    }
  }

  private static void processLine(String line, List<Point> points)
  {
    String[] coor = line.split(",");
    Point pt = new Point(Double.parseDouble(coor[X_COOR]), Double.parseDouble(coor[Y_COOR]), 
      Double.parseDouble(coor[Z_COOR]));
    points.add(pt);
  }



  public static void main(String[] args) throws IOException
  {
    String newFile = "drawMe.txt";
    List<Point> points = new LinkedList<>();
    try
    {
      processFile(args[0], points);
      File file = new File(newFile);
      if (!file.exists()){file.createNewFile();}
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      List<Point> ptStream = points.stream().filter(p -> p.getZ() <= 2).map(p -> p.scale(.5)).map(p -> p.translate(-157,-37)).collect(Collectors.toList());
      for (Point pt : ptStream)
      {
        String newLine = String.format("%f, %f, %f\n",pt.getX(), pt.getY(), pt.getZ());
        bw.write(newLine);
      }
      bw.close();
    }
    catch(FileNotFoundException e){e.getMessage();}
    catch(IOException ioe){ioe.getMessage();}
  }
}