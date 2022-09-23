import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;

import org.junit.Test;

public class TestCases
{
   private final double DELTA = .00001 ;
   /* helper methods used in the tests below */
   private <T> List<T> mapIt(List<T> list, Function<T,T> func)
   {
      final List<T> results = new LinkedList<>();
      for (final T value : list)
      {
         results.add(func.apply(value));
      }

      return results;
   }

   private <T> List<T> filterIt(List<T> list, Predicate<T> pred)
   {
      final List<T> results = new LinkedList<>();
      for (final T value : list)
      {
         if (pred.test(value))
         {
            results.add(value);
         }
      }

      return results;
   }

   private LongSupplier getNumberGenerator()
   {
      int number[] = {0};

      return () -> number[0]++;
   }

   private LongFunction<LongUnaryOperator> createAdder()
   {
      return x -> y -> x + y;
   }

   /* test cases */
   @Test
   public void testExercise1()
   {
      final LongUnaryOperator func = x -> x + 1;

      assertEquals(8, func.applyAsLong(7)); //takes in the value passed through and performs on it
   }

   @Test
   public void testExercise2()
   {
      final LongSupplier first = getNumberGenerator(); 
      final LongSupplier second = getNumberGenerator(); //each create a reference to number generator

      assertEquals(0, first.getAsLong());
      assertEquals(1, first.getAsLong());
      assertEquals(0, second.getAsLong());
      assertEquals(2, first.getAsLong());
      assertEquals(1, second.getAsLong());
      assertEquals(3, first.getAsLong());
      assertEquals(2, second.getAsLong()); //each time a reference is called, the respective value in the generator is incremented
   }

   @Test
   public void testExercise3()
   {
      final LongFunction<LongUnaryOperator> curried = createAdder();
      final LongUnaryOperator add7 = curried.apply(7);
      final LongUnaryOperator add3 = curried.apply(3);

      assertEquals(9, add7.applyAsLong(2));
      assertEquals(5, add3.applyAsLong(2));
      assertEquals(13, add3.applyAsLong(10)); //the value that is incremented is the one that was originally initialized
   }

   @Test
   public void testExercise4()
   {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      final List<Integer> expected = Arrays.asList(11, 12, 13, 14, 15);
      final int n = 10;

      final List<Integer> result = mapIt(numbers, x -> x + n); //takes in each value of the passed list and performs the function on it

      assertEquals(expected, result);
   }

   @Test
   public void testExercise5()
   {
      final List<String> strings = Arrays.asList(
         "hello",
         "Hello",
         "HeLLo",
         "helLo",
         "HELLO");
      final List<String> expected = Arrays.asList("hello", "hello", "hello", "hello", "hello");

      final List<String> result = mapIt(strings, String::toLowerCase); //performs toLowerCase on each string in the list

      assertEquals(expected, result);
   }

   @Test
   public void testExercise6()
   {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      final List<Integer> expected = Arrays.asList(2, 4);

      final List<Integer> result = filterIt(numbers, x -> (x & 1) == 0); //return all the values that do not have a 1 in last bit

      assertEquals(expected, result);
   }

   @Test
   public void testExercise7()
   {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      final List<Integer> expected = Arrays.asList(4, 16);

      final List<Integer> result = mapIt(
         filterIt(numbers, x -> (x & 1) == 0),
         x -> x * x); //multiplies each returned value from filterIt by itself and adds that to result

      assertEquals(expected, result);
   }
}
