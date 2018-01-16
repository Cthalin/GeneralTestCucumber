import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class TestExport {
    private static WebDriver driver;
    private static WebDriverWait waitLong;
    private static TestData testData;

    public static void main(String[] args) throws InterruptedException {
        ChromeOptions capabilities = new ChromeOptions();
        driver = new ChromeDriver(capabilities);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        waitLong = new WebDriverWait(driver, 600);
        JavascriptExecutor je = (JavascriptExecutor) driver;
//        driver.manage().window().maximize();
//        System.setProperty("webdriver.chrome.driver","C:\\\\Users\\Erik\\Projects\\Selenium\\Driver");
        capabilities.setCapability("marionette", true);

        //Release
        String url = "https://release.go.channelpilot.com";
        String user = "release_1080@channelpilot.com";
        String passwd = "Daheim123";
        //Würfel
//        String url = "http://erikswuerfel";
//        String user = "erik.slowikowski@channelpilot.com";
//        String passwd = "*uUQL;k!6(9p&Em&";

        String shopTitle = "TestShop";
        String shopUrl = "www.testshop.shop";
        String feedUrl = "http://www.daheim.de/channelpilot?password=cP4AMz2014";
        testData = new TestData("idealo");

        driver.get(url);

        //Login
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(user);
        WebElement parent = driver.findElement(By.className("password"));
        parent.findElement(By.name("password")).sendKeys(passwd);
        driver.findElement(By.cssSelector("form.login div.submit input")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ready")));
        driver.findElement(By.cssSelector("div.menu-bar a.button.price-comparison-sites")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.row.shop-selector > div > a"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.shop-selector a[title=\""+shopTitle+"\"]"))).click();

        //Export Feed
        createExport(wait,je);

        //Clean Up
        //Delete Feed
        driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a[title=\""+testData.title+"\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.row > div.twelve.columns.header > div.details > a"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#channelpilot-dialogbox > div > a.button.okay"))).click();

        //Logout
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("div.navigation-service a.button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.navigation-service a[href=\"/service/logout\"]"))).click();

        Thread.sleep(5000);
        driver.quit();
    }

    public static void createExport(WebDriverWait wait, JavascriptExecutor je) throws InterruptedException {
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.row.channels a.add-channel")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.channels a.add-channel"))).click();
        waitLong.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.channels a[href=\""+testData.ref+"\"]"))).click();
        Thread.sleep(3000);
        checkChannel(testData.logo);
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.app.pcs-manager div.logo > img[src=\""+testData.logo+"\"]")));
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.row > div > div.layer > div.row.context > div.display > div.general > div.links > div")).click();
        assertTrue("ChannelSetup incomplete", testChannelSetup());
    }

    private static boolean testChannelSetup(){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul > li:nth-child(2) > a")));
        return driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a")).findElements(By.cssSelector("a[title=\""+testData.title+"\"]")).size() == 0;
    }

    private static void checkChannel(String yourChannelLogo){
        WebElement channelLogoWE = driver.findElement(By.cssSelector("div.app.pcs-manager div.logo > img[src]"));
        assertTrue("Wrong Channel selected",channelLogoWE.getAttribute("src").contains(yourChannelLogo));
    }
}