import com.sun.javafx.PlatformUtil;

import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class FlightBookingTest {

	public WebDriver driver;

	@Test(priority = 1)
	public void testThatResultsAppearForAOneWayJourney() {

		// Initialize the Chrome Driver
		driver = new ChromeDriver();
		setDriverPath();

		// Open the ClearTrip Website
		driver.get("https://www.cleartrip.com/");
		
		// Maximize the Browser
		driver.manage().window().maximize();
		
		// Enter the Origin Address
		waitFor(2000); // Site take time to load so give wait for some seconds
		driver.findElement(By.id("OneWay")).click();
		driver.findElement(By.id("FromTag")).clear();
		waitFor(5000);
		driver.findElement(By.id("FromTag")).sendKeys("Bangalore");

		// wait for the auto complete options to appear for the origin
		waitFor(5000);
		// select the first item from the destination auto complete list
		List<WebElement> originOptions = driver.findElement(By.id("ui-id-1")).findElements(By.tagName("li"));
		originOptions.get(0).click();

		// Enter the Destination Address
		waitFor(2000);
		driver.findElement(By.id("ToTag")).click();
		driver.findElement(By.id("ToTag")).clear();
		driver.findElement(By.id("ToTag")).sendKeys("Delhi");

		// wait for the auto complete options to appear for the destination
		waitFor(5000);
		// select the first item from the destination auto complete list
		List<WebElement> destinationOptions = driver.findElement(By.id("ui-id-2")).findElements(By.tagName("li"));
		destinationOptions.get(0).click();

		// Click the Depart Date
		waitFor(5000);
		driver.findElement(By.xpath("//*[@id='ui-datepicker-div']//*[@class='calendar']//a")).click();

		// all fields filled in. Now click on search
		waitFor(2000);
		driver.findElement(By.id("SearchBtn")).click();

		waitFor(10000);
		// verify that result appears for the provided journey search
		Assert.assertTrue(isElementPresent(By.className("searchSummary")));

	}

	private void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
		}
	}

	@Test(priority = 0)
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@BeforeMethod // TestNG must finish instantiating a KnowledgeBase object before it calls any
				// of the configuration methods (annotated with @BeforeTest).
	private void setDriverPath() {
		if (PlatformUtil.isMac()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		}
		if (PlatformUtil.isWindows()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver1.exe");
		}
		if (PlatformUtil.isLinux()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
		}
	}

	@AfterMethod
	public void closeDriver() {
		// close the browser
		driver.quit();
	}

}
