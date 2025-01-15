public class TargetedAd {

    public static void main(String[] args)
    {
      /*  
       * TODO:
       * PREPARATION WORK
       * (1) Create a file called targetWords.txt. Populate this file with words on each line that
       *     you think would determine if a user is a dog or cat owner.
       * 
       * PROGRAMMING
       * (2) Create a new DataCollector object and set the data to "socialMediaPostsSmall.txt" and "targetWords.txt"
       *     Important: Use the socialMedialPostsSmall to create your algorithm. Using a small file will help you 
       *     generate your solution quicker and give you the ability to double check your work.
       * (3) Create a String variable to hold the names of all the user. (The first word of every post is 
       *     a person's username)
       * (4) Compare each user's post to each target word. If a user mentions a target word, add their username to 
       *     the String of users. Separate usernames with a space. 
       *         Hint: You can use loops to look through each word. 
       *         Hint2: You can use indexOf to check if a word is in a user post. 
       * (5) Once you have all the users, use your DataCollector's prepareAdvertisement method to prepare a file 
       *     with all users and the advertisement you will send them.
       *         Additional Info: The prepareAdvertisement creates a new file on your computer. Check the posts of
       *         some of the usernames to make sure your algorithm worked.
       * 
       * THE FINAL SOLUTION
       * (6) Your solution should work with the socialMedialPostsSmall.txt. Modify your DataCollector initialization
       *    so you use the socialMediaPosts.txt. You should now have a larger file of users to target.
       */
  
  
      /* your code here */
      DataCollector dc = new DataCollector();
      dc.setData("reviews.txt","targetwords.txt");
      String names = "";

      while (true){
        String post = dc.getNextReview().toLowerCase();
        if (!post.equals("none")){
          String name = post.split(" ")[0];
          while (true){
            String tw = dc.getNextTargetWord().toLowerCase();
            if (tw.equals("none")){
              break;
            }
            if (post.indexOf(tw) != -1){
              names = names + name + " ";
              break;
            }
          }
        } else {
          break;
        }
        //System.out.println(names);
      }

      dc.setData("reviews.txt","targetwords2.txt");
      String names2 = "";
      while (true){
        String post = dc.getNextReview().toLowerCase();
        if (!post.equals("none")){
          String name2 = post.split(" ")[0];
          while (true){
            String tw = dc.getNextTargetWord().toLowerCase();
            if (tw.equals("none")){
              break;
            }
            if (post.indexOf(tw) != -1){
              names2 = names2 + name2 + " ";
              break;
            }
          }
        } else {
          break;
        }
        //System.out.println(names);
      }
      String adText = "Hello, are you interested in buying an Apple product? It's a step into the future afterall.";
      String adText2 = "Hello, are you interested in switching from an Apple product to an Andriod product?";
      dc.prepareAdvertisement("advertisement.txt", names, adText);
      dc.prepareAdvertisement("advertisement2.txt", names2, adText2);
    }
  
  }
