package AutomationTask;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	WebDriver browser;
	String WebsiteURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

	// Elements
	By createAccEmail = By.id("email_create");
	By createAccBtn = By.id("SubmitCreate");
	By signInEmail = By.id("email");
	By signInPass = By.id("passwd");
	By signInBtn = By.id("SubmitLogin");
	
	//constructor
	public LoginPage(WebDriver browser) {
		this.browser = browser;
	}
	
	//actions
	public void navigate() {
		browser.navigate().to(WebsiteURL);
	}

	public void logInAttempt(String email, String pass) {
		browser.findElement(signInEmail).sendKeys(email);
		browser.findElement(signInPass).sendKeys(pass);
		browser.findElement(signInBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	public void crtAccAttempt(String email) {
		browser.findElement(createAccEmail).sendKeys(email);
		browser.findElement(createAccBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
}
