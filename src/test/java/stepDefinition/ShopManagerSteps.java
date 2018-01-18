package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;
import view.Utils;

import static org.junit.Assert.assertTrue;

public class ShopManagerSteps {

    private WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),10);
    private WebDriver driver = BrowserDriver.getCurrentDriver();

    @Given("^I click on shop management")
    public void clickOnShopManagement(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("nav.start a[href=\"/start/shops\"]"))).click();
    }

    @Given("^shop management is open$")
    public void shopManagementOpened(){
        assertTrue(driver.findElement(By.cssSelector("div.app.shops")).isDisplayed());
    }

    @Given("^I click on shop selection$")
    public void openShopSelection(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.shops[href=\"#shops\"]"))).click();
    }

    @Given("^I click on new shop$")
    public void clickOnNewShop(){
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("a[href=\"/start/shops/new\"")))).click();
    }

    @Given("^I enter new shop name \"(.*?)\", url \"(.*?)\" and set active$")
    public void enterNewShopDetails(String shopTitle,String shopUrl){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.title")));
        driver.findElement(By.cssSelector("input.title")).sendKeys(shopTitle+System.currentTimeMillis());
        driver.findElement(By.cssSelector("input.url")).sendKeys(shopUrl);
        driver.findElement(By.cssSelector("input.is-active")).click();
    }

    @Given("^I save the shop$")
    public void saveShop() {
        driver.findElement(By.cssSelector("input.theme")).click();
        Utils.wait(2000);
    }

    @Given("^the shop is created successfully$")
    public void assessShopCreation() {
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.settings a.button.delete")));
        Utils.wait(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]")));
        assertTrue(driver.findElement(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]")).getAttribute("class") == null
        || driver.findElement(By.cssSelector("ul.category-navigation a[href=\"#catalog\"]")).getAttribute("class").equals(""));
    }


}
