import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFeedImport {

    public void importFeed(WebDriverWait wait, WebDriver driver, TestData testData, WebDriverWait waitLong, JavascriptExecutor je) throws InterruptedException {
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
        assertTrue("Feedimport fehlgeschlagen",importSuccessShown(waitLong,driver));
        assertFalse("Feedimport erfolgreich", importSuccessShown(waitLong,driver));

        //Format
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.catalog-format div.submit")));
        driver.findElement(By.cssSelector("div.catalog-format div.submit")).click();
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-format div.response-messages")));
        assertTrue("Formatprüfung fehlgeschlagen",formatSuccessShown(waitLong,driver));
        assertFalse("Formatprüfung erfolgreich", formatSuccessShown(waitLong,driver));

        //Save
        je.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("div.save-settings div.submit")));
        driver.findElement(By.cssSelector("div.save-settings div.submit")).click();
        waitLong.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.feed-overview")));
    }

    private static boolean importSuccessShown(WebDriverWait waitLong, WebDriver driver){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        return driver.findElement(By.cssSelector("div.catalog-source div.response-messages")).findElements(By.cssSelector("p.type-success")).size() == 1;
    }

    private static boolean formatSuccessShown(WebDriverWait waitLong, WebDriver driver){
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.catalog-source div.response-messages")));
        return driver.findElement(By.cssSelector("div.catalog-format div.response-messages")).findElements(By.cssSelector("p.type-success")).size() == 1;
    }
}
