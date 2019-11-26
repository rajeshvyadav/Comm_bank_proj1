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
	public void validate_SubTopics(){

		SoftAssert softAssert1=new SoftAssert();
		
		//Navigate to "Travelling Overseas?" section and click on "Top 10 travel tips"
		driver.findElement(By.xpath("//p[contains(text(),'Top 10 travel tips')]")).click();

		//Click on "Travel Money Card" from "RELATED LINKS AND PRODUCTS"
		driver.findElement(By.xpath("//aside[@class='related-articles hide-mobile']//a[contains(text(),'Travel Money Card')]")).click();

		//Navigating to Travel Money Card window
		Set<String> windowHandles=driver.getWindowHandles();
		Iterator<String> it1=windowHandles.iterator();
		String ParentWindow=it1.next();
		String TravelWindow=it1.next();
		driver.switchTo().window(TravelWindow);

		Set<String> subTopics_names=new HashSet();
		List<WebElement> subtopics=driver.findElements(By.xpath("//div[@class='anchor-module']/nav/ul/li"));
		for(int i=1;i<=subtopics.size();i++){

			String topicName=driver.findElement(By.xpath("//div[@class='anchor-module']/nav/ul/li["+i+"]/a")).getText();
			subTopics_names.add(topicName);	
		}

		Iterator<String> it=subTopics_names.iterator();
		System.out.println("****Display all the subtopics****");
		while(it.hasNext()){

			//Display all the subtopics
			System.out.println(it.next());		
		}

		//Verify whether "Follow the exchange rate" subTopic exists
		String st1="Follow the exchange rate";
		Boolean flag_subtopicchk=false;

		if(subTopics_names.contains(st1)){

			flag_subtopicchk=true;
		}
		softAssert1.assertTrue(flag_subtopicchk, "SubTopic "+st1+" exists");

		String st2="check fees";
		flag_subtopicchk=false;
		if(subTopics_names.contains(st2)){

			flag_subtopicchk=true;
		}
		softAssert1.assertTrue(flag_subtopicchk, "SubTopic "+st2+" exists");

		softAssert1.assertAll();	
	}

	@Test(priority=2)
	public void loginpage_chk(){

		SoftAssert softAssert2=new SoftAssert();
		
		//Scroll down and click on "Log on to Net Bank"
		WebElement Netbank_link=driver.findElement(By.xpath("//div[@class='item-section']//div[1]//div[1]//ol[1]//li[1]//a[1]"));

		Actions act=new Actions(driver);
		act.moveToElement(Netbank_link).build().perform();
		Netbank_link.click();	

		Set<String> windowHandles1=driver.getWindowHandles();

		Iterator<String> it2=windowHandles1.iterator();
		String ParentWindow1=it2.next();
		String TravelWindow1=it2.next();
		String ApplyWindow=it2.next();

		driver.switchTo().window(ApplyWindow);

		System.out.println(driver.getTitle());

		//click on select under Online container
		driver.findElement(By.xpath("//div[@class='quickOptionContainer']/div[1]/label[1]/span[2]/span[1]")).click();

		//validate the username field in the login page
		String expected_fieldlabelname1="username";
		String actual_fieldlabelname1=driver.findElement(By.xpath("//span[contains(text(),'Client number')]")).getText();
		softAssert2.assertTrue(actual_fieldlabelname1.equalsIgnoreCase(expected_fieldlabelname1), "Username field label check");

		//validate the Password field in the login page
		String expected_fieldlabelname2="Password";
		String actual_fieldlabelname2=driver.findElement(By.xpath("//span[contains(text(),'Password')]")).getText();
		System.out.println(actual_fieldlabelname2);
		softAssert2.assertTrue(actual_fieldlabelname2.equalsIgnoreCase(expected_fieldlabelname2), "password field label check");

		softAssert2.assertAll();
	}

	@AfterTest
	public void driver_quit(){

		driver.quit();
	}
}
