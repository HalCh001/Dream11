package Dream11.IPL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Resources.LoggerClass;
import Dream11.IPL.Dream11Web;

public class RealTimeData extends Dream11Web 
{
	
	static final Logger log=LoggerClass.Configure(RealTimeData.class);
	static int NoOfMatch=1;
	
	//UPDATING Total POINTS:
	public static ArrayList<String> updateExcelWithRealTimePlayerPoints(WebDriver wb) throws InterruptedException, IOException
	
	{
		HashMap<String,HashMap<String,String>> AllPlayersPoints= new HashMap<String,HashMap<String,String>>();
		
		//Update Credit points:
		HashMap<String, String[]> AllPlayersMap= new HashMap<String,String[]>();
		
		log.info("NO Of Games Next day: "+NoOfMatch);
		
		for(int iMatch=1; iMatch<=NoOfMatch; iMatch++)
		{
		wb=InitiateDriver();
		AllPlayersMap=getPlayerCreditsFromDream11Site(wb,iMatch);		
		updateExcelWithNextMatchPlayerCredits(AllPlayersMap);
		wb.quit();
		}

		
		//Get Live player Points:
		wb=InitiateDriver();
		AllPlayersPoints= getPlaying11PointsFromDream11Site(wb);
		int SheetNo=0;
		int row=1;
		
		//updating sheet with all details:
		
		ArrayList<String> TeamNames= new ArrayList<String>();
		
		
		
		
		for(Entry<String, HashMap<String,String>> entry : AllPlayersPoints.entrySet())
			{
				File F1= new File(ExcelLocation);
				
				FileInputStream Fis= new FileInputStream(F1);
				HSSFWorkbook x1= new HSSFWorkbook(Fis);
				
						
		    	 HSSFSheet Y1= x1.getSheetAt(SheetNo);
		    	 x1.setSheetName(SheetNo, entry.getKey());
		    	 System.out.println("Changed Sheet"+SheetNo+" Name to : "+entry.getKey());
		    	 
		    	 if(Y1.getPhysicalNumberOfRows()>=12)			//clearing existing data
		    	 {
		    		 System.out.println("Cleared previous records");
		    		 for(int iCnt=1;iCnt<Y1.getPhysicalNumberOfRows();iCnt++)
		    		 {Y1.removeRow(Y1.getRow(iCnt));}
		    	 }
		    	 
		    	 
		    	 TeamNames.add(x1.getSheetName(SheetNo)); //to be used while calling Mail Dream11
		    	 		    	 		    	 
		    	 for(Entry<String,String> entry1 : entry.getValue().entrySet())
					{
		    		 	String[] PlayerInfo= getPlayerInfo(entry.getKey(), entry1.getKey());		    		 	
		    		 	HSSFRow sheetrow = Y1.getRow(row);		    		 	
		    		 	
		    		 	if(sheetrow == null)
			    		 	 {
			    		 		sheetrow = Y1.createRow(row);
			    		 	 }		    		 			    		 	
		    		 	 sheetrow.createCell((short) 0).setCellValue(entry1.getKey());
		    		 	 sheetrow.createCell((short) 1).setCellValue(PlayerInfo[0]);
		    		 	 sheetrow.createCell((short) 2).setCellValue(entry1.getValue());
		    		 	 sheetrow.createCell((short) 3).setCellValue(PlayerInfo[1]);
		    		 	 sheetrow.createCell((short) 4).setCellValue(entry.getKey());
				    	 row++;
					     }
		    	 		row=1;
		    	 		SheetNo++;
		    	 		FileOutputStream fos=new FileOutputStream(F1);
		    	 		x1.write(fos);		    	 		
		    	 		fos.close();
					}
		
		return TeamNames;
			}	
		
	//UPDATING CREDIT POINTS:
	public static Boolean updateExcelWithNextMatchPlayerCredits(HashMap<String, String[]> TeamDetails) throws InterruptedException, IOException
	
	{
		File F1= new File(ExcelLocation);
		FileInputStream Fis= new FileInputStream(F1);
		HSSFWorkbook x1= new HSSFWorkbook(Fis);
		
		
		for(Entry<String, String[]> entry : TeamDetails.entrySet())
			{			
				String[] str=entry.getValue();
				int updated=0;
										
		    	 try
		    	 {		    		 
		    		 HSSFSheet Sh= x1.getSheet("IPL_"+str[0]);	
		    		 
		    		 if(Sh.getPhysicalNumberOfRows()>0)  //Check if data already availble, add if not present
		    		 {
			    		 for(int iCnt= 1;iCnt<Sh.getPhysicalNumberOfRows(); iCnt++)
			    			{
			    				String Name= Sh.getRow(iCnt).getCell((short) 0).getStringCellValue();
			    				if(entry.getKey().contains(Name))
			    					{
				    					Sh.getRow(iCnt).getCell((short) 2).setCellValue(str[2]);
				    					updated++;
			    					}
			    			}
			    		 
		    		}
		    		if(updated==0){
					HSSFRow sheetrow= Sh.createRow(Sh.getPhysicalNumberOfRows());					
					sheetrow.createCell((short) 0).setCellValue(entry.getKey());
					sheetrow.createCell((short) 1).setCellValue(str[1]);
					sheetrow.createCell((short) 2).setCellValue(str[2]);
					sheetrow.createCell((short) 3).setCellValue(str[0]);}}
					

		    		 

		    	 
		    	 catch(Exception e)
		    	 {
		    		 e.printStackTrace();
		    		 e.getMessage();
		    		 HSSFSheet Sh1= x1.createSheet("IPL_"+str[0]);
		    		 HSSFRow sheetrow= Sh1.createRow(0);
		    		 
		    		 sheetrow.createCell((short) 0).setCellValue("PLayerName");
	    		 	 sheetrow.createCell((short) 1).setCellValue("PLayerType");
	    		 	 sheetrow.createCell((short) 2).setCellValue("PLayerCredit");
	    		 	 sheetrow.createCell((short) 3).setCellValue("PLayerTeam");
	    		 	 	    		 	 
		    		 
		    		 sheetrow=Sh1.createRow(Sh1.getPhysicalNumberOfRows());
		    		 log.info("Creating Sheet Named : "+str[0]);
		    		 
		    		 sheetrow.createCell((short) 0).setCellValue(entry.getKey());
	    		 	 sheetrow.createCell((short) 1).setCellValue(str[1]);
	    		 	 sheetrow.createCell((short) 2).setCellValue(str[2]);
	    		 	 sheetrow.createCell((short) 3).setCellValue(str[0]);
		    	 }
			}
				FileOutputStream fos=new FileOutputStream(F1);
				x1.write(fos);		    	 		
				fos.close();
				return true;			
			}
	
	//Collecting Live points from Dream11.com:		
	public static HashMap<String,HashMap<String,String>> getPlaying11PointsFromDream11Site(WebDriver wb) throws InterruptedException, IOException
	{
		wb.get("https://www.dream11.com/");
		Dream11Web Objects= PageFactory.initElements(wb, Dream11Web.class);
		WebDriverWait wt = new WebDriverWait(wb,30);
		
		HashMap<String, String> Team1= new HashMap<String,String>();
		HashMap<String,String> Team2= new HashMap<String,String>();
		HashMap<String,HashMap<String,String>> Fullteam= new HashMap<String,HashMap<String,String>>();

		Objects.getLogin().click();
		Objects.getSignin().click();
		Objects.getEmail().sendKeys("chiranjit.halder@gmail.com");
		Objects.getPassword().sendKeys("sca1p2md3");
		Objects.getSubmit().click();
		
		wt.until(ExpectedConditions.presenceOfElementLocated(Dream11Web.getMyContests())).click();
		
		try
		{
			wt.until(ExpectedConditions.presenceOfElementLocated(Dream11Web.ClickOnLiveTab())).click();
			Thread.sleep(3000);
			//WebElement Match= wb.findElement(Dream11Web.getMatch());
			//Match.click();
			List<WebElement> Contest1= wb.findElements(Dream11Web.getContests());
			Contest1.get(0).click();
		}
	catch(Exception e)
		{
			wt.until(ExpectedConditions.presenceOfElementLocated(Dream11Web.ClickOnResultsTab())).click();
			Thread.sleep(3000);
			List<WebElement> Contest1= wb.findElements(Dream11Web.getContests());
			Contest1.get(0).click();
		}

		Thread.sleep(5000);		
		Objects.SelectLeaderBoardContest().click();
		
		Thread.sleep(5000);
		Objects.getScore().click();
		
		Thread.sleep(2000);
		
		String TeamName1 = null,TeamName2 = null;
		
		List<WebElement> allRow= Objects.getPointsTableRow();
		log.info("Total No of Rows: "+allRow.size());
					
		List<WebElement> PlayersName=Objects.getPoints_Players();
		List<WebElement> PlayersPoint=Objects.getPoints();
		
		for(int i=0; i<11;i++ )
		{
			TeamName1= getTeamName(PlayersName.get(i).getText());
			System.out.println("Team Name Found: "+TeamName1+" for Player: "+PlayersName.get(i).getText());
			Team1.put(PlayersName.get(i).getText(), PlayersPoint.get(i).getText());
		}
		
		for(int i=11; i<22;i++ )
		{
			TeamName2= getTeamName(PlayersName.get(i).getText());
			System.out.println("Team Name Found: "+TeamName1+" for Player: "+PlayersName.get(i).getText());
			Team2.put(PlayersName.get(i).getText(), PlayersPoint.get(i).getText());
		}
		
		Fullteam.put(TeamName1, Team1);
		Fullteam.put(TeamName2, Team2);
		
		
		log.info("Record Size "+Fullteam.entrySet().size());
		wb.quit();
		return Fullteam;

	}
	
	private static String getTeamName(String PlayerName) throws IOException 
	
	{
		Boolean TeamFound=false;
		String SheetName = null;
		for(int i=2; i<=9; i++)
		{
			File F1= new File(ExcelLocation);			
			FileInputStream Fis= new FileInputStream(F1);
			HSSFWorkbook x1= new HSSFWorkbook(Fis);									
	    	HSSFSheet Y1= x1.getSheetAt(i);
	    	SheetName=x1.getSheetName(i);
	    	//System.out.println("Searching in: "+SheetName); 
	    	 for(int j=1; j<Y1.getPhysicalNumberOfRows();j++)
	    	 {
	    		 if(Y1.getRow(j).getCell((short) 0).getStringCellValue().contains(PlayerName))
	    		 {
	    			 TeamFound=true;
	    			 break;
	    		 }
	    	 }
			
	    	 if(TeamFound)
	    	 {
	    		 break;
	    	 }
	    	 
		}
		return SheetName.substring(4);
	}

	//Collecting credit points from Dream11.com:
	public static HashMap<String, String[]> getPlayerCreditsFromDream11Site(WebDriver wb, int iMatch) throws InterruptedException
	{
		wb.get("https://www.dream11.com/");
		wb.manage().window().maximize();
		WebDriverWait wt = new WebDriverWait(wb,30);
		
		Dream11Web Objects= PageFactory.initElements(wb, Dream11Web.class);
		wt.until(ExpectedConditions.visibilityOf(Objects.getLogin())).click();
		wt.until(ExpectedConditions.visibilityOf(Objects.getSignin())).click();
		wt.until(ExpectedConditions.visibilityOf(Objects.getEmail())).sendKeys("chiranjit.halder@gmail.com");
		wt.until(ExpectedConditions.visibilityOf(Objects.getPassword())).sendKeys("sca1p2md3");
		wt.until(ExpectedConditions.visibilityOf(Objects.getSubmit())).click();
		

//Checking count of 'No of match fixes' on first go only		
		Thread.sleep(1000);
		if(iMatch==1){NoOfMatch= getNoOfMatchesNextDay(wb);}

		

//Clicking on Selected Match:
		Thread.sleep(10000);
		
		List<WebElement> Matches=Objects.getMatches();		
		Matches.get(iMatch-1).click();
		
		Thread.sleep(3000);
		
		try
		{
			Objects.getMyTeams().click();
		}
		
		catch(Exception e)
		{
			System.out.println("My teams links not present. Since one team is already created");
		}
		
		//Clicking on Create Team	
		wt.until(ExpectedConditions.presenceOfElementLocated(Dream11Web.getCreateTeam())).click();
		
		HashMap<String, String[]> AllPlayersMap= new HashMap<String,String[]>();
		

	//Get All Wicketkeeper credits
		Thread.sleep(2000);
		List<WebElement> TeamName1 = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getTeam()));
		List<WebElement> AllPlayersWK =wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayersName()));
		List<WebElement> AllCreditsWK = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayerCredits()));

		
		for(int i=0;i<AllPlayersWK.size();i++)
		{
			String str[]={TeamName1.get(i).getText().substring(0, 3),"WK",AllCreditsWK.get(i).getText()};
			AllPlayersMap.put(AllPlayersWK.get(i).getText(),str);
			
		}
		
	//Get All Batsman credits	
			
		WebElement BAT= Objects.getBatIcon();
		BAT.click();
		Thread.sleep(2000);
		List<WebElement> TeamName2 = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getTeam())); 
		List<WebElement> AllPlayersBat = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayersName())); 				
		List<WebElement> AllCreditsBat = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayerCredits()));
		
		for(int i=0;i<AllPlayersBat.size();i++)
		{
			String str1[]={TeamName2.get(i).getText().substring(0, 3),"Bat",AllCreditsBat.get(i).getText()};
			AllPlayersMap.put(AllPlayersBat.get(i).getText(),str1);
		}

	
	//Get All Alrounder credits	

			WebElement AL= Objects.getARIcon();
			AL.click();
			Thread.sleep(2000);
			List<WebElement> TeamName3 = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getTeam()));
			List<WebElement> AllPlayersAL = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayersName()));
			List<WebElement> AllrounderCredit =wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayerCredits()));

			
			for(int i=0;i<AllPlayersAL.size();i++)
			{

				String[] str2= {TeamName3.get(i).getText().substring(0, 3),"AL",AllrounderCredit.get(i).getText()};
				AllPlayersMap.put(AllPlayersAL.get(i).getText(), str2);
			}
	
		//Get All Bowler credits	

		WebElement BOWL= Objects.getBowlIcon();
		BOWL.click();
		Thread.sleep(2000);
		List<WebElement> TeamName4 = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getTeam()));
		List<WebElement> AllBowlers = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayersName()));	
		List<WebElement> AllBowlersCredits = wt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Dream11Web.getPlayerCredits()));
		
		for(int i=0;i<AllBowlers.size();i++)
		{
			String[] str3={TeamName4.get(i).getText().substring(0, 3),"Bowl",AllBowlersCredits.get(i).getText()};
			AllPlayersMap.put(AllBowlers.get(i).getText(),str3);
		}

		wb.quit();		
		return AllPlayersMap;
		
		
	}
	
	//Getting No of Matches Next day:
	private static int getNoOfMatchesNextDay(WebDriver wb) throws InterruptedException 
	{
		WebDriverWait wt = new WebDriverWait(wb,20);
		wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@href,'/cricket/leagues/Indian T20 League/811')]/div[1]/div/div/div[2]/div/div")));		
		List<WebElement> TimerTime= wb.findElements(By.xpath("//*[contains(@href,'/cricket/leagues/Indian T20 League/811')]/div[1]/div/div/div[2]/div/div"));
		Thread.sleep(3000);
		for(WebElement Time: TimerTime)
		{
			try{
			 if( Integer.parseInt(Time.getText().substring(0, 2)) < 23)
			 {
				 NoOfMatch++; }
			 }
			catch(Exception e){e.getMessage();};
		}
		return NoOfMatch-1;
	}

	//Manipulating All data together:
	public static String[] getPlayerInfo(String TeamName, String PlayerName) throws IOException
	{
		File F1= new File(ExcelLocation);

		
		FileInputStream Fis= new FileInputStream(F1);
		HSSFWorkbook x1= new HSSFWorkbook(Fis);
		HSSFSheet Y1= x1.getSheet("IPL_"+TeamName);
		String Type = null,Credit=null;

		
		for(int iCnt= 1;iCnt<Y1.getPhysicalNumberOfRows(); iCnt++)
		{
			String Name= Y1.getRow(iCnt).getCell((short) 0).getStringCellValue();
			if(PlayerName.contains(Name))
			{
				Type= Y1.getRow(iCnt).getCell((short) 1).getStringCellValue();
				Credit= Y1.getRow(iCnt).getCell((short) 2).getStringCellValue();
			}
		}
		String[] str={Type,Credit};					
		return str;

	}
}