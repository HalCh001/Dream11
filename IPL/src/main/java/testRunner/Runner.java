package testRunner;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(features="/Users/Chiranjit/git/Dream11/IPL/Features/IPL.feature",glue={"cucumberSteps"})
public class Runner 
{
  
}
