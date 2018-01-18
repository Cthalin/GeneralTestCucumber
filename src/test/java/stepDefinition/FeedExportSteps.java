package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;

import static org.junit.Assert.assertTrue;

public class FeedExportSteps {
    private WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),10);
    private WebDriverWait waitLong = new WebDriverWait(BrowserDriver.getCurrentDriver(),600);
    private WebDriver driver = BrowserDriver.getCurrentDriver();
    private JavascriptExecutor je = (JavascriptExecutor) driver;

    @Given("^I click on PSM$")
    public void clickOnPsm(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.menu-bar a.button.price-comparison-sites"))).click();
    }

    @Given("^the PSM is opened$")
    public void psmIsOpened(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.channels")));
        assertTrue(driver.findElement(By.cssSelector("div.row.channels")).isDisplayed());
    }
}
