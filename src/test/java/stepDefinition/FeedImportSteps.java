package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;
import view.Utils;

import static org.junit.Assert.assertTrue;

public class FeedImportSteps {

    private WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),10);
    private WebDriverWait waitLong = new WebDriverWait(BrowserDriver.getCurrentDriver(),600);
    private WebDriver driver = BrowserDriver.getCurrentDriver();

    @Given("^I click on import feed$")
    public void clickOnFeedImport(){
        driver.findElement(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]")).click();
        Utils.wait(2000);
    }

    @Given("^I select HTTP and put in the feed url \"(.*?)\"$")
    public void addHttpSource(String url){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source label.type")));
        Select typeSelect = new Select(driver.findElement(By.cssSelector("form.catalog-source select.type")));
//        Utils.wait(1000);
        typeSelect.selectByValue("http");
//        Utils.wait(1000);
//        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.catalog-source div.type.http input.url")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.catalog-source div.type.http input.url"))).sendKeys(url);
//        Utils.wait(1000);
    }

    @Given("^I click on test feed$")
    public void clickOnTestFeed(){
        driver.findElement(By.cssSelector("div.single-feed-config form.catalog-source div.submit input")).click();
    }

    @Given("^the feed is imported successfully$")
    public void testFeedResponse(){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        assertTrue("Feedimport fehlgeschlagen",importSuccessShown(waitLong,driver));
    }

    private static boolean importSuccessShown(WebDriverWait waitLong, WebDriver driver){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        return driver.findElement(By.cssSelector("div.catalog-source div.response-messages")).findElements(By.cssSelector("p.type-success")).size() == 1;
    }
}
