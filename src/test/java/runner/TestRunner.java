package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/CustomerPortal.feature",
glue = { "tests" },
tags = { "@Demo2" },
format = { "pretty", 
		"html:target/site/cucumber-pretty", },
plugin = { "json:target/cucumber-reports/cucumber.json"})

public class TestRunner {

}