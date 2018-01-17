package stepDefinition;

import cucumber.api.java.en.Given;
import view.Utils;

import static org.junit.Assert.assertTrue;

public class LoginSteps {
    private Utils utils = new Utils();

    @Given("^open the login page$")
    public void user_is_on_HomePage() throws Throwable{
        System.setProperty("browser","CHROME");
        Utils.openPage();
        assertTrue(utils.isLogInPageDisplayed());
    }

    @Given("^login with given data \"(.*?)\" and \"(.*?)\"$")
    public void login_user_with_given_data(String user, String password){
        utils.login(user,password);
    }
}
