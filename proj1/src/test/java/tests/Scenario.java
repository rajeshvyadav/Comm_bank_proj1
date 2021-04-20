package tests;
import java.sql.Driver;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Scenario {

	static WebDriver driver=null;

	@BeforeTest
	public void LaunchBrowser(){

		//Launch the Chrome Browser
		System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		//Navigate to URL: https://www.commbank.com.au
		driver.get("https://www.commbank.com.au");
		System.out.println("Website launched successfully");
	}

	@Test (priority=1)
	public void validate_Title(){

		SoftAssert softAssert1=new SoftAssert();
		
		System.out.println(driver.getTitle());
		
		String title=driver.getTitle();
		softAssert1.assertTrue(title.contains("CommBank"), "Title check");

		softAssert1.assertAll();	
	}

	@Test(priority=2)
	public void Validate_LoginButtonAvailability() throws InterruptedException{

		SoftAssert softAssert2=new SoftAssert();
		Thread.sleep(3000);
		//driver.wait(3000);
		Boolean bln_itemsection=driver.findElement(By.xpath("//a[@class='commbank-header-login']")).isDisplayed();
		softAssert2.assertTrue(bln_itemsection.equals(true), "Login button check");

		softAssert2.assertAll();	
	}

	@AfterTest
	public void driver_quit(){

		driver.quit();
	}
}
