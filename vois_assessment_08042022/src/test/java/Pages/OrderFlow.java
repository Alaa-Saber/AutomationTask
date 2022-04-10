package Pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import AutomationTask.OrderFormPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OrderFlow {

	WebDriver browser;
	OrderFormPage order;
	Dimension requiredDimension = new Dimension(1024, 768);

	// Elements
	By summaryProceedToCheckoutBtn = By.xpath("//*[@id=\"center_column\"]/p[2]/a[1]");
	By loginEmail = By.id("email");
	By loginPass = By.id("passwd");
	By signInBtn = By.id("SubmitLogin");
	By signOutBtn = By.xpath("//*[@id=\"header\"]//div[2]/a");
	By addressProceedToCheckoutBtn = By.xpath("//*[@id=\"center_column\"]//button");
	By termsCheckBox = By.id("cgv");
	By shippingProceedToCheckoutBtn = By.xpath("//*[@id=\"form\"]//button");
	By payBankWire = By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[1]//a");
	By payBankWireConfirmation = By.xpath("//*[@id=\"center_column\"]//h3");
	By confirmMyOrderBtn = By.xpath("//*[@id=\"cart_navigation\"]/button");
	By OrderConfirmed = By.xpath("//*[@id=\"center_column\"]//p/strong");
	By OrderIDfromText = By.xpath("//*[@id=\"center_column\"]/div");
	By backToOrdersBtn = By.xpath("//*[@id=\"center_column\"]/p/a");
	By latestOrderInList = By.xpath("//*[@id=\"order-list\"]//tr[1]/td[1]/a");

	// Confirmation Messages
	String orderMadeSuc = "Your order on My Store is complete.";
	String payBankWireConfirmMsg = "Bank-wire payment.";

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		browser = new ChromeDriver();
		browser.manage().window().setSize(requiredDimension);
		order = new OrderFormPage(browser);
	}

	@BeforeMethod
	public void beforeMethod() {
		order.addItemToOrder();
		// Order Confirmation Steps
		browser.findElement(summaryProceedToCheckoutBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		// Login to confirm order
		browser.findElement(loginEmail).sendKeys("autoregist1@gmail.com");
		browser.findElement(loginPass).sendKeys("Password@123");
		browser.findElement(signInBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		browser.findElement(addressProceedToCheckoutBtn).click();
		// Agree terms&Conditions
		browser.findElement(termsCheckBox).click();
		browser.findElement(shippingProceedToCheckoutBtn).click();
	}

	@AfterMethod
	public void afterMethod() {
		browser.findElement(signOutBtn).click();
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	
	@Test 
//	(priority = 1)
	public void PayByBankWire() {
		// Payment tab -> payment by BANK-WIRE
		browser.findElement(payBankWire).click();
		assertEquals(browser.findElement(payBankWireConfirmation).getText().toLowerCase(), payBankWireConfirmMsg.toLowerCase());
	}

	@Test 
//	(priority = 2)
	public void OrderConfirmedSuccessful() {
		PayByBankWire();
		// Confirm the order
		browser.findElement(confirmMyOrderBtn).click();
		// Get Text
		assertEquals(browser.findElement(OrderConfirmed).getText().toLowerCase(), orderMadeSuc.toLowerCase());
	}

	@Test 
//	(priority = 3)
	public void ConfirmOrderAddedToOrdersListSuccessful() {
		OrderConfirmedSuccessful();
		// go back to Order History Page
		String OrderID = browser.findElement(OrderIDfromText).getText().toLowerCase();
		browser.findElement(backToOrdersBtn).click();
		String FirstOrderInList = browser.findElement(latestOrderInList).getText().toLowerCase();
		assertTrue(OrderID.contains(FirstOrderInList));
	}

	@AfterClass
	public void afterClass() {
		browser.quit();
	}

}
