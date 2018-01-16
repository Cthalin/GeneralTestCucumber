import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChromeLauncher {
    private static WebDriver driver;
    private static WebDriverWait waitLong;
    private static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        //Start Chrome Browser driver
        ChromeOptions capabilities = new ChromeOptions();;
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
        login(testData);

        //Create new Shop
        TestShopCreation testShopCreation = new TestShopCreation();
        testShopCreation.createShop(wait,driver,testData);

        //Import Feed
        Thread.sleep(5000);
        WebElement title = driver.findElement(By.cssSelector("h1.shop-selected-title"));
        waitLong.until(ExpectedConditions.attributeToBe(title,"innerText",testData.shopTitle));
        WebElement myDynElement = (new WebDriverWait(driver,5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]")));
        myDynElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source label.type")));
        Select typeSelect = new Select(driver.findElement(By.cssSelector("form.catalog-source select.type")));
        Thread.sleep(1000);
        typeSelect.selectByValue("http");
        Thread.sleep(1000);
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.catalog-source div.type.http input.url")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.catalog-source div.type.http input.url"))).sendKeys(testData.feedUrl);
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("div.single-feed-config form.catalog-source div.submit input")).click();
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        assertTrue("Feedimport fehlgeschlagen",importSuccessShown());
        assertFalse("Feedimport erfolgreich", importSuccessShown());

        //Format
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.catalog-format div.submit")));
        driver.findElement(By.cssSelector("div.catalog-format div.submit")).click();
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-format div.response-messages")));
        assertTrue("Formatprüfung fehlgeschlagen",formatSuccessShown());
        assertFalse("Formatprüfung erfolgreich", formatSuccessShown());

        //Save
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.save-settings div.submit")));
        driver.findElement(By.cssSelector("div.save-settings div.submit")).click();
        waitLong.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.feed-overview")));

        //Export Feed
        driver.findElement(By.cssSelector("div.menu-bar a.button.price-comparison-sites")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.channels a.add-channel"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.channels a[href=\"#googleShopping\"]"))).click();
        //Use Google Shopping
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.row > div > div.layer > div.row.context > div.display > div.general > div.logo > img[src=\"https://cdn-frontend-channelpilotsolu.netdna-ssl.com/images/channels/medium/googleShopping.png\"]")));
        driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.row > div > div.layer > div.row.context > div.display > div.general > div.links > div")).click();
        assertTrue("ChannelSetup complete", testChannelSetup());

        //Clean Up & Tear Down
        //Delete Shop
        testShopCreation.deleteShop(wait,driver);

        //Logout
        logout();

        Thread.sleep(5000);
        driver.quit();
    }

    public static boolean importSuccessShown(){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        return driver.findElement(By.cssSelector("div.catalog-source div.response-messages")).findElements(By.cssSelector("p.type-success")).size() == 1;
    }

    public static boolean formatSuccessShown(){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        return driver.findElement(By.cssSelector("div.catalog-format div.response-messages")).findElements(By.cssSelector("p.type-success")).size() == 1;
    }

    public static boolean testChannelSetup(){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul > li:nth-child(2) > a")));
        return driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul > li:nth-child(2) > a")).findElements(By.cssSelector("a[title=\"googleShopping (DE)\"]")).size() == 1;
    }

    public static void login(TestData testData){
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(testData.user);
        WebElement parent = driver.findElement(By.className("password"));
        parent.findElement(By.name("password")).sendKeys(testData.passwd);
        driver.findElement(By.cssSelector("form.login div.submit input")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ready")));
        driver.findElement(By.cssSelector("nav.start a[href=\"/start/shops\"]")).click();
    }

    public static void logout(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.category-context")));
        driver.findElement(By.cssSelector("div.navigation-service a.button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.navigation-service a[href=\"/service/logout\"]"))).click();
    }
}