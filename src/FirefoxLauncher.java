import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;

public class FirefoxLauncher {
    private static WebDriver driver;
    private static WebDriverWait waitLong;
    private static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        //Start Chrome Browser driver
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        waitLong = new WebDriverWait(driver, 600);
        JavascriptExecutor je = (JavascriptExecutor) driver;
//        driver.manage().window().maximize();
//        System.setProperty("webdriver.firefox.driver","C:\\\\Users\\Erik\\Projects\\Selenium\\Driver");
        System.setProperty("webdriver.firefox.marionette","true");

        //Get Test Data
        TestData testData = new TestData("idealo");
        driver.get(testData.url);

        //Login
        Utils.login(wait, driver, testData);

        //Create new Shop
        TestShopCreation.createShop(wait,driver,testData);

        //Import Feed
        TestFeedImport testFeedImport = new TestFeedImport();
        testFeedImport.importFeed(wait,driver,testData,waitLong,je);

        //Export Feed
        TestExport.createExport(wait,je,testData);

        //Clean Up & Tear Down
        //Delete Shop
        TestShopCreation.deleteShop(wait,driver);

        //Logout
        Utils.logout(wait,driver);

        Thread.sleep(5000);
        driver.quit();
    }

}