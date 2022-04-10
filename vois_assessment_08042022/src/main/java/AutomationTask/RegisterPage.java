package AutomationTask;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
	
	WebDriver browser;
	String WebsiteURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

	// Elements
	By createAccEmail = By.id("email_create");
	By createAccBtn = By.id("SubmitCreate");
	
	//constructor
	public RegisterPage(WebDriver browser) {
		this.browser = browser;
	}
	
	//actions	
	public void goToRegisterForm(String email) {
		browser.navigate().to(WebsiteURL);
		browser.findElement(createAccEmail).sendKeys(email);
		browser.findElement(createAccBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
}
