import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignInTest {

	public WebDriver driver;

	@Test
	public void shouldThrowAnErrorIfSignInDetailsAreMissing() {

		// Initializing the Chrome Driver
		driver = new ChromeDriver();
		setDriverPath();
		
		// Maximize the window
		driver.manage().window().maximize();

		// Page Redirects to Cleartrip Site
		driver.get("https://www.cleartrip.com/");
		waitFor(2000);

		// Click on Signin Link
		driver.findElement(By.linkText("Your trips")).click();
		driver.findElement(By.id("SignIn")).click();
		
		// Switch to iFrame and click sigin Button
		waitFor(5000); // wait is missing because this popup will take 2 or 5 seconds to load
		driver.switchTo().frame(driver.findElement(By.id("modal_window")));
		waitFor(2000);
		driver.findElement(By.id("signInButton")).click();
		
		// Verify the Error
		waitFor(1000);
		String errors1 = driver.findElement(By.id("errors1")).getText();
		Assert.assertTrue(errors1.contains("There were errors in your submission"));
		
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
		if (PlatformUtil.isWindows()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver1.exe");
		}
		if (PlatformUtil.isMac()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		}
		if (PlatformUtil.isLinux()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
		}
	}

	@AfterMethod
	public void closeDriver() {
		// close the browser
		waitFor(3000);
		driver.quit();
	}

}
