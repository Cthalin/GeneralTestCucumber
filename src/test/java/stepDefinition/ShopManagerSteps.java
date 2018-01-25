package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;
import view.Utils;

import static org.junit.Assert.assertTrue;

public class ShopManagerSteps {

    private WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),10);
    private WebDriver driver = BrowserDriver.getCurrentDriver();
    private JavascriptExecutor je = (JavascriptExecutor) driver;

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

    @Given("^I delete the shop$")
    public void deleteShop(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.settings a.button.delete"))).click();
        Utils.wait(3000);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.body a.button.okay"))).click();
        Utils.wait(3000);
    }

    @Given("^I check if shop named \"(.*?)\" is available$")
    public void searchForTestShop(String shopName){
        Utils.wait(2000);
        assertTrue("No more test shop available",driver.findElement(By.cssSelector("a[title^=\""+shopName+"\"]")).isDisplayed());
    }

    @Given("^a TestShop is selected$")
    public void selectTestShop(){
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("a[title^=\"TestShop\"]")))).click();
    }

    @Given("^I click on set up filter$")
    public void clickOnSetupFilter(){
        driver.findElement(By.cssSelector("ul.category-navigation a[href=\"#filter\"]")).click();
        Utils.wait(2000);
    }

    @Given("^I click on add filter$")
    public void clickOnAddFilter(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.twelve.columns.filter-overview  a.filter-add"))).click();
    }

    @Given("^I put in a filter name \"(.*?)\" and save it$")
    public void enterFilterName(String name){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.twelve.columns.filter-add fieldset > label > input[type=\"text\"]"))).sendKeys(name);
        driver.findElement(By.cssSelector("div.twelve.columns.filter-add div.submit > input[type=\"submit\"]")).click();
        Utils.wait(2000);
    }

    @Given("^I setup a filter for price$")
    public void setupPriceFilter(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.four.columns.datafield-column div.field.empty.truncate"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.data-field-selector > div[data-type=\"feed\"]"))).click();
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > div.data-field-selector > div:nth-child(6) > div.content > div:nth-child(14) > span")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.data-field-selector > div:nth-child(6) > div.content > div:nth-child(14) > span"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.two.columns.parameter-name-column > fieldset > label > input.name"))).sendKeys("test");
        Select typeSelect = new Select(driver.findElement(By.cssSelector("div.two.columns.parameter-type-column > fieldset > label > select")));
        typeSelect.selectByValue("number");
        Utils.wait(500);
        je.executeScript("$(\".CodeMirror\")[0].CodeMirror.setValue(arguments[0]);", "test > 100");
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.category-context.filter div.context.setup div.submit input.theme")));
        driver.findElement(By.cssSelector("div.category-context.filter div.context.setup div.submit input.theme")).click();
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > nav")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.shops > div.category-context.filter > div.twelve.columns.datafield-config > div.layer > div.header > a.close"))).click();
    }

    @Given("^I select the created testfilter$")
    public void selectTestFilter(){
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup fieldset > label > select.filter")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup fieldset > label > input")));
        Select typeSelect = new Select(driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup fieldset > label > select.filter")));
        typeSelect.selectByVisibleText("TestFilter");
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div.twelve.columns > div.submit")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div.twelve.columns > div.submit"))).click();
        Utils.wait(5000);
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > nav > div")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.row > div.twelve.columns.header > div.back-to-overview > a"))).click();
        Utils.wait(120000); //Wait for feed update
    }
}
