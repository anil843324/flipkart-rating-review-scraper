package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        // testCase01: Go to www.flipkart.com. Search "Washing Machine".
        // Sort by popularity and print the count of items with rating less than or
        // equal to 4 stars.
        @Test(enabled = true)
        public void testCase01() throws InterruptedException {
                System.out.println("testCase 01 Start");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                SoftAssert softAssert = new SoftAssert();
                // Navigate to the URL.
                Wrappers.openURL(driver, "http://www.flipkart.com/");

                // check login Popu if present or not
                Wrappers.closeLoginPopupIfPresent(driver, By.xpath("// div[@class='JFPqaw']/span"));

                String currentURLString = driver.getCurrentUrl();

                softAssert.assertTrue(currentURLString.contains("flipkart"),
                                "The current URL does not indicate a successful redirection to the Flipkart website.");

                // Enter Text
                Wrappers.enterText(driver, By.xpath(
                                "//input[starts-with(@placeholder, 'Search for Product') or @type='text' or starts-with(@title, 'Search for Product')]"),
                                "Washing Machine");

                // Click on button.
                Wrappers.clickElement(driver, By.xpath(
                                "//button[starts-with(@aria-label, 'Search for Product') or @type='submit' or starts-with(@title, 'Search for Product')]"));

                wait.until(ExpectedConditions.urlContains("https://www.flipkart.com/search"));

                String currentTitle = driver.getTitle();
                boolean containsiwashingMaching = currentTitle.toLowerCase().contains("washing machine");
                softAssert.assertTrue(containsiwashingMaching,
                                "Search for 'washing machine' may have failed. title does not reflect the search term.");

                // Click on "popularity"
                Wrappers.clickElement(driver, By.xpath(
                                "//div[text()='Popularity']"));

                // print the count of items with rating less than or equal to 4 stars.

                Thread.sleep(5000);
                Wrappers.printItemsByRatingCondition(driver, By.xpath("//div[contains(@class,'yKfJKb')]"), By.xpath(
                                ".//div[contains(text(), 'Washing Machine') or contains(text(), 'Automatic') or contains(text(), 'Fully') or contains(text(),'kg')]"),
                                By.xpath(".//span[@id and contains(@id,'productRating')]"));

                System.out.println("testCase01 End");
                softAssert.assertAll();

        }

        // testCase02: Search "iPhone", print the Titles and discount % of items with
        // more than 17% discount
        @Test(enabled = true)
        public void testCase02() throws InterruptedException {
                System.out.println("testCase 02 Start");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                SoftAssert softAssert = new SoftAssert();
                // Navigate to the URL.
                Wrappers.openURL(driver, "http://www.flipkart.com/");

                // check login Popu if present or not
                Wrappers.closeLoginPopupIfPresent(driver, By.xpath("// div[@class='JFPqaw']/span"));

                String currentURLString = driver.getCurrentUrl();

                softAssert.assertTrue(currentURLString.contains("flipkart"),
                                "The current URL does not indicate a successful redirection to the Flipkart website.");

                // Enter Text "iPhone"
                Wrappers.enterText(driver, By.xpath(
                                "//input[starts-with(@placeholder, 'Search for Product') or @type='text' or starts-with(@title, 'Search for Product')]"),
                                "iPhone");

                // Click on search button.
                Wrappers.clickElement(driver, By.xpath(
                                "//button[starts-with(@aria-label, 'Search for Product') or @type='submit' or starts-with(@title, 'Search for Product')]"));

                wait.until(ExpectedConditions.urlContains("https://www.flipkart.com/search"));

                String currentTitle = driver.getTitle();
                boolean containsiPhone = currentTitle.toLowerCase().contains("iphone");
                softAssert.assertTrue(containsiPhone,
                                "Search for 'iPhone' may have failed. title does not reflect the search term.");

                // print the Titles and discount % of items with more than 17% discount
                Thread.sleep(5000);
                Wrappers.printItemsByDiscount(driver, By.xpath("//div[contains(@class,'yKfJKb')]"), By.xpath(
                                ".//div[contains(text(),'Apple') or contains(text(),'iphone')]"),
                                By.xpath(".//div[contains(@class,'UkUFwK')]/span"));

                System.out.println("testCase02 End");
                softAssert.assertAll();

        }

        // testCase03: Search "Coffee Mug", select 4 stars and above, and print the
        // Title and image URL of the 5 items with highest number of reviews
        @Test(enabled = true)
        public void testCase03() throws InterruptedException {
                System.out.println("testCase 03 Start");
                SoftAssert softAssert = new SoftAssert();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                // Navigate to the URL.
                Wrappers.openURL(driver, "http://www.flipkart.com/");

                // check login Popu if present or not
                Wrappers.closeLoginPopupIfPresent(driver, By.xpath("// div[@class='JFPqaw']/span"));

                String currentURLString = driver.getCurrentUrl();

                softAssert.assertTrue(currentURLString.contains("flipkart"),
                                "The current URL does not indicate a successful redirection to the Flipkart website.");

                // Enter Text
                Wrappers.enterText(driver, By.xpath(
                                "//input[starts-with(@placeholder, 'Search for Product') or @type='text' or starts-with(@title, 'Search for Product')]"),
                                "Coffee Mug");

                // Click on button.
                Wrappers.clickElement(driver, By.xpath(
                                "//button[starts-with(@aria-label, 'Search for Product') or @type='submit' or starts-with(@title, 'Search for Product')]"));

                wait.until(ExpectedConditions.urlContains("https://www.flipkart.com/search"));

                String currentTitle = driver.getTitle();

                boolean containsCoffeMug = currentTitle.toLowerCase().contains("coffee mug");

                softAssert.assertTrue(containsCoffeMug,
                                "Search for 'Coffee Mug' may have failed. title does not reflect the search term.");

                // select 4 stars and above
                Wrappers.clickElement(driver, By.xpath("//div[contains(text(),'4') and contains(text(),'above')]"));

                Thread.sleep(5000);

                // print the Title and image URL of the 5 items with highest number of reviews
                Wrappers.printItemByHighestReview(driver, By.xpath("//div[starts-with(@data-id, 'MUGG')]"),
                                By.xpath(".//img[@loading='eager' or starts-with(@alt,'Pigeon ThermoCup')]"),
                                By.xpath(".//a[@class='wjcEIp']"),
                                By.xpath(".//span[starts-with(@id,'productRating')]/following-sibling::span"));

                System.out.println("testCase03 End");

                softAssert.assertAll();

        }

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}