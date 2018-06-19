package Ranner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/Features/marcinstolyrest.feature"},
        format = {"json:target/marcinstoly.json", "html:target/site/marcinstoly-report-html/"}, glue = "MarcinStolyRestPackage")
public class TestRanner {
}
