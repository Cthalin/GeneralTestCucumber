package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;
import view.Utils;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FeedExportSteps {
    private WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),10);
    private WebDriverWait waitLong = new WebDriverWait(BrowserDriver.getCurrentDriver(),600);
    private WebDriverWait waitShort = new WebDriverWait(BrowserDriver.getCurrentDriver(),2);
    private WebDriver driver = BrowserDriver.getCurrentDriver();
    private JavascriptExecutor je = (JavascriptExecutor) driver;
    private String shopId;
    private int itemCount;
    private DatabaseSteps dbsteps = new DatabaseSteps();

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
    public void selectTestShop(String shopTitle) throws SQLException, ClassNotFoundException {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.row.shop-selector > div > a"))).click();
        shopId = getShopId(shopTitle);
//        itemCount = getChannelItemCount(shopTitle);
//        Connection con = dbsteps.establishDbConnection();
////        dbsteps.createStatementForChecksum(con,shopId);
//        itemCount = dbsteps.createStatementForItemCount(con,shopId);
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

    @Given("^I open the export channel \"(.*?)\"$")
    public void openExportChannel(String title){
        //Open channel
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a[title=\""+title+"\"]"))).click();
    }

    @Given("^I set the channel to active$")
    public void setChannelActive(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div:nth-child(2) > div > fieldset > label > input"))).click();
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div.twelve.columns > div.submit")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.category-context.settings > div.context.setup > form > div.twelve.columns > div.submit"))).click();
        Utils.wait(5000);
        System.out.println("Should be activated");
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("body > nav > div")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.row > div.twelve.columns.header > div.back-to-overview > a"))).click();
        Utils.wait(120000); //Wait for feed update
    }

    @Given("^I check the export feed \"(.*?)\" on \"(.*?)\"$")
    public void checkExportFeed(String title, String channelFile) throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        //Open channel
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div > div.twelve.columns.channel-overview > ul a[title=\""+title+"\"]"))).click();
        //Get Exportfeed URL
        String exportFeedUrl = driver.findElement(By.cssSelector("body > div.page > div > div.app.pcs-manager > div > div.row > div.twelve.columns.header > div.details > span.fact.export-url > input[type=\"text\"]")).getAttribute("value");
//        System.out.println(exportFeedUrl);
//        Utils.wait(3000);
//        driver.navigate().to(exportFeedUrl);
        try{
            driver.get(exportFeedUrl);
        }catch (TimeoutException timeoutException){
            System.out.println("Timeout "+timeoutException);
        }

        Utils.wait(3000);
        System.out.println("Should be downloaded");
        driver.navigate().back();

//        MessageDigest md = MessageDigest.getInstance("MD5");
        String shopTitle = driver.findElement(By.cssSelector("body > div.page > div > div.row.shop-selector > div > h1")).getText();

        Connection con = dbsteps.establishDbConnection();
        itemCount = dbsteps.createStatementForItemCount(con,shopId);
        String filePath = getFilePath(shopTitle,channelFile);

        //Count the items in export feed
            int rows = count(filePath)-1; //subtract 1 for header
            System.out.println("Rows: "+rows);
            System.out.println("Item count: "+itemCount);
            assertTrue(rows == itemCount);
            System.out.println("All items configured for this channel are exported");
        File f = new File(filePath);
        f.delete();

        //Calculate md5 sum
//        File f=new File(filePath);
//        InputStream is=new FileInputStream(f);
//        byte[] buffer=new byte[8192];
//        int read=0;
//        while( (read = is.read(buffer)) > 0)
//            md.update(buffer, 0, read);
//        byte[] md5 = md.digest();
//        BigInteger bi=new BigInteger(1, md5);
//        String output = bi.toString(16);
//        System.out.println(output+"  "+filePath);
//        assertTrue("Checksum invalid", output.equals(testSum));
    }

    private String getFilePath(String shopTitle, String channelFile){
        String filePath;
        if(System.getProperty("os.name").startsWith("Windows")) {
            filePath = "C:\\Users\\Erik\\Downloads\\"+shopTitle+"_"+channelFile+".csv";
        } else {
            shopTitle = shopTitle.toLowerCase();
            String home = System.getProperty("user.home");
            filePath = home+"/Downloads/"+shopTitle+"_"+channelFile+".csv";
        }
        return filePath;
    }

    private String getShopId(String shopTitle){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.shop-selector a[title^=\""+shopTitle+"\"]")));
        shopId = driver.findElement(By.cssSelector("div.row.shop-selector a[title^=\""+shopTitle+"\"]")).getAttribute("data-id");
        System.out.println("ShopID: "+shopId);
        return shopId;
    }

    private int getChannelItemCount(String shopTitle){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.shop-selector a[title^=\""+shopTitle+"\"]")));
        itemCount = Integer.valueOf(driver.findElement(By.cssSelector("div.row.shop-selector a[title^=\""+shopTitle+"\"] span.offers-online")).getText());
        return itemCount;
    }

    public int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    @Given("^I just wait$")
    public void justWait(){
        Utils.wait(30000);
    }
}
