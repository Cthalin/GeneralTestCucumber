import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeLauncher {

    public static void main(String[] args) throws InterruptedException {
        ChromeOptions capabilities = new ChromeOptions();;
        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
//        System.setProperty("webdriver.chrome.driver","C:\\\\Users\\Erik\\Projects\\Selenium\\Driver");
        capabilities.setCapability("marionette", true);
        String url = "https://release.go.channelpilot.com";
        String user = "release_1080@channelpilot.com";

        driver.get("https://release.go.channelpilot.com");
        String title = driver.getTitle();
        System.out.println("The page's title is " + title);
        System.out.println("The title is " + title.length() + " characters long");
        String actualUrl = driver.getCurrentUrl();
        if (actualUrl.equals(url)) {
            System.out.println("Verification successful - correct Url is opened");
        } else {
            System.out.println("Verification failed - incorrect Url is opened");
            System.out.println("Current Url is: " + actualUrl);
        }

        String source = driver.getPageSource();
        System.out.println("The page source's total length is " + source.length());

        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(user);
        WebElement parent = driver.findElement(By.className("password"));
        parent.findElement(By.name("password")).click();
        Thread.sleep(5000);
        driver.quit();
    }

}