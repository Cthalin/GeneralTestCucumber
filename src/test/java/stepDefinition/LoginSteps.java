package stepDefinition;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserDriver;
import view.Utils;

import static org.junit.Assert.assertTrue;

public class LoginSteps {
    private Utils utils = new Utils();

    @Given("^open the login page$")
    public void user_is_on_HomePage() {
        System.setProperty("browser","CHROME");
        Utils.openPage();
        assertTrue(Utils.isLogInPageDisplayed());
    }

    @Given("^login with given data \"(.*?)\" and \"(.*?)\"$")
    public void login_user_with_given_data(String user, String password){
        Utils.login(user,password);
    }

    @Given("^I see the dashboard$")
    public void isDashboardShown(){
        WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(),20);
        wait.until(ExpectedConditions.elementToBeClickable(BrowserDriver.getCurrentDriver().findElement(By.cssSelector("div.ready"))));
        assertTrue(BrowserDriver.getCurrentDriver().findElement(By.cssSelector("div.app.dashboard")).isDisplayed());
    }
}
