package testRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features="/Users/Chiranjit/git/Dream11/IPL/Features/IPL.feature",glue={"cucumberSteps"})
public class Runner extends AbstractTestNGCucumberTests
{
	
  
}
