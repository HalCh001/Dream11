package cucumberSteps;

import java.io.IOException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import Resources.LoggerClass;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import Dream11.IPL.*;

public class Steps 
{
	static final Logger log=LoggerClass.Configure(Thread.currentThread().getStackTrace()[1].getClass());
	static WebDriver wb;
	
	@Given("^Open Browser$")
	public void OpenBrowser()
	{
		System.out.println("Starting up");
	}
	@Then("^Mail Dream11$")
	public void MailMeDream11() throws IOException, MessagingException, InterruptedException 
	{			
		//-------update all data
			ArrayList<String> Team= new ArrayList<String>();
			Team= RealTimeData.updateExcelWithRealTimePlayerPoints(wb);						
		//Today's playing teams:	
			log.info("Team1: "+Team.get(0)+" ,Team2: "+Team.get(1));			
		//Here u go..	
			PlayerManager.MailMyDream11(Team.get(0),Team.get(1));
			//PlayerManager.MailMyDream11("HYD","KOL");
	}
}
