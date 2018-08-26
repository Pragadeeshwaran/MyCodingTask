import com.sun.javafx.PlatformUtil;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HotelBookingTest {
	
	public WebDriver driver;
	
	@FindBy(linkText = "Hotels")
	private WebElement hotelLink;

	@FindBy(id = "Tags")
	private WebElement localityTextBox;

	@FindBy(xpath = "//*[@id='ui-id-1']//li[2]")
	private WebElement selectFromList;

	@FindBy(xpath = "//*[@id='ui-datepicker-div']//*[@class='calendar']//a")
	private WebElement dateSelection;

	@FindBy(id = "SearchHotelsButton")
	private WebElement searchButton;

	@FindBy(id = "travellersOnhome")
	private WebElement travellerSelection;

	@Test
	public void shouldBeAbleToSearchForHotels() {

		// Intialize the Chrome Browser
		driver = new ChromeDriver();
		setDriverPath();

		// Maximize the Window
		driver.manage().window().maximize();

		// Go to the Website
		driver.get("https://www.cleartrip.com/");
		waitFor(3000);

		// Call the Method from another Class
		HotelBookingTest call = PageFactory.initElements(driver, HotelBookingTest.class);

		// Click the Hotels Link
		call.hotelLink.click();

		// Enter the Address and Select
		call.localityTextBox.sendKeys("Indiranagar, Bangalore");
		waitFor(5000);
		call.selectFromList.click();

		// Select the Check In and Out Date
		waitFor(2000);
		call.dateSelection.click();
		waitFor(2000);
		call.dateSelection.click();

		// Select the Travellers
		waitFor(2000);
		new Select(call.travellerSelection).selectByVisibleText("1 room, 1 adult");

		// Click Search Button
		waitFor(2000);
		call.searchButton.click();
		waitFor(20000);

	}

	private void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
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
