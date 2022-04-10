package Pages;

import static org.testng.Assert.assertEquals;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import AutomationTask.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPageTests {

	WebDriver browser;
	LoginPage logInPage;
	Dimension requiredDimension = new Dimension(1024, 768);

	// Elements
	By createAccSucc = By.xpath("//*[@id=\"account-creation_form\"]/div[1]/h3");
	By createAccResponse = By.xpath("//ol/li");
	By signInSuc = By.xpath("//h1");

	// Validation Messages
	String invalidEmailMsg = "Invalid email address.";
	String createAccSuccMsg = "Your personal information";
	String createAccRegistEmailMsg = "An account using this email address has already been registered. Please enter a valid password or request a new one.";
	String logInNoEmailMsg = "An email address required.";
	String logInWithInValidMsg = "Authentication failed.";
	String logInSucMsg = "My Account";

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		browser = new ChromeDriver();
		browser.manage().window().setSize(requiredDimension);
		logInPage = new LoginPage(browser);
	}

	@BeforeMethod
	public void beforeMethod() {
		logInPage.navigate();
	}

	@Test
	public void createNewAccountWith_UnRegistered_Email() {
		logInPage.crtAccAttempt("registauto42022@gmail.com");
		assertEquals(browser.findElement(createAccSucc).getText().toLowerCase(), createAccSuccMsg.toLowerCase());
	}

	@Test
	public void createNewAccountWith_Registered_Email() {
		logInPage.crtAccAttempt("autoregist1@gmail.com");
		assertEquals(browser.findElement(createAccResponse).getText().toLowerCase(),
				createAccRegistEmailMsg.toLowerCase());
	}

	@Test
	public void createNewAccountWith_InValid_Email_Format() {
		logInPage.crtAccAttempt("autoregist136");
		assertEquals(browser.findElement(createAccResponse).getText().toLowerCase(), invalidEmailMsg.toLowerCase());
	}

	@Test
	public void logInWith_Valid_Email_Valid_Password() {
		logInPage.logInAttempt("autoregist1@gmail.com", "Password@123");
		assertEquals(browser.findElement(signInSuc).getText().toLowerCase(), logInSucMsg.toLowerCase());
		browser.findElement(By.xpath("//*[@id=\"header\"]//div[2]/a")).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test
	public void logInWith_Valid_Email_InValid_Password() {
		logInPage.logInAttempt("autoregist1@gmail.com", "Password");
		assertEquals(browser.findElement(createAccResponse).getText().toLowerCase(), logInWithInValidMsg.toLowerCase());
	}

	@Test
	public void logInWith_In_Valid_Email_Valid_Password() {
		logInPage.logInAttempt("autoregist1", "Password@123");
		assertEquals(browser.findElement(createAccResponse).getText().toLowerCase(), invalidEmailMsg.toLowerCase());
	}

	@Test
	public void logInWith_NO_Email_NO_Password() {
		logInPage.logInAttempt("", "");
		assertEquals(browser.findElement(createAccResponse).getText().toLowerCase(), logInNoEmailMsg.toLowerCase());
	}

	@AfterClass
	public void afterClass() {
		browser.quit();
	}

}
