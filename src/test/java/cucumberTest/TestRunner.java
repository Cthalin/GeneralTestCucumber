package cucumberTest;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"html:target/cucumber", "json:target/cucumber.json"},
        features = "src/test/resources/web",
        glue = {"stepDefinition"},
        tags = {"@General"},
//         dryRun = true,
        monochrome = true,
        strict = true)
public class TestRunner {

}
