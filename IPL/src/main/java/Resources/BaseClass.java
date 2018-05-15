package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class BaseClass 
{
	String PrjDirectory= System.getProperty("user.dir");
	//static final Logger log= LoggerClass.Configure(LoginToFacebook.class);

	public static WebDriver InitiateDriver() throws IOException
	{
		Properties prop= new Properties();
		FileInputStream fis= new FileInputStream("D:\\Automation Tool\\Selenium\\WorkSpace\\SOMS_Automation\\src\\test\\java\\SOMS\\Data.properties");
		prop.load(fis);
		//String browser= prop.getProperty("Browser");
		
		System.setProperty("webdriver.chrome.driver", "D:\\Automation Tool\\Selenium\\Installation Files\\zip version\\chromedriver_win32\\chromedriver.exe");
		WebDriver wb= new ChromeDriver();
		wb.manage().window().maximize();
		System.out.println("Inilizing Driver");
		return  wb;
		
		}
	}



