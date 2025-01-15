
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.*;


public class DataCollector
{
  private ArrayList<String> allReviews;
  private ArrayList<String> targetWords;
  private Scanner sc;
  private int currentPost;
  private int currentTargetWord;

  public DataCollector()
  {
    allReviews = new ArrayList<String>();
    targetWords = new ArrayList<String>();
    currentPost = 0;
    currentTargetWord = 0;
  }

  /**
   * Gather the data contained in the files allReviews and
   * targetWordsFilename (including punctuation), with words separated by a single
   * space
   * 
   * @param allReviews the name of the file containing the reviews
   * @param targetWordsFilename the name of the file containing the target words
   */
  public void setData(String allReviews, String targetWordsFilename) {
    // read all the scraped reviews
    try
    {
      sc = new Scanner(new File(allReviews));
      while (sc.hasNextLine())
      {
        // String method trim removes whitespace before and after a string
        String temp = sc.nextLine().trim();
        this.allReviews.add(temp);
      }
    } catch (Exception e) { System.out.println("Error reading or parsing" + allReviews + "\n" + e); }

    // read in the target words in targetWords
    try
    {
      sc = new Scanner(new File(targetWordsFilename));
      while (sc.hasNextLine())
      {
        // String method trim removes whitespace before and after a string
        this.targetWords.add(sc.nextLine().trim());
      }
    } catch (Exception e) { System.out.println("Error reading or parsing" + targetWords + "\n" + e); }
  }

  //Get the next review in allReviews 
  public String getNextReview()
  {
    if (currentPost < allReviews.size())
    {
      this.currentPost++;
      return allReviews.get(currentPost - 1);
    }
    else
    {
      return "NONE";
    }
  }

  /*
   Get the next line in targetWords, with words separated by a space,
   or "NONE" if there is no more data.
   */
  public String getNextTargetWord()
  {
    if (currentTargetWord < targetWords.size())
    {
      this.currentTargetWord++;
      return targetWords.get(currentTargetWord - 1);
    }
    else
    {
      this.currentTargetWord = 0;
      return "NONE";
    }
  }

  //Create a File named filename and stores all the usernames to target
  public void prepareAdvertisement(String filename, String usernames, String advertisement)
  {
    try
    {
      FileWriter fw = new FileWriter(filename);
      for (String un : usernames.split(" "))
      {
          fw.write("@" + un + " " + advertisement +"\n");
      }
      fw.close();
    }
    catch (IOException e)
    {
        System.out.println("Could not write to file. " + e);
    }
  }
}