import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class ChromeLauncher {
    private static WebDriver driver;
    private static WebDriverWait waitLong;

    public static void main(String[] args) throws InterruptedException {
        ChromeOptions capabilities = new ChromeOptions();;
        driver = new ChromeDriver(capabilities);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        waitLong = new WebDriverWait(driver, 600);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

        driver.get(url);

        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(user);
        WebElement parent = driver.findElement(By.className("password"));
        parent.findElement(By.name("password")).sendKeys(passwd);
        driver.findElement(By.cssSelector("form.login div.submit input")).click();
//        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ready")));
        driver.findElement(By.cssSelector("nav.start a[href=\"/start/shops\"]")).click();
        //Create new Shop
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.shops[href=\"#shops\"]"))).click();
//        driver.findElement(By.cssSelector("a.shops[href=\"#shops\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("a[href=\"/start/shops/new\"")))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.title")));
        driver.findElement(By.cssSelector("input.title")).sendKeys(shopTitle);
        driver.findElement(By.cssSelector("input.url")).sendKeys(shopUrl);
        driver.findElement(By.cssSelector("input.is-active")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("input.theme")).click();

        //Import Feed
        Thread.sleep(5000);
        WebElement title = driver.findElement(By.cssSelector("h1.shop-selected-title"));
        waitLong.until(ExpectedConditions.attributeToBe(title,"innerText",shopTitle));
        WebElement myDynElement = (new WebDriverWait(driver,5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]")));
        myDynElement.click();
        //        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.settings input.theme")));
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]"))).click();
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("form.catalog-source select.type"))).click();
//        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source label.type")));
        Select typeSelect = new Select(driver.findElement(By.cssSelector("form.catalog-source select.type")));
        Thread.sleep(1000);
        typeSelect.selectByValue("http");
        Thread.sleep(1000);
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.catalog-source div.type.http input.url")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.catalog-source div.type.http input.url"))).sendKeys(feedUrl);
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("div.single-feed-config form.catalog-source div.submit input")).click();
//        Thread.sleep(5000);
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        assertTrue("Feedimport fehlgeschlagen",importSuccessShown());

        //Format
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.catalog-format div.submit")));
        driver.findElement(By.cssSelector("div.catalog-format div.submit")).click();
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-format div.response-messages")));

        //Save
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.save-settings div.submit")));
        driver.findElement(By.cssSelector("div.save-settings div.submit")).click();
        waitLong.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.feed-overview")));

        //Delete Shop
        driver.findElement(By.cssSelector("ul.category-navigation a[href=\"#setup\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.settings a.button.delete"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.body a.button.okay"))).click();

        //Logout
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.category-context")));
        driver.findElement(By.cssSelector("div.navigation-service a.button[href=\"#\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.navigation-service a[href=\"/service/logout\"]"))).click();

        Thread.sleep(5000);
        driver.quit();
    }

    public static boolean importSuccessShown(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        return driver.findElement(By.cssSelector("div.catalog-source div.response-messages")).findElements(By.cssSelector("p.type-success")).size() == 1;
    }

}