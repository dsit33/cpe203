import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978),
      };

   private static final Song testSong = new Song("Masons", "The Mariner's Revenge Song", 2005);

   @Test
   public void testArtistComparator()
   {
      ArtistComparator artist = new ArtistComparator();
      assertTrue(artist.compare(songs[0], songs[1]) < 0);
      assertTrue(artist.compare(songs[1], songs[2]) > 0);
      assertTrue(artist.compare(songs[0], songs[0]) == 0);
   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> titleComparator = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      assertTrue(titleComparator.compare(songs[0], songs[1]) > 0);
      assertTrue(titleComparator.compare(songs[1], songs[2]) < 0);
      assertTrue(titleComparator.compare(songs[3], songs[5]) == 0);
   }

   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> yearComparator = Comparator.comparing(Song::getYear).reversed();
      assertTrue(yearComparator.compare(songs[0], songs[1]) == 0);
      assertTrue(yearComparator.compare(songs[1], songs[2]) > 0);
      assertTrue(yearComparator.compare(songs[2], songs[3]) < 0);
   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> titleComparator = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      Comparator<Song> yearComparator = Comparator.comparing(Song::getYear).reversed();
      ComposedComparator composedComparator = new ComposedComparator(titleComparator, yearComparator);

      assertTrue(composedComparator.compare(songs[3], songs[7]) < 0);
      assertTrue(composedComparator.compare(songs[7], songs[5]) > 0);
      assertTrue(composedComparator.compare(songs[0], testSong) == 0);
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> titleComparator = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      Comparator<Song> artistComparator = (s1,s2) -> s1.getArtist().compareTo(s2.getArtist());
      Comparator<Song> thenComparator = titleComparator.thenComparing(artistComparator);

      assertTrue(thenComparator.compare(songs[3], songs[5]) > 0);
      assertTrue(thenComparator.compare(songs[3], songs[7]) == 0);
      assertTrue(thenComparator.compare(songs[0], songs[1]) > 0);
   }

   @Test
   public void runSort()
   {
      Comparator<Song> artistComparator = (s1,s2) -> s1.getArtist().compareTo(s2.getArtist());
      Comparator<Song> titleComparator = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      Comparator<Song> yearComparator = Comparator.comparing(Song::getYear);
      Comparator<Song> finalComparator = artistComparator.thenComparing(titleComparator.thenComparing(yearComparator));
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      songList.sort(
         finalComparator
      );

      assertEquals(songList, expectedList);

   }
}
