import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /* some sample tests but you must write more! see lab write up */

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(22.1, new Point(5, 4), Color.RED);

      assertEquals(101.2839543, c.getArea(), DELTA);
      assertEquals(1534.3852679, c2.getArea(), DELTA);
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(22.1, new Point(5, 4), Color.RED);

      assertEquals(35.6759261, c.getPerimeter(), DELTA);
      assertEquals(138.8583952, c2.getPerimeter(), DELTA);
   }

   @Test
   public void testCircleRadius()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(.0954, new Point(0, 0), Color.GREEN);

      assertEquals(5.678, c.getRadius(), DELTA);
      assertEquals(.0954, c2.getRadius(), DELTA);

      c.setRadius(69.0);
      c2.setRadius(1.0);

      assertEquals(69.0, c.getRadius(), DELTA);
      assertEquals(1.0, c2.getRadius(), DELTA);
   }

   @Test
   public void testRectangle()
   {
      Rectangle r = new Rectangle(5.0, 3.0, new Point(0, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(20.0, 5.2, new Point(0, 0), Color.RED);

      assertEquals(5.0, r.getWidth(), DELTA);
      assertEquals(20.0, r2.getWidth(), DELTA);
      assertEquals(3.0, r.getHeight(), DELTA);
      assertEquals(5.2, r2.getHeight(), DELTA);
      assertTrue(r.getTopLeft().equals(new Point(0, 3)));
      assertTrue(r2.getTopLeft().equals(new Point(0, 0)));
      assertEquals(15.0, r.getArea(), DELTA);
      assertEquals(104.0, r2.getArea(), DELTA);
      assertEquals(16.0, r.getPerimeter(), DELTA);
      assertEquals(50.4, r2.getPerimeter(), DELTA);

      r.setWidth(4.0);
      r.setHeight(5.0);
      r2.setWidth(6.0);
      r2.setHeight(9.0);

      assertEquals(4.0, r.getWidth(), DELTA);
      assertEquals(6.0, r2.getWidth(), DELTA);
      assertEquals(5.0, r.getHeight(), DELTA);
      assertEquals(9.0, r2.getHeight(), DELTA);
   }

   @Test
   public void testTriangle()
   {
      Triangle t = new Triangle(new Point(0, 0), new Point(4, 0), new Point(0, 3), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.RED);

      assertTrue(t.getVertexA().equals(new Point(0, 0)));
      assertTrue(t.getVertexB().equals(new Point(4, 0)));
      assertTrue(t.getVertexC().equals(new Point(0, 3)));
      assertTrue(t2.getVertexA().equals(new Point(0, 0)));
      assertTrue(t2.getVertexB().equals(new Point(6, 0)));
      assertTrue(t2.getVertexC().equals(new Point(3, 4)));
      assertEquals(6.0, t.getArea(), DELTA);
      assertEquals(12.0, t2.getArea(), DELTA);
      assertEquals(12.0, t.getPerimeter(), DELTA);
      assertEquals(16.0, t2.getPerimeter(), DELTA);
   }

   @Test
   public void testTranslate()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(.0954, new Point(0, 0), Color.GREEN);
      Rectangle r = new Rectangle(5.0, 3.0, new Point(0, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(20.0, 5.2, new Point(0, 0), Color.RED);
      Triangle t = new Triangle(new Point(0, 0), new Point(4, 0), new Point(0, 3), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.RED);

      c.translate(new Point(3, 4));
      c2.translate(new Point(6, 9));
      r.translate(new Point(8, 4));
      r2.translate(new Point(-2, -2));
      t.translate(new Point(0, 0));
      t2.translate(new Point(5, -3));

      assertTrue(c.getCenter().equals(new Point(5, 7)));
      assertTrue(c2.getCenter().equals(new Point(6, 9)));
      assertTrue(r.getTopLeft().equals(new Point(8, 7)));
      assertTrue(r2.getTopLeft().equals(new Point(-2, -2)));
      assertTrue(t.getVertexA().equals(new Point(0, 0)));
      assertTrue(t2.getVertexA().equals(new Point(5, -3)));
      assertTrue(t2.getVertexB().equals(new Point(11, -3)));
      assertTrue(t2.getVertexC().equals(new Point(8, 1)));
   }

   @Test
   public void testColor()
   {
      WorkSpace ws = new WorkSpace();
      List<Shape> expected = new LinkedList<>();

      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(.0954, new Point(0, 0), Color.GREEN);
      Rectangle r = new Rectangle(5.0, 3.0, new Point(0, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(20.0, 5.2, new Point(0, 0), Color.RED);
      Triangle t = new Triangle(new Point(0, 0), new Point(4, 0), new Point(0, 3), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.RED);

      ws.add(r);
      ws.add(c);
      ws.add(r2);
      ws.add(c2);
      ws.add(t);
      ws.add(t2);

      expected.add(r);
      expected.add(c);
      expected.add(t);

      assertEquals(expected, ws.getShapesByColor(Color.BLACK));

      assertTrue(c.getColor().equals(Color.BLACK));
      assertTrue(c2.getColor().equals(Color.GREEN));
      assertTrue(r.getColor().equals(Color.BLACK));
      assertTrue(r2.getColor().equals(Color.RED));
      assertTrue(t.getColor().equals(Color.BLACK));
      assertTrue(t2.getColor().equals(Color.RED));

      c.setColor(Color.BLUE);
      c2.setColor(Color.BLACK);
      r.setColor(Color.GREEN);
      r2.setColor(Color.BLACK);
      t.setColor(Color.ORANGE);
      t2.setColor(Color.YELLOW);

      assertTrue(c.getColor().equals(Color.BLUE));
      assertTrue(c2.getColor().equals(Color.BLACK));
      assertTrue(r.getColor().equals(Color.GREEN));
      assertTrue(r2.getColor().equals(Color.BLACK));
      assertTrue(t.getColor().equals(Color.ORANGE));
      assertTrue(t2.getColor().equals(Color.YELLOW));
   }

   @Test
   public void testEquals()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(.0954, new Point(0, 0), Color.GREEN);
      Circle c3 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Rectangle r = new Rectangle(5.0, 3.0, new Point(0, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(20.0, 5.2, new Point(0, 0), Color.RED);
      Rectangle r3 = new Rectangle(5.0, 3.0, new Point(0, 3), Color.BLACK);
      Triangle t = new Triangle(new Point(0, 0), new Point(4, 0), new Point(0, 3), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.RED);
      Triangle t3 = new Triangle(new Point(0, 0), new Point(4, 0), new Point(0, 3), Color.BLACK);

      assertFalse(c.equals(c2));
      assertTrue(c.equals(c3));
      assertFalse(r.equals(r2));
      assertTrue(r.equals(r3));
      assertFalse(t.equals(t2));
      assertTrue(t.equals(t3));
   }

   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                 Color.BLACK));

      assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceGetShapes()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expectedC = new LinkedList<>();
      List<Rectangle> expectedR = new LinkedList<>();
      List<Triangle> expectedT = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);
      Rectangle r = new Rectangle(5.0, 3.0, new Point(0, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(20.0, 5.2, new Point(0, 0), Color.RED);
      Triangle t = new Triangle(new Point(0, 0), new Point(4, 0), new Point(0, 3), Color.BLACK);
      Triangle t2 = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.RED);

      ws.add(r);
      ws.add(c1);
      ws.add(r2);
      ws.add(c2);
      ws.add(t);
      ws.add(t2);

      expectedC.add(c1);
      expectedC.add(c2);
      expectedR.add(r);
      expectedR.add(r2);
      expectedT.add(t);
      expectedT.add(t2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expectedC, ws.getCircles());
      assertEquals(expectedR, ws.getRectangles());
      assertEquals(expectedT, ws.getTriangles());
   }

   @Test
   public void testWorkSpaceGetPerimeterOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), Color.BLACK));

      assertEquals(61.09516775, ws.getPerimeterOfAllShapes(), DELTA);
   }

   /* HINT - comment out implementation tests for the classes that you have not 
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, 
         new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
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
         0, clazz.getFields().length);

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
