import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirefoxLauncher {
    private static WebDriver driver;
    private static WebDriverWait waitLong;
    private static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        //Start Chrome Browser driver
        ChromeOptions capabilities = new ChromeOptions();
        driver = new ChromeDriver(capabilities);
        wait = new WebDriverWait(driver, 10);
        waitLong = new WebDriverWait(driver, 600);
        JavascriptExecutor je = (JavascriptExecutor) driver;
//        driver.manage().window().maximize();
//        System.setProperty("webdriver.chrome.driver","C:\\\\Users\\Erik\\Projects\\Selenium\\Driver");
        capabilities.setCapability("marionette", true);

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
        TestExport.createExport(wait,je);

        //Clean Up & Tear Down
        //Delete Shop
        TestShopCreation.deleteShop(wait,driver);

        //Logout
        Utils.logout(wait,driver);

        Thread.sleep(5000);
        driver.quit();
    }

}