public class Lab00
{
   public static void main(String[] args)
   {
   		//declaring and initializing variable	
   		int x = 5;
   		String y = "hello";
   		double z = 9.8;

   		//printing the variables
   		System.out.println("x: " + x + " y: " + y + " z: " + z);

   		//a list (make an array in Java)
   		int nums[] = new int[]{3, 6, -1, 2};
   		for (int num : nums){
   			System.out.println(num);
   		}

   		//call a function
   		int numFound = char_count(y, 'l');
   		System.out.println("Found: " +numFound);

   		//a counting for loop
   		for(int i=1; i<11; i++)
   		{
   			System.out.print(i + " ");
   		}
   		System.out.println();

   
   }
   public static int char_count(String s, char c){
   	int count = 0;
   	char[] word = s.toCharArray();
   	for(char ch : word){
   		if (ch == c){
   			count += 1;
   		}
   	}
   	return count;
   }
}