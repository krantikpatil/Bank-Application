package com.bankapplication.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/Resources/cucumber.features",
        glue = "com.bankapplication.cucumber"
//        plugin = {"pretty", "html:target/cucumber-reports.html"},
//        monochrome = true
)
public class CucumberTestRunner {
}
