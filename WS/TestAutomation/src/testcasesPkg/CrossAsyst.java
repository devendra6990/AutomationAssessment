package testcasesPkg;

import java.util.List;
import java.util.concurrent.TimeUnit;

import libraryPkg.BrowserInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CrossAsyst 
{
	WebDriver driver;
	By SignIn = By.className("login");
	By EmailAddress = By.id("email_create");
	By CreateAccountBtn = By.id("SubmitCreate");
	By Gender = By.id("id_gender1");
	By FirstName= By.id("customer_firstname");
	By LastName =By.id("customer_lastname");
	By SetPassword =By.id("passwd");
	By Address = By.id("address1");
	By City = By.id("city");
	By Zcode = By.name("postcode");
	By UserName = By.id("email");
	By Password = By.id("passwd");
	By Submit= By.xpath("SubmitLogin']/span"); 
	By selectstate = By.name("id_state");
	By countryname = By.id("id_country");
	By MobNo = By.id("phone_mobile");
	By RegisterButton = By.id("submitAccount");
	By LoginEmail = By.id("email");
	
	String UName = "devendra107@gmail.com";
	String Pwd = "Test@12345";
	String AmountInPayment;
	String OrderPrice;
	
	@BeforeTest
	public void BrowserLaunch()
	{
		driver = BrowserInfo.OpenBrowser("Chrome", "http://automationpractice.com/index.php");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
	}	
	
	@Test (priority=1)
	public void CreateAccount()
	{			
		String State = "Indiana";
		String Country = "United States";		
		String FName = "Devendra";
		String LName = "Sawant";		
		String Add = "Mumbai,abc04,xyz";
		String cityName = "Mumbai";
		String MobileNo = "867859302";
		String ZipCode = "12345";
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.findElement(SignIn).click();
		driver.findElement(EmailAddress).sendKeys(UName);
		driver.findElement(CreateAccountBtn).click();
		driver.findElement(Gender).click();
		driver.findElement(FirstName).sendKeys(FName);
		driver.findElement(LastName).sendKeys(LName);
		driver.findElement(SetPassword).sendKeys(Pwd);
		driver.findElement(Address).sendKeys(Add);
		driver.findElement(City).sendKeys(cityName);
		
		Select state = new Select(driver.findElement(selectstate));
		state.selectByVisibleText(State);
		
		driver.findElement(Zcode).sendKeys(ZipCode);
		
		Select country = new Select(driver.findElement(countryname));
		country.selectByVisibleText(Country);
		
		driver.findElement(MobNo).sendKeys(MobileNo);		
		driver.findElement(RegisterButton).click();
		
		
		driver.findElement(By.xpath("//*[@title='Log me out']")).click();	
		
	}	
	
	@Test (priority=2)
	public void ProductToCart()
	{
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Women")).click();		
		driver.findElement(By.xpath("//*[@class='product_list grid row']/descendant::a[1]/img")).click();		
		
		WebElement frame = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame);
		
		driver.findElement(By.id("quantity_wanted")).clear();
		driver.findElement(By.id("quantity_wanted")).sendKeys("2");		
		driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();
		driver.switchTo().defaultContent();		
		driver.findElement(By.xpath("//a[@title='Proceed to checkout']")).click();
	}	
		
	@Test (priority=3)
	public void ProcessingOrder()
	{
		try
		{
			String ButtonName = driver.findElement(By.xpath("//span[text()='Proceed to checkout']")).getText();			
			if(ButtonName.equalsIgnoreCase("Proceed to checkout"))
			{
				List<WebElement> TabName = driver.findElements(By.xpath("//*[@id='order_step']/li"));
				for(int i=1; i <= TabName.size();i++)
				{
					String CurrentTabNo = driver.findElement(By.xpath("//*[@id='order_step']/li/span/em")).getText();			
					if(CurrentTabNo.equalsIgnoreCase("04."))
					{
						driver.findElement(By.id("uniform-cgv")).click();
						driver.findElement(By.xpath("//*[@class='cart_navigation clearfix']/descendant::span")).click();
					}
					else if(CurrentTabNo.equalsIgnoreCase("05."))
					{
						String PageTitle = driver.getTitle();					
						if(PageTitle.equalsIgnoreCase("Order - My Store"))
						{
							AmountInPayment = driver.findElement(By.xpath("(//*[@class='cart_total_price']/descendant::td/span)[2]")).getText();
							System.out.println(AmountInPayment);
							driver.findElement(By.xpath("//*[@title='Pay by bank wire']")).click();
							driver.findElement(By.xpath("//*[text()='I confirm my order']")).click();
							break;
						}
					}	
					else if(CurrentTabNo.equalsIgnoreCase("02."))
					{
						String PageTitle = driver.getTitle();
						if(PageTitle.equalsIgnoreCase("Login - My Store"))
						{
							driver.findElement(LoginEmail).sendKeys(UName);
							driver.findElement(SetPassword).sendKeys(Pwd);
							driver.findElement(By.xpath("//*[@id='SubmitLogin']/span")).click();
						}
					}
					else
					{
						driver.findElement(By.xpath("//*[@class='cart_navigation clearfix']/descendant::span")).click();
					}
				}
			}	
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
	}
	
	@Test (priority=4)
	public void VerifyOrder()
	{
		driver.findElement(By.xpath("//*[@title='View my customer account']")).click();
		driver.findElement(By.xpath("//*[text()='Order history and details']")).click();
		OrderPrice = driver.findElement(By.xpath("//*[@class='history_price']/span")).getText().trim();	
		
		if(OrderPrice.equalsIgnoreCase(AmountInPayment))
		{
			System.out.println("Order Price and Payment Amount is showing same value");
		}
		else
		{
			System.out.println("Order Price and Payment Amount is NOT showing same value");
		}
	}
	
	@AfterTest
	public void CloseBrowser()
	{
		driver.findElement(By.xpath("//*[@title='Log me out']")).click();
		driver.close();	
		
	}
}
