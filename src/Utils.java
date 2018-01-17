import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {

    public static void login(WebDriverWait wait, WebDriver driver, TestData testData){
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(testData.user);
        WebElement parent = driver.findElement(By.className("password"));
        parent.findElement(By.name("password")).sendKeys(testData.passwd);
        driver.findElement(By.cssSelector("form.login div.submit input")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ready")));
        driver.findElement(By.cssSelector("nav.start a[href=\"/start/shops\"]")).click();
    }

    public static void logout(WebDriverWait wait, WebDriver driver){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.category-context")));
        driver.findElement(By.cssSelector("div.navigation-service a.button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.navigation-service a[href=\"/service/logout\"]"))).click();
    }
}
