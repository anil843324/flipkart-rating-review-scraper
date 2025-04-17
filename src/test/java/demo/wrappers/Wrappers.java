package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
// import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.browsingcontext.Locator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.TimeoutException;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static class Product {
        String title;
        String imageUrl;
        int reviewCount;

        // Constructor
        public Product(String title, String imageUrl, int reviewCount) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.reviewCount = reviewCount;
        }

        // Getters (optional)
        public String getTitle() {
            return title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getReviewCount() {
            return reviewCount;
        }

    }

    public static void openURL(WebDriver driver, String url) {

        try {
            driver.get(url);
        } catch (Exception e) {
            System.out.println("Failed to open URL:" + url);
            e.printStackTrace();
        }
    }

    public static void enterText(WebDriver driver, By locator, String inputString) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            WebElement inputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            inputBox.click();
            inputBox.clear();
            inputBox.sendKeys(inputString);
        } catch (Exception e) {
            System.out.println("Failed to enter text in input box located by: " + locator);
            e.printStackTrace();
        }
    }

    public static void clickElement(WebDriver driver, By locator) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            WebElement element = driver.findElement(locator);
            try {
                element.click();
            } catch (Exception clickIssue) {
                // Fallback using JavaScript
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }
        } catch (Exception e) {
            System.out.println("Failed to Click on the element located by: " + locator);
            e.printStackTrace();
        }
    }

    public static void printItemsByRatingCondition(WebDriver driver, By parentLocator, By titleLocator,
            By ratingLocator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        int count = 1;
        boolean found = false; // Flag to check if any product matches the condition

        try {
            List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(parentLocator));

            for (int i = 0; i < list.size(); i++) {
                String star = list.get(i).findElement(ratingLocator).getText();
                String title = list.get(i).findElement(titleLocator).getText();

                // Check if the rating is <= 4.0
                if (Double.parseDouble(star) <= 4.0) {
                    System.out.println(count + ". " + title + " | Rating: " + star);
                    count++;
                    found = true; // Set found to true when we match a product
                }
            }

            // If no product matched the condition, print a message
            if (!found) {
                System.out.println("No products found with a rating of 4.0 or less.");
            }

        } catch (Exception e) {
            System.out.println("Failed to fetch product titles or ratings using the provided locators.");
            e.printStackTrace();
        }
    }

    public static void printItemsByDiscount(WebDriver driver, By parentLocator, By titleLocator,
            By percentageLocator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        int count = 1;
        boolean found = false; // Flag to check if any product matches the condition

        try {
            List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(parentLocator));

            for (int i = 0; i < list.size(); i++) {
                try {
                    // Try to find percentage text
                    String percentageText = list.get(i).findElement(percentageLocator).getText().trim();

                    if (percentageText.isEmpty()) {
                        continue; // Skip if text is empty
                    }

                    String numeric = percentageText.replaceAll("[^0-9]", ""); // Extract only digits
                    if (numeric.isEmpty()) {
                        continue; // Skip if no number is found
                    }

                    int percentValue = Integer.parseInt(numeric);
                    if (percentValue > 17) {
                        String title = list.get(i).findElement(titleLocator).getText().trim();
                        System.out.println(count + ". " + title + " | Discount: " + percentageText);
                        count++;
                        found = true;
                    }
                } catch (NoSuchElementException e) {
                    // Skip this product if percentage element is not found
                    continue;
                } catch (Exception e) {
                    System.out.println("Error processing item at index " + i + ": " + e.getMessage());
                }
            }

            if (!found) {
                System.out.println("No products found with a discount greater than 17%.");
            }

        } catch (Exception e) {
            System.out.println("Failed to fetch product titles or discounts using the provided locators.");
            e.printStackTrace();
        }
    }

    public static void printItemByHighestReview(WebDriver driver, By parentLocator, By imageURLocator, By titleLocator,
            By reviewLocator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<Product> productList = new ArrayList<>();

        try {

            List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(parentLocator));

            for (int i = 0; i < list.size(); i++) {

                try {

                    // Try to find review text
                    // reviewLocator will return in reviewText this (392)
                    String reviewText = list.get(i).findElement(reviewLocator).getText().trim();

                    if (reviewText.isEmpty()) {
                        continue; // Skip if text is empty
                    }

                    String numeric = reviewText.replaceAll("[^0-9]", ""); // Extract only digits
                    if (numeric.isEmpty()) {
                        continue; // Skip if no number is found
                    }

                    int review = Integer.parseInt(numeric);
                    String title = list.get(i).findElement(titleLocator).getText().trim();
                    String imageURL = list.get(i).findElement(imageURLocator).getAttribute("src");

                    productList.add(new Product(title, imageURL, review));

                } catch (NoSuchElementException e) {
                    // Skip this product if percentage element is not found
                    continue;

                } catch (Exception e) {
                    System.out.println("Error processing item at index " + i + ": " + e.getMessage());
                }

            }

        } catch (Exception e) {
            System.out.println("Failed to fetch product titles or image url and review using the provided locators.");
            e.printStackTrace();
        }

        // sort that productlist according to most number of reviews
        productList.sort((p1, p2) -> Integer.compare(p2.getReviewCount(), p1.getReviewCount()));

        // print the Title and image URL of the 5 items with highest number of reviews
        // Math.min(5, productList.size())
        for (int i = 0; i < 5; i++) {
            Product p = productList.get(i);

            System.out.println((i + 1) + ". " + "  | Image URL" + p.getImageUrl() + " |  Title: " + p.getTitle()
                    + " | Reviews: " + p.getReviewCount());

        }

    }

    public static void closeLoginPopupIfPresent(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement loginPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            loginPopup.click();
            System.out.println("Login popup was present and clicked.");
        } catch (TimeoutException e) {
            System.out.println("Login popup not present. Continuing without clicking.");
        }
    }

}
