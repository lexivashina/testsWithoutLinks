package Pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static STUtils.eSTProperties.*;

public class eBillingPageMobile extends eCommonMethods
{


	public eBillingPageMobile(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}

	public eBillingPageMobile(WebDriver webDriver) {
		super(webDriver);
	}

	private By stealthTalkTitleWebSite = By.xpath("//*[@text='StealthTalk']");
	private By rechargeButton					= By.id("com.app.stealthtalk.testers:id/activity_billing_blocked_activate_button");
	private By credentialRequestLabel			= By.xpath("//*[@text = 'Use your authorization credentials from StealthTalk application:']");
	private By phoneNumberInput					= By.xpath("//*[@resource-id='loginform-phone_number' or @resource-id='updateperiodform-phone_replenish' or @resource-id='prolongperiodform-phone_replenish']");
	private By passcodeInput					= By.xpath("//*[@resource-id='loginform-password']");
	private By signInButton						= By.xpath("//*[@text = 'SIGN IN']");
	private By subscriptionPeriodDropDownMenu	= By.xpath("//*[@resource-id='updateperiodform-period' or @text = '1 month']");
	private By oneMonthButton					= By.xpath("//*[@text = '1 month']");
	//private By paypalButton						= By.xpath("//*[@text='paypal']");
	private By filledPhoneNumberInput			= By.xpath("//*[@resource-id='loginform-phone_number' and @text='+1"+getProperty("phoneNumberForBillingTesting")+"']");
	private By paypalLogInButton				= By.xpath("//*[@text='Log In']");
	private By paypalEmailInput					= By.xpath("//*[@resource-id='email']");
	private By paypalPasswordInput				= By.xpath("//*[@resource-id='password']");
	private By paypalSendCredentialsButton		= By.xpath("//*[@resource-id='btnLogin']");
	private By shippingInfo						= By.xpath("//*[@resource-id='shippingAddress']");
	private By payNowButton						= By.xpath("//*[@text='Pay Now']");
	private By payWithLabel						= By.xpath("//*[@text='Pay with']");
	private By logOutButton						= By.xpath("//*[@text='SIGN OUT']");
	private By renewButtonOnSite				= By.xpath("//*[@text='RENEW NOW']");
	private By urlBarBrowser					= By.xpath("//*[@text='aeraglobal.stealthtalk.com/en/subscription' or @text='account.stealthtalk.com/site/login']");
	private By renewNowButton					= By.id("com.app.stealthtalk.testers:id/fragment_settings_billing_button_primary");
	private By addressBarInput					= By.id("com.android.chrome:id/url_bar");
	private By businessMessengerTitle 			= By.xpath("//*[@text='Cyber Defense Business Messenger']");
	private By continueButton					= By.xpath("//android.widget.Button[@text='CONTINUE']");
	private By signInInput						= By.xpath("//*[@resource-id='authform-password']"); //don't change to id
	private By paypalButton						= By.xpath("//*[@text='PayPal']"); //don't change to id
	private By paypalGreeting					= By.xpath("//*[contains(@text, 'Hi, SMBuyName!')]");
	private By renewalTitle						= By.xpath("//*[@text='RENEWAL']");
	private By renewSubscriptionTitle			= By.xpath("//*[@text='Renew subscription']");
	private By promocodeInput					= By.id("updateperiodform-code");
	private By applyButton						= By.xpath("//*[@resource-id='code-btn']");
	private By discountDescription				= By.xpath("//*[@resource-id='discount_desc']");
	private By historyTitle						= By.xpath("//*[@text='HISTORY']");
	private By chromeLabel						= By.xpath("//*[@text='Chrome']");
	private By nextButton						= By.xpath("//*[@text='Next' or @text='next']");
	private By logOutPayPayButton				= By.xpath("//*[@text='Log out']");
	private By extendButton						= By.xpath("//android.widget.Button[@text='EXTEND >']");

	static String kiribatiCountryCode			= "+686";
	static String discountDescriptionLabel		= "This code applies 10% discount";
	static String prepaidDescriptionLabel		= "This code contains 30-day StealthTalk subscription";


	public void rechargeAccountWithDiscount()
	{
		renewalWithDiscount();
		signInPaypalAccount();
		acceptPaypalPayment();
	}

	public void rechargeAccountWithPrepaid()
	{
		renewalWithPrepaid();
	}

	private void logOutStealthTalkAccount()
	{
		scrollUpUntilElementVisible(logOutButton);
		click(logOutButton);
		waitForLoadShort(stealthTalkTitleWebSite);
		driver.activateApp(getStealthTalkPackage());
	}

	public void acceptPaypalPayment()
	{
		waitForLoad(paypalGreeting);
		waitForLoad(payWithLabel);
		scrollDownUntilElementVisible(payNowButton);
		click(payNowButton);
		waitForLoad(paypalButton);
	}

	public void signInPaypalAccount()
	{
		waitForAnyElementLong(logOutPayPayButton, paypalLogInButton);
		if (isPresent(logOutPayPayButton)) click(logOutPayPayButton);

		waitForLoadLong(paypalLogInButton);
		waitThenClick(paypalLogInButton);
		clear(paypalEmailInput);
		input(paypalEmailInput, getProperty("paypalEmail"));
		click(nextButton);
		input(paypalPasswordInput, getProperty("paypalPassword"));
		click(paypalLogInButton);
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

	public void navigateBillingSite()
	{
		waitThenClick(renewNowButton);
		if (isPresentWithWait(chromeLabel, 1)) waitThenClick(chromeLabel);
		waitForLoad(urlBarBrowser);
		Assert.assertTrue(getText(urlBarBrowser).contains("account.stealthtalk.com/site/login"));
		navigateTestBillingSite();
	}

	private void loginToBillingSiteIfItNeeded()
	{
		By signInLabel		= By.id("com.android.chrome:id/alertTitle");
		By userNameInput	= By.xpath("//android.widget.EditText[@password='false']");
		By passwordInput	= By.xpath("//android.widget.EditText[@password='true']");
		By signInButton		= By.xpath("//android.widget.Button[@text='Sign in']");

		waitForAnyElementLong(signInLabel, businessMessengerTitle);
		if (isPresent(signInLabel))
		{
			input(userNameInput, getProperty("billingSiteUsername"));
			input(passwordInput, getProperty("billingSitePassword"));

			click(signInButton);
		}
	}

	private void navigateTestBillingSite() {
		waitThenClick(urlBarBrowser);
		clearThenInput(addressBarInput, getConfigProperty("billingSiteUrlLocal"));
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		loginToBillingSiteIfItNeeded();
		waitForLoad(businessMessengerTitle);
	}

	public void reNewSubscriptionBySignIn(String phoneNumber, PromoCode promoCodeType)
	{
		signInBillingSite(phoneNumber);

		switch (promoCodeType)
		{
			case PREPAID:
				rechargeAccountWithPrepaid();
				break;
			case DISCOUNT:
				rechargeAccountWithDiscount();
				break;
		}

		logOutStealthTalkAccount();
		openStealthTalk();
	}

	public void reNewSubscriptionByRenewNow(String phoneNumber, PromoCode promoCodeType)
	{
		reNewNowBillingSite(phoneNumber);

		switch (promoCodeType)
		{
			case PREPAID:
				rechargeAccountWithPrepaid();
				break;
			case DISCOUNT:
				rechargeAccountWithDiscount();
				break;
		}

		openStealthTalk();
	}

	public void signInBillingSite(String phoneNumber)
	{
		navigateBillingSite();
		invokeSignInCode(phoneNumber);
		String code = getSignInCodeInApp();
		loginWithSignInCode(code);
	}

	public void reNewNowBillingSite(String phoneNumber)
	{
		navigateBillingSite();
		clickIfPossible(logOutButton);
		waitThenClick(renewButtonOnSite);
		waitForLoad(renewSubscriptionTitle);

		clear(phoneNumberInput);
		input(phoneNumberInput, kiribatiCountryCode + phoneNumber);
	}

	private String getSignInCodeInApp()
	{
		openStealthTalk();

		eAccountPage eAccountPage = new eAccountPage(driver);
		eMainPage eMainPage = new eMainPage(driver);

		eMainPage.openAccountSettings();
		String code = eAccountPage.getSignInCode();

		openChromeBrowser();
		return code;
	}

	private void invokeSignInCode(String phoneNumber) {
		clickIfPossible(logOutButton);

		scrollDownUntilElementVisible(continueButton);
		waitThenClick(phoneNumberInput);
		clear(phoneNumberInput);
		input(phoneNumberInput, kiribatiCountryCode + phoneNumber);
//		waitThenClick(continueButton);
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		waitForLoad(signInButton);
	}

	public void loginWithSignInCode(String code)
	{
		waitForLoad(signInInput);
		input(signInInput, code);
		waitThenClick(signInButton);
	}

	public void openChromeBrowser()
	{
		driver.activateApp("com.android.chrome");
	}

	public void openStealthTalk()
	{
		driver.activateApp(getStealthTalkPackage());
	}

	@Deprecated
	public void webViewExample()
	{
		// Change driver context:
		driver.context("WEBVIEW_chrome");

		WebElement button = driver.findElement(By.xpath("//*[contains(@id, 'zoid-paypal-button')]"));
		//Scroll to element in webview:
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);" , button);

		driver.findElement(By.xpath("//*[contains(@id, 'zoid-paypal-button')]")).click();

		driver.context("NATIVE_APP");
	}

	public void renewalWithDiscount()
	{
		waitForAnyElementLong(renewalTitle, renewSubscriptionTitle);

		scrollDownUntilElementVisible(promocodeInput);
		clearThenInput(promocodeInput, getProperty("discountCode"));
		scrollDownUntilElementVisible(discountDescription);
		waitForLoad(discountDescription);
		Assert.assertEquals(getText(discountDescription), discountDescriptionLabel);
		scrollDownUntilElementVisible(paypalButton);
		click(paypalButton);
	}

	public void  renewalWithPrepaid()
	{
		waitForAnyElementLong(renewalTitle, renewSubscriptionTitle);

		scrollDownUntilElementVisible(promocodeInput);
		clearThenInput(promocodeInput, getProperty("prepaidCode"));
		scrollDownUntilElementVisible(discountDescription);
		waitForLoad(discountDescription);
		Assert.assertEquals(getText(discountDescription), prepaidDescriptionLabel);
		scrollDownUntilElementVisible(extendButton);
		click(extendButton);
	}
}
