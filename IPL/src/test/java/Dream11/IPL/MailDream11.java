package Dream11.IPL;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Resources.BaseClass;
import Resources.LoggerClass;


public class MailDream11 extends BaseClass
{
	static final Logger log=LoggerClass.Configure(MailDream11.class);
	static WebDriver wb;
	
	@BeforeTest
	public void OnStart() throws IOException, InterruptedException
  {
	  //wb=InitiateDriver();

  }
	
	

	@Test
	public void MailMeDream11() throws IOException, MessagingException, InterruptedException 
	{			
		//-------update all data
			ArrayList<String> Team= new ArrayList<String>();
			Team= RealTimeData.updateExcelWithRealTimePlayerPoints(wb);						
		//Today's playing teams:	
			log.info("Team1: "+Team.get(0)+" ,Team2: "+Team.get(1));			
		//Here u go..	
			PlayerManager.MailMyDream11(Team.get(0),Team.get(1));
			//PlayerManager.MailMyDream11("PNJ","BLR");
	}

	    
	 
}
	
	
	
	
		
	
		
		
		


