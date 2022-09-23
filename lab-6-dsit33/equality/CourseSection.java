import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public String getPrefix()
   {return prefix;}

   public String getNumber()
   {return number;}

   public int getEnrollment()
   {return enrollment;}

   public LocalTime getStartTime()
   {return startTime;}

   public LocalTime getEndTime()
   {return endTime;}

   public boolean equals(Object other)
   {
      if (other == null)
         {return false;}
      if (getClass() != other.getClass())
         {return false;}
      return hashCode() == other.hashCode();
   }

   public int hashCode()
   {
      int hash = 0;
      if (this == null)
         {return 0;}
      if (prefix != null)
      {
         for (int i = 0; i < prefix.length(); i++)
         {hash += 32 * prefix.charAt(i);}
      }
      if (number != null)
      {
         for (int j = 0; j < number.length(); j++)
         {hash += 5 * number.charAt(j);}
      }

      hash += 12 * enrollment;

      if (startTime != null)
      {
         String startStr = startTime.toString();
         for (int k = 0; k < startStr.length(); k++)
            {hash += 10 * startStr.charAt(k);}
      } 

      if (endTime != null)
      {
         String endStr = endTime.toString();
         for (int p = 0; p < endStr.length(); p++)
            {hash += 13 * endStr.charAt(p);}
      }
      return hash; 
   }

   // additional likely methods not defined since they are not needed for testing
}
