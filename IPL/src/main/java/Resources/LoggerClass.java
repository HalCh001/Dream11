package Resources;

import org.apache.logging.log4j.*;

public class LoggerClass {

	public static void main(String[] args) 
	{
		
	}
		public static org.apache.logging.log4j.Logger Configure(Class<?> cls)
		{
		System.setProperty("log4j.configurationFile","./log4j2.xml");
		return LogManager.getLogger(cls.getName());
		}

	}


