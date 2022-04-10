package AutomationTask;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class OrderFormPage {

	WebDriver browser;
	String WebsiteURL = "http://automationpractice.com/index.php";

	// Elements
	By womenCategory = By.xpath("//*[@id=\"block_top_menu\"]/ul/li[1]/a");
	By blousesSubCat = By.xpath("//*[@id=\"block_top_menu\"]//li[1]//li[1]//li[2]/a");
	By selectProduct = By.xpath("//*[@id=\"center_column\"]//img");
	By addToCartBtn = By.xpath("//*[@id=\"center_column\"]//div[2]/div[2]/a[1]");
	By proceedToCheckOutBtn = By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a");

	// constructor
	public OrderFormPage(WebDriver browser) {
		this.browser = browser;
	}

	// actions
	public void addItemToOrder() {
		browser.navigate().to(WebsiteURL);
		// Hover on Women Category and select Blouses
		Actions hoverAction = new Actions(browser);
		WebElement element = browser.findElement(womenCategory);
		hoverAction.moveToElement(element).perform();
		element = browser.findElement(blousesSubCat);
		hoverAction.moveToElement(element).perform();
		element.click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		// Hover on result and add it to cart
		element = browser.findElement(selectProduct);
		hoverAction.moveToElement(element).perform();
		element = browser.findElement(addToCartBtn);
		hoverAction.moveToElement(element).perform();
		element.click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		// Proceed to checkout
		browser.findElement(proceedToCheckOutBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

}
