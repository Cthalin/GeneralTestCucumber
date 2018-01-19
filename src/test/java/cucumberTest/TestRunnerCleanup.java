package cucumberTest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"html:target/cucumber", "json:target/cucumber.json"},
        features = "src/test/resources/web",
        glue = {"stepDefinition"},
        tags = {"@Cleanup"},
//         dryRun = true,
        monochrome = true,
        strict = true)
public class TestRunnerCleanup {

}
