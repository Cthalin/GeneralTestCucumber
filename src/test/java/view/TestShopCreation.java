package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestShopCreation {

    public static void createShop(WebDriverWait wait, WebDriver driver, TestData testData) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.shops[href=\"#shops\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("a[href=\"/start/shops/new\"")))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.title")));
        driver.findElement(By.cssSelector("input.title")).sendKeys(testData.shopTitle);
        driver.findElement(By.cssSelector("input.url")).sendKeys(testData.shopTitle);
        driver.findElement(By.cssSelector("input.is-active")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("input.theme")).click();
    }

    public static void deleteShop(WebDriverWait wait, WebDriver driver){
        driver.findElement(By.cssSelector("div.menu-bar a.button.start")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.category-navigation a[href=\"#setup\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.row.settings a.button.delete"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.body a.button.okay"))).click();
    }
}
