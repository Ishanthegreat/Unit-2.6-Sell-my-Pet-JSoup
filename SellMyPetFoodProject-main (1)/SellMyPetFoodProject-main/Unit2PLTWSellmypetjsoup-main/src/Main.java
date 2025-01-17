import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class ReviewScraper {
    public static void main(String[] args) {
        
        String baseURL = "https://www.trustpilot.com/review/www.apple.com?page="; // Base URL to scrape reviews
        int totalPages = 10; // Number of pages to scrape
        String outputFilePath = "reviews_output.txt"; // Output file for storing scraped data

        try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
            // Write CSV header to the file
            fileWriter.write("\"Reviewer Name\",\"Review Content\",\"Star Rating\",\"Review Date\"\n");
            int totalReviewsScraped = 0;

            for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
                String completeURL = baseURL + currentPage; // Construct the page URL

                // Establish a connection with the webpage
                Connection webpageConnection = Jsoup.connect(completeURL)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                                   "Chrome/115.0.0.0 Safari/537.36")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Language", "en-US,en;q=0.9")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("Referer", "https://www.trustpilot.com/")
                        .timeout(10000)
                        .followRedirects(true);

                // Delay to avoid overloading the server
                try {
                    Thread.sleep((long) (2000 + Math.random() * 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Fetch HTML content of the page
                Document htmlDocument = webpageConnection.get();
                System.out.println("Fetched HTML content for page: " + currentPage);

                // Extract reviews from the page
                Elements reviewElements = htmlDocument.select("article[data-service-review-card-paper]");
                if (reviewElements.isEmpty()) {
                    System.out.println("No reviews found on page " + currentPage + ". Possibly dynamically loaded.");
                    break;
                }
                
                // Parse and save each review
                for (Element review : reviewElements) {
                    String reviewerName = review.select("span[data-consumer-name-typography]").text().replaceAll("\"", "\"\"");
                    if (reviewerName.isEmpty()) reviewerName = "N/A";

                    String reviewContent = review.select("p[data-service-review-text-typography]").text().replaceAll("\"", "\"\"");
                    if (reviewContent.isEmpty()) reviewContent = "N/A";

                    String starRating = review.select("div[data-service-review-rating] img").attr("alt").replaceAll("\"", "\"\"");
                    if (starRating.isEmpty()) starRating = "N/A";

                    String reviewDate = review.select("p[data-service-review-date-of-experience-typography]").text().replaceAll("\"", "\"\"");
                    if (reviewDate.isEmpty()) reviewDate = "N/A";

                    // Write extracted data to the file
                    fileWriter.write("\"" + reviewerName + "\",\""
                            + reviewContent + "\",\""
                            + starRating + "\",\""
                            + reviewDate + "\"\n");
                    totalReviewsScraped++;
                }
            }

            System.out.println("Scraping completed. Total reviews scraped: " + totalReviewsScraped);
            System.out.println("Data saved to file: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
