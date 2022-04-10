package Pages;

import static org.testng.Assert.assertEquals;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import AutomationTask.RegisterPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class RegisterPageTests {

	WebDriver browser;
	RegisterPage registPage;
	Dimension requiredDimension = new Dimension(1024, 768);

	// Elements
	By cstFirstName = By.id("customer_firstname");
	By cstLastName = By.id("customer_lastname");
	By accPass = By.id("passwd");
	By addressFirstName = By.id("firstname");
	By addressLastName = By.id("lastname");
	By addressONE = By.id("address1");
	By addressCity = By.id("city");
	By addressState = By.id("id_state");
	By postalCode = By.id("postcode");
	By addressCountry = By.id("id_country");
	By mobilePhone = By.id("phone_mobile");
	By addressAlias = By.id("alias");
	By registBtn = By.id("submitAccount");
	By registSuc = By.xpath("//h1");
	By registFail = By.xpath("//*[@id=\"center_column\"]/div[contains(.,error)]");

	// Validation Messages
	String registSucMsg = "My Account";

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		browser = new ChromeDriver();
		browser.manage().window().setSize(requiredDimension);
		registPage = new RegisterPage(browser);
	}

	@BeforeMethod
	public void beforeMethod() {
		registPage.goToRegisterForm("regist042022@gmail.com");
	}

	public void FillFormWith_Valid_Data() {
		// Fill in required account details
		browser.findElement(cstFirstName).sendKeys("FirstName");
		browser.findElement(cstLastName).sendKeys("lastName");
		browser.findElement(accPass).sendKeys("Password@123");
		browser.findElement(addressFirstName).sendKeys("address Fname");
		browser.findElement(addressLastName).sendKeys("address LnameinAddr");
		browser.findElement(addressONE).sendKeys("address 1");
		browser.findElement(addressCity).sendKeys("Pune");
		Select sState = new Select(browser.findElement(addressState));
		sState.selectByVisibleText("Alabama");
		browser.findElement(postalCode).sendKeys("12345");
		Select sCountry = new Select(browser.findElement(addressCountry));
		sCountry.selectByVisibleText("United States");
		browser.findElement(mobilePhone).sendKeys("888666888");
		browser.findElement(addressAlias).sendKeys("address alias");
		browser.findElement(registBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	@Test
	public void CreatedAccSuccess() {
		FillFormWith_Valid_Data();
		// assert that the account is created successfully
		assertEquals(browser.findElement(registSuc).getText().toLowerCase(), registSucMsg.toLowerCase());
	}

	@Test
	public void CreatedAccFail() {
		browser.findElement(registBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		assertEquals(browser.findElement(registSuc).isDisplayed(), true);
	}

	@AfterClass
	public void afterClass() {
		browser.quit();
	}

}
