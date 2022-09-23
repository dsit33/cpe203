import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap
{
   public static List<String> highEnrollmentStudents(
      Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List<String> overEnrolledStudents = new LinkedList<>();


      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */
      for (String key : courseListsByStudentName.keySet()){
         List<Course> courseList = courseListsByStudentName.get(key);
         int totalUnits = 0;
         for (Course course : courseList){
            totalUnits += course.getNumUnits();
         }
         if (totalUnits > unitThreshold){
            overEnrolledStudents.add(key);
         }
      }
      /**for (List classList : courseListsByStudentName.values()){
         int totalUnits = 0;
         for (int i = 0; i <= classList.size() - 1; i++){
            totalUnits += classList.get(i).numUnits;
         }
         if (totalUnits > unitThreshold){
            overEnrolledStudents.add(classList.getKey());
         }
      }*/
         

      return overEnrolledStudents;      
   }
}
