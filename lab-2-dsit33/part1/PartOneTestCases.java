import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartOneTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircle(){
      Circle circle = new Circle(new Point(1.0, 3.0), 5.0);
      assertTrue(new Point(1.0, 3.0).equals(circle.getCenter()));
      assertEquals(5.0, circle.getRadius(), DELTA);
   }

   @Test
   public void testRectangle(){
      Rectangle rect = new Rectangle(new Point(0.0, 7.0), new Point(10.0, 0.0));
      assertTrue(new Point(0.0, 7.0).equals(rect.getTopLeft()));
      assertTrue(new Point(10.0, 0.0).equals(rect.getBottomRight()));
   }

   @Test 
   public void testPolygon(){
      List<Point> ptSet = new ArrayList<Point>();
      ptSet.add(new Point(0.0, 0.0));
      ptSet.add(new Point(0.0, 5.0));
      ptSet.add(new Point(5.0, 5.0));
      ptSet.add(new Point(5.0, 0.0));
      Polygon poly = new Polygon(ptSet);

      assertTrue(ptSet.equals(poly.getPoints()));
   }

   @Test
   public void testPerimeter(){
      Circle c = new Circle(new Point(0.0, 0.0), 3);
      Rectangle r = new Rectangle(new Point(0.0, 5.0), new Point(3.0, 0.0));
      List<Point> ptSet = new ArrayList<Point>();
      ptSet.add(new Point(0.0, 0.0));
      ptSet.add(new Point(0.0, 5.0));
      ptSet.add(new Point(5.0, 5.0));
      ptSet.add(new Point(5.0, 0.0));
      Polygon p = new Polygon(ptSet);

      assertEquals(6*Math.PI, Util.perimeter(c), DELTA);
      assertEquals(16.0, Util.perimeter(r), DELTA);
      assertEquals(20.0, Util.perimeter(p), DELTA);
   }

   @Test
   public void testPolyPerimeter(){
      List<Point> ptSet = new ArrayList<Point>();
      ptSet.add(new Point(0.0, 0.0));
      ptSet.add(new Point(0.0, 4.0));
      ptSet.add(new Point(3.0, 4.0));
      Polygon p = new Polygon(ptSet);

      assertEquals(Util.perimeter(p), 12.0, DELTA);
   }

   @Test
   public void testPolyPerimeter2(){
      List<Point> ptSet = new ArrayList<Point>();
      ptSet.add(new Point(1.0, 5.0));
      ptSet.add(new Point(3.0, 3.0));
      ptSet.add(new Point(2.0, -1.0));
      ptSet.add(new Point(-3.0, -1.0));
      Polygon p = new Polygon(ptSet);

      assertEquals(Util.perimeter(p), 19.1626353, DELTA);
   }

   @Test
   public void testBigger(){
      Circle c = new Circle(new Point(1.0, 1.0), 2.0);
      Rectangle r = new Rectangle(new Point(-1.0, 2.0), new Point(1.0, -1.6));
      List<Point> ptSet = new ArrayList<Point>();
      ptSet.add(new Point(0.0, 0.0));
      ptSet.add(new Point(3.0, 1.0));
      ptSet.add(new Point(1.0, 4.0));
      ptSet.add(new Point(-1.0, 4.0));
      Polygon p = new Polygon(ptSet);

      assertEquals(Util.perimeter(p), Bigger.whichIsBigger(c, r, p), DELTA);
   }

   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getCenter", "getRadius");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getTopLeft", "getBottomRight");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getPoints");

      final List<Class> expectedMethodReturns = Arrays.asList(
         List.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[][] {new Class[0]});

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testUtilImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "perimeter", "perimeter", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[] {Circle.class},
         new Class[] {Polygon.class},
         new Class[] {Rectangle.class});

      verifyImplSpecifics(Util.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, Point.class.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
