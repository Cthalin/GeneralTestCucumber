package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;
import view.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FeedExportSteps {
    private WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),10);
    private WebDriverWait waitLong = new WebDriverWait(BrowserDriver.getCurrentDriver(),600);
    private WebDriverWait waitShort = new WebDriverWait(BrowserDriver.getCurrentDriver(),2);
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

    @Given("^I select the test shop named \"(.*?)\"$")
    public void selectTestShop(String shopTitle){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.row.shop-selector > div > a"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.shop-selector a[title^=\""+shopTitle+"\"]"))).click();
    }

    @Given("^I check if channel \"(.*?)\" is already there$")
    public void checkChannelPresence(String title){
        waitShort.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.twelve.columns.channel-overview")));
        List<WebElement> bob = driver.findElements(By.cssSelector("div.twelve.columns.channel-overview a[title*=\""+title+"\"]"));
        assertTrue("Intended channel is already there", bob.isEmpty());
    }

    @Given("^I click on add channel$")
    public void clickOnAddChannel(){
        Utils.wait(2000);
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.row.channels a.add-channel")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.channels a.add-channel"))).click();
    }

    @Given("^Channel Selection is opened$")
    public void channelSelOpened(){
        assertTrue(driver.findElement(By.cssSelector("div.twelve.columns.channel-browser")).isDisplayed());
    }

    @Given("^I click on channel \"(.*?)\"$")
    public void clickOnChannel(String ref){
        waitLong.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.channels a[href=\""+ref+"\"]"))).click();
    }

    @Given("^given channel \"(.*?)\" is selected$")
    public void checkChannelLogo(String yourChannelLogo){
        WebElement channelLogoWE = driver.findElement(By.cssSelector("div.app.pcs-manager div.logo > img[src]"));
        assertTrue("Wrong Channel selected",channelLogoWE.getAttribute("src").contains(yourChannelLogo));
    }

    @Given("^I click on add$")
    public void clickOnAdd(){
        driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.row > div > div.layer > div.row.context > div.display > div.general > div.links > div")).click();
    }

    @Given("^the channel \"(.*?)\" is added$")
    public void checkChannelAdded(String title){
        assertTrue("Intended channel not added",checkAddedChannel(title));
    }

    private boolean checkAddedChannel(String title){
        waitShort.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a")));
        return driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a")).findElements(By.cssSelector("a[title=\""+title+"\"]")).size() == 0;
    }

    @Given("^I click on start$")
    public void cickOnStart(){
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("div.menu-bar a.button.start")))).click();
    }

    @Given("^I set the channel \"(.*?)\" to active$")
    public void setChannelActive(String title){
        //Open channel
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a[title=\""+title+"\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div:nth-child(2) > div > fieldset > label > input"))).click();
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div.twelve.columns > div.submit")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div.twelve.columns > div.submit"))).click();
        Utils.wait(5000);
        System.out.println("Should be activated");
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > nav > div")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.row > div.twelve.columns.header > div.back-to-overview > a"))).click();
        Utils.wait(120000); //Wait for feed update
    }

    @Given("^I check the checksum of my export feed \"(.*?)\" \"(.*?)\" on \"(.*?)\"$")
    public void getExportChecksum(String title, String testSum, String channelFile) throws IOException, NoSuchAlgorithmException {
        //Open channel
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a[title=\""+title+"\"]"))).click();
        //Get Exportfeed URL
        String exportFeedUrl = driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.row > div.twelve.columns.header > div.details > span.fact.export-url > input[type=\"text\"]")).getAttribute("value");
        System.out.println(exportFeedUrl);
        Utils.wait(3000);
        driver.navigate().to(exportFeedUrl);
        Utils.wait(3000);
        System.out.println("Should be downloaded");
        driver.navigate().back();

        MessageDigest md = MessageDigest.getInstance("MD5");
        String shopTitle = driver.findElement(By.cssSelector("body > div.page > div > div.row.shop-selector > div > h1")).getText();

        String filePath = getFilePath(shopTitle,channelFile);
        File f=new File(filePath);
        InputStream is=new FileInputStream(f);
        byte[] buffer=new byte[8192];
        int read=0;
        while( (read = is.read(buffer)) > 0)
            md.update(buffer, 0, read);
        byte[] md5 = md.digest();
        BigInteger bi=new BigInteger(1, md5);
        String output = bi.toString(16);
        System.out.println(output+"  "+filePath);

        assertTrue("Checksum invalid", output.equals(testSum));
    }

    private String getFilePath(String shopTitle, String channelFile){
        String filePath;
        if(System.getProperty("os.name").startsWith("Windows")) {
            filePath = "C:\\Users\\Erik\\Downloads\\"+shopTitle+"_"+channelFile+".csv";
        } else {
            filePath = "$HOME/Downloads/"+shopTitle+"_"+channelFile+".csv";
        }
        return filePath;
    }

}
