package Pages;

import STUtils.eSTProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static STUtils.eSTProperties.getConfigProperty;
import static STUtils.eSTProperties.getProperty;
import static STUtils.eUtils.await;
import static STUtils.eUtils.executeInTerminal;

public class eBillingPageWindows extends eCommonMethods
{
	public eBillingPageWindows(WebDriver webDriver, AndroidDriver<AndroidElement> driver) {
		super(webDriver, driver);
	}

	WebDriver 		winiumDriver;
	WebDriverWait	waitWeb;
	WebElement		webElement;
	eAccountPage 	accountPage = new eAccountPage(this.driver);


	By rechargeButton					= By.id("com.app.stealthtalk.testers:id/activity_billing_blocked_activate_button");
	By credentialRequestLabel			= By.xpath("//*[@text = 'Use your authorization credentials from StealthTalk application:']");
	By phoneNumberInput					= By.xpath("//*[@resource-id='loginform-phone_number']");
	By passcodeInput					= By.xpath("//*[@resource-id='loginform-password']");
	By signInButton						= By.xpath("//*[@text = 'SIGN IN']");
	By subscriptionPeriodDropDownMenu	= By.xpath("//*[@resource-id='updateperiodform-period' or @text = '1 month']");
	By oneMonthButton					= By.xpath("//*[@text = '1 month']");
	By filledPhoneNumberInput			= By.xpath("//*[@resource-id='loginform-phone_number' and @text='+1"+getProperty("phoneNumberForBillingTesting")+"']");
	By paypalLogInButton				= By.xpath("//*[@text='Log In']");
	By paypalEmailInput					= By.xpath("//*[@resource-id='email']");
	By paypalPasswordInput				= By.xpath("//*[@resource-id='password']");
	By paypalSendCredentialsButton		= By.xpath("//*[@resource-id='btnLogin']");
	By payNowButton						= By.xpath("//*[@text='Pay Now']");
	By logOutButton						= By.xpath("//*[@text='SIGN OUT']");
	By urlBarBrowser					= By.xpath("//*[@text='https://account.stealthtalk.com/site/login']");
	By renewNowButton					= By.id("com.app.stealthtalk.testers:id/fragment_settings_billing_button_primary");
	By addressBarInput					= By.id("com.android.chrome:id/url_bar");
	By businessMessengerTitle 			= By.xpath("//*[@text='Cyber Defense Business Messenger']");
	By continueButton					= By.xpath("//android.widget.Button[@text='CONTINUE']");
	By signInInput						= By.xpath("//*[@resource-id='authform-password']"); 							//don't change to id
	By paypalButton						= By.xpath("//*[@text='PP PayPal']"); 											//don't change to id
	By paypalGreeting					= By.xpath("//*[@text='Hi, SMBuyName!']");
	By renewalTitle						= By.xpath("//*[@text='RENEWAL']");
	By promocodeInput					= By.xpath("//*[@resource-id='updateperiodform-code']");
	By discountDescription				= By.xpath("//*[@resource-id='discount_desc']");

	By phoneNumberInputWeb				= By.id("loginform-phone_number");
	By continueButtonWeb				= By.xpath("//button[@type='submit']");
	By advancedButtonWeb 				= By.id("details-button");
	By proceedWeb						= By.id("proceed-link");
	By promoCodeInputWeb				= By.id("updateperiodform-code");
	By signInCodeInputWeb				= By.id("authform-password");
	By sigInButtonWeb					= By.xpath("//button[@name='login-button']");
	By payPalButtonWeb					= By.xpath("//*[@class='paypal-buttons paypal-buttons-context-iframe paypal-buttons-label-paypal paypal-buttons-layout-horizontal']");
	By logInButtonWeb					= By.xpath("//*[@id='loginSection']/div/div[2]/a");
	By emailInputWeb					= By.xpath("//input[@id='email' and @name='login_email']");
	By passwordInputWeb					= By.xpath("//input[@id='password' and @name ='login_password' and @type='password']");
	By authorizedLogInButtonWeb			= By.id("btnLogin");
	By payNowButtonWeb					= By.xpath("//button[@id='payment-submit-btn' and @name='payment-submit-btn']");
	By nextButtonWeb					= By.id("btnNext");
	By prolongButtonWeb					= By.xpath("//button[@id='code-btn' and @type='submit']");
	By usersTabButton					= By.xpath("//a[contains(text(), 'Users')]");
	By usersTabLabel					= By.xpath("//h1[contains(text(), 'Users')]");
	By addUserButtonOnUsersTab			= By.xpath("//a[contains(text(), 'Add user')]");
	By addUserButton					= By.xpath("//button[contains(text(), 'Add user')]");
	By addUserTabLabel					= By.xpath("//h1[contains(text(), 'Add user')]");
	By userNameInput					= By.xpath("//input[@id='talkuserform-full_name']");
	By phoneNumberInput_Enterprise		= By.xpath("//input[@id='talkuserform-phone_number']");
	By successAddedUserNotification		= By.id("w0-success-0");
	By renewedSubscriptionNotification	= By.id("w0-success-0");
	By failedAddedUserNotification		= By.xpath("//div[contains(@class, 'has-error')]/div[@class='help-block']");
	By signInForm						= By.xpath("//*[h1='Sign in']");
	By emailInput						= By.xpath("//input[@id='loginform-email']");
	By passwordInput					= By.xpath("//input[@id='loginform-password']");
	By signInButtonEnterpriseWeb		= By.xpath("//button[@name='login-button']");
	By extendTabLabel					= By.xpath("//h1[contains(text(), 'Extend')]");
	By extendSubscriptionButton			= By.xpath("//button[contains(text(), 'Extend subscription')]");
	By confirmExtendMessage				= By.id("discount_desc");
	By confirmExtendButton				= By.xpath("//button[contains(text(), 'Confirm')]");
	By countryKiribati                  = By.xpath("//*[@id=\"talkuserform-country\"]/option[118]");
	By countryChoose  					= By.id("talkuserform-country");


	String usersTableElementXpath				= "//tr/td/div[contains(text(), '%s')]";
	String userBillingStateXpath				= "//tr/td/div[contains(text(), '%s')]/../../td[contains(text(), 'Active') or contains(text(), 'Expired')]";
	String extendUserSubscriptionButtonXpath	= "//tr/td/div[contains(text(), '%s')]/../../td/a[@title='Extend subscription']";

	static String kiribatiCountryCode						= "+686";
	static String failedAddedUserErrorMessage				= "You can't register this phone number for your Company";
	static String successfullyAddedUserMessage				= "×\nUser \"%s\" have been added successfully!";
	static String successfullyRenewedSubscriptionMessage	= "User subscription has been successfully renewed!";

	public void setUpWiniumDriver()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("debugConnectToRunningApp", "true");
		capabilities.setCapability("app", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");

		try {
			winiumDriver = new RemoteWebDriver(new URL("http://localhost:9999"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void signInBillingSite()
	{
		invokeSignInCodeWeb();
		String code = accountPage.getSignInCode();
		loginWithSignInCode(code);
	}

	public void invokeSignInCodeWeb()
	{
		openBillingWeb();
		clearThenInputWeb(phoneNumberInputWeb, kiribatiCountryCode+getProperty("phoneNumberForBillingTestingWeb"));
		clickWeb(continueButtonWeb);
	}

	public void openBillingWeb()
	{
		webDriver.get(getConfigProperty("billingSiteUrlLocal"));
		clickWeb(advancedButtonWeb);
		clickWeb(proceedWeb);
	}

	public void openBillingEnterpriseWeb()
	{
		webDriver.get(getConfigProperty("billingSiteEnterpriseUrlLocal"));
		authorizeToBillingSite();
	}

	public void loginToBillingEnterpriseWeb()
	{
		waitWeb(signInForm);
		inputWeb(emailInput, getProperty("enterpriseBillingSiteUsername"));
		inputWeb(passwordInput, getProperty("enterpriseBillingSitePassword"));

		clickWeb(signInButtonEnterpriseWeb);
	}

	public void loginWithSignInCode(String code)
	{
		waitWeb(signInCodeInputWeb);
		clearThenInputWeb(signInCodeInputWeb, code);
		clickWeb(sigInButtonWeb);
	}

	public void renewalWithPromoWeb()
	{
		clickWeb(promoCodeInputWeb);
		clearThenInputWeb(promoCodeInputWeb, getProperty("discountCode"));
		await(5000);																							// TODO await()
		clickWeb(payPalButtonWeb);
	}

	public void renewalWithPrepaid()
	{
		Set<String> handles = webDriver.getWindowHandles();
		ArrayList<String> handlesArray = new ArrayList<>(handles);
		webDriver.switchTo().window(handlesArray.get(0));
		waitWeb(promoCodeInputWeb);
		inputWeb(promoCodeInputWeb, getProperty("prepaidCode"));
		waitWeb(prolongButtonWeb);
		clickWeb(prolongButtonWeb);
	}

	private void addNewUserOnEnterpriseBillingSite(String username, String phoneNumber)
	{
		waitWeb(usersTabButton);
		clickWeb(usersTabButton);

		waitWeb(usersTabLabel);
		clickWeb(addUserButtonOnUsersTab);

		waitWeb(userNameInput);
		await(1000);
		inputWeb(userNameInput, username);
		clearThenInputWeb(phoneNumberInput_Enterprise, "+686" + phoneNumber);
		clickWeb(addUserButton);
		clickWeb(countryChoose);
		Select country = new Select(driver.findElement(countryChoose));
		country.selectByIndex(118);

	}

	public void verifyUserOnUsersTab(String username, String phoneNumber, eSTProperties.EnterpriseBillingState billingState)
	{
		waitWeb(usersTabButton);
		clickWeb(usersTabButton);
		waitWeb(usersTabLabel);

		WebElement usernameElement		= webDriver.findElement(By.xpath(String.format(usersTableElementXpath, username)));
		WebElement phoneNumberElement	= webDriver.findElement(By.xpath(String.format(usersTableElementXpath, phoneNumber)));
		String userBillingState			= webDriver.findElement(By.xpath(String.format(userBillingStateXpath, phoneNumber))).getText();

		Assert.assertEquals(username, usernameElement.getText());
		Assert.assertEquals(kiribatiCountryCode + phoneNumber, phoneNumberElement.getText());

		switch (billingState)
		{
			case ACTIVE:
				Assert.assertEquals(userBillingState, "Active");
				break;
			case EXPIRED:
				Assert.assertEquals(userBillingState, "Expired");
				break;
		}
	}

	private void verifySuccessfullyAddedAccount(String phoneNumber)
	{
		waitWeb(successAddedUserNotification);
		String message = webDriver.findElement(successAddedUserNotification).getText();

		Assert.assertEquals(message, String.format(successfullyAddedUserMessage, kiribatiCountryCode + phoneNumber));
	}

	private void verifyFailedAddedAccount()
	{
		waitWeb(failedAddedUserNotification);
		String message = webDriver.findElement(failedAddedUserNotification).getText();

		Assert.assertEquals(message, failedAddedUserErrorMessage);
	}

	public void addNewUserOnEnterpriseBillingSite_PositiveCase(String username, String phoneNumber)
	{
		addNewUserOnEnterpriseBillingSite(username, phoneNumber);
		verifySuccessfullyAddedAccount(phoneNumber);
	}

	public void addNewUserOnEnterpriseBillingSite_NegativeCase(String username, String phoneNumber)
	{
		addNewUserOnEnterpriseBillingSite(username, phoneNumber);
		verifyFailedAddedAccount();
	}

	public void rechargeEnterpriseAccount(String username)
	{
		waitWeb(usersTabButton);
		clickWeb(usersTabButton);
		waitWeb(usersTabLabel);

		webDriver.findElement(By.xpath(String.format(extendUserSubscriptionButtonXpath, username))).click();

		waitWeb(extendTabLabel);
		clickWeb(extendSubscriptionButton);
		waitWeb(confirmExtendMessage);
		clickWeb(confirmExtendButton);

		waitWeb(renewedSubscriptionNotification);
		String message = webDriver.findElement(renewedSubscriptionNotification).getText();

		Assert.assertTrue(message.contains(successfullyRenewedSubscriptionMessage));
	}

	public void rechargeAccountPromo()
	{
		renewalWithPromoWeb();
		signInPaypalAccount();
		acceptPaypalPayment();
	}

	public void rechargeAccountPrepeid()
	{
		renewalWithPrepaid();
	}

	public void acceptPaypalPayment()
	{
		waitWeb(payNowButtonWeb);
		clickWeb(payNowButtonWeb);
	}

	public void signInPaypalAccount()
	{
		Set<String> handles = webDriver.getWindowHandles();
		ArrayList<String> handlesArray = new ArrayList<>(handles);
		webDriver.switchTo().window(handlesArray.get(1));
		clickWeb(logInButtonWeb);
		inputWeb(emailInputWeb, getProperty("paypalEmail"));
		clickWeb(nextButtonWeb);
		waitWeb(passwordInputWeb);
		inputWeb(passwordInputWeb, getProperty("paypalPassword"));
		clickWeb(authorizedLogInButtonWeb);
	}

	private void signInStealthTalkAccount(String phoneNumber)
	{
		click(rechargeButton);
		waitForLoadShort(credentialRequestLabel);
		input(phoneNumberInput, "+1" + phoneNumber);
		input(passcodeInput, getProperty("default4digitPass"));
		waitForLoadShort(filledPhoneNumberInput);
		click(signInButton);

		scrollDownUntilElementVisible(subscriptionPeriodDropDownMenu);
		click(subscriptionPeriodDropDownMenu);
		click(oneMonthButton);
		click(paypalButton);
	}

	public void navigateBillingSite()																					// TODO
	{
		waitThenClick(renewNowButton);
		waitForLoad(urlBarBrowser);
		Assert.assertEquals(getText(urlBarBrowser), getConfigProperty("billingSiteUrl"));
		navigateTestBillingSite();
	}

	private void navigateTestBillingSite() {
		waitThenClick(urlBarBrowser);
		clearThenInput(addressBarInput, getConfigProperty("billingSiteUrlLocal"));
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		waitForLoad(businessMessengerTitle);
	}

	public void turnOffWebDriver()
	{
		if (!(webDriver == null)) {
			webDriver.quit();
		}
	}

	public void clearThenInputWeb(By locator, String textToInput)
	{
		waitWeb(locator);
		webElement = webDriver.findElement(locator);
		webElement.click();
		webElement.clear();
		webElement.sendKeys(textToInput);
	}

	public void inputWeb(By locator, String textToInput)
	{
		waitWeb(locator);
		webElement = webDriver.findElement(locator);
		webElement.click();
		webElement.sendKeys(textToInput);
	}

	public void clickWeb(By locator)
	{
		waitWeb(locator);
		webElement = webDriver.findElement(locator);
		webElement.click();
	}

	public void waitWeb(By locator)
	{
		waitWeb = new WebDriverWait(webDriver, 35);
		waitWeb.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static WebDriver setWebDriver()
	{
		WebDriverManager.chromedriver().setup();
		return new ChromeDriver();
	}

	private void authorizeToBillingSite()
	{
		String command = 	"cmd /c echo Set WshShell = WScript.CreateObject(\"WScript.Shell\") > script.vbs && " +
							"echo WshShell.AppActivate \"chrome\" >> script.vbs && " +
							"echo WshShell.SendKeys \"" + getProperty("billingSiteUsername") + "\" >> script.vbs && " +
							"echo WshShell.SendKeys \"{TAB}\" >> script.vbs && " +
							"echo WshShell.SendKeys \"" + getProperty("billingSitePassword") + "\" >> script.vbs && " +
							"echo WshShell.SendKeys \"{ENTER}\" >> script.vbs && " +
							"cscript script.vbs && del /f script.vbs";

		executeInTerminal(command);
	}

	@Deprecated
	public void usingWinium_Example()
	{
		String pathToWiniumDriver = "D:\\WORK\\Winnium\\Winium.Desktop.Driver.exe";

		ProcessBuilder processBuilder = new ProcessBuilder(pathToWiniumDriver);
		try {
			Process process = processBuilder.start();

			List<String> windowHandles = new ArrayList<>();
			Iterator iterator = winiumDriver.getWindowHandles().iterator();

			while (iterator.hasNext()) windowHandles.add(String.valueOf(iterator.next()));

			Collections.sort(windowHandles);

			windowHandles.stream().forEach( windowHandle -> {
				try {
					winiumDriver.switchTo().window(windowHandle);
				} catch (WebDriverException e) {
					System.out.println(e.getMessage());
				}
			} );

			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
