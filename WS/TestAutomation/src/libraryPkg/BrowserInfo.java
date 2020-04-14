package libraryPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserInfo 
{
	static WebDriver driver;
	public static WebDriver OpenBrowser(String BrowserName, String URL)
	{
		if(BrowserName.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "resources//chromedriver.exe");
			driver = new ChromeDriver();
		}		
		driver.manage().window().maximize();	
		driver.get(URL);
		return driver;
	}
}
