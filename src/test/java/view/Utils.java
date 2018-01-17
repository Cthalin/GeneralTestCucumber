package view;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;

public class Utils {

    public static void login(WebDriverWait wait, WebDriver driver, TestData testData){
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(testData.user);
        WebElement parent = driver.findElement(By.className("password"));
        parent.findElement(By.name("password")).sendKeys(testData.passwd);
        driver.findElement(By.cssSelector("form.login div.submit input")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ready")));
        driver.findElement(By.cssSelector("nav.start a[href=\"/start/shops\"]")).click();
    }
    public static void login(String user, String password){
        BrowserDriver.getCurrentDriver().findElement(By.name("email")).sendKeys(user);
        BrowserDriver.getCurrentDriver().findElement(By.name("password")).sendKeys(password);
        BrowserDriver.getCurrentDriver().findElement(By.cssSelector("form.login div.submit input")).click();
    }

    public static void logout(WebDriverWait wait, WebDriver driver){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.category-context")));
        driver.findElement(By.cssSelector("div.navigation-service a.button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.navigation-service a[href=\"/service/logout\"]"))).click();
    }

    public static void openPageChrome(){
        //Start Chrome Browser driver
        ChromeOptions capabilities = new ChromeOptions();
        WebDriver driver = new ChromeDriver(capabilities);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebDriverWait waitLong = new WebDriverWait(driver, 600);
        JavascriptExecutor je = (JavascriptExecutor) driver;
//        driver.manage().window().maximize();
//        System.setProperty("webdriver.chrome.driver","C:\\\\Users\\Erik\\Projects\\Selenium\\Driver");
        capabilities.setCapability("marionette", true);

        //Get Test Data
        TestData testData = new TestData("idealo");
        driver.get(testData.url);
    }

    public static void openPage(){
        BrowserDriver.getCurrentDriver().manage().deleteAllCookies();

        String host = "release.go.channelpilot.com";

        if (System.getProperty("host") != null) {
            host = System.getProperty("host");
        }

        BrowserDriver.getCurrentDriver().get("http://" + host);

    }
    public static boolean isLogInPageDisplayed() {
        return BrowserDriver.getCurrentDriver().findElement(By.cssSelector("form.login")).isDisplayed();
    }
}
