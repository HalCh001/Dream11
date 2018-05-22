package testRunner;

import cucumber.api.CucumberOptions;

//import cucumber.api.junit.Cucumber;

//import org.junit.runner.RunWith;
import cucumber.api.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)
@CucumberOptions(features="/Users/Chiranjit/git/Dream11/IPL/Features/IPL.feature",glue={"cucumberSteps"})
public class Runner extends AbstractTestNGCucumberTests
{
	
  
}
