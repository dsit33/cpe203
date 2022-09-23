import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   public boolean equals(Object other)
   {
      if (other == null)
         {return false;}
      if (getClass() != other.getClass())
         {return false;}
      Student otherStudent = (Student)other;
      return Objects.equals(surname, otherStudent.surname) && Objects.equals(givenName, otherStudent.givenName) 
         && age == otherStudent.age && Objects.equals(currentCourses, otherStudent.currentCourses)
         && hashCode() == other.hashCode();
   }

   public int hashCode()
   {
      return 5 * Objects.hashCode(surname) + 7 * Objects.hashCode(givenName) + 9 * Objects.hashCode(age)
         + 3 * getCourseHashCodes() ;
   }

   private int getCourseHashCodes()
   {
      int hash = 0;
      for (CourseSection course : currentCourses)
      {
         hash += Objects.hash(course.getPrefix(), course.getNumber(), course.getEnrollment(),
            course.getStartTime(), course.getEndTime());
      }

      return hash;
   }
}
