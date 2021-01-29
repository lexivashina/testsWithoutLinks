package Pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Date;

import static STUtils.eSTProperties.*;
import static STUtils.eUtils.await;
import static STUtils.eUtils.verifyBruteForceTime;

public class eSidePanelPage extends eCommonMethods
{

    private eMainPage mainPage;

    By settingsButton				= By.xpath("//*[@text='Settings']");
    By aboutButton					= By.id("com.app.stealthtalk.testers:id/drawer_menu_item_about");
    By authorizationButton			= By.id("com.app.stealthtalk.testers:id/drawer_header_user_authorization_button");
    By digitPassInput				= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_pin_edit");
    By customPassInput				= By.id("com.app.stealthtalk.testers:id/passcode_edit_edit");
    By okButton						= By.id("android:id/button1");
    By authorizedStatus				= By.xpath("//android.widget.TextView[@text='Stealth mode enabled']");
    By websiteLink					= By.id("com.app.stealthtalk.testers:id/fragment_about_links_website");
    By feedbackLink					= By.id("com.app.stealthtalk.testers:id/fragment_about_links_contact_us");
	By authorizationErrorContainer	= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_error");
	static By authorizeButton		= By.xpath("//android.widget.Button[@text='ENABLE' or @text='Enable']");
	By chromeImage					= By.xpath("//*[@text='Chrome']");
	By accountSettings				= By.id("com.app.stealthtalk.testers:id/design_menu_item_text");
    By openUrlInChrome              = By.xpath("//android.widget.GridView/android.widget.LinearLayout");

    String authorizationStatusXPath = "//android.widget.TextView[contains(@text, 'PHONE_NUMBER')]/following-sibling::android.widget.TextView";
	String actualBanTime;

    static String authorizationBruteForceBanLabel 	= "Too many unsuccessful attempts.\nTry again later:\n";
    static String stealthModeEnabledLabel			= "Stealth mode enabled";


	eSidePanelPage(AndroidDriver<AndroidElement> driver)
    {
        super(driver);
        mainPage = new eMainPage(driver);
    }

    public eSettingsPage openSettings()
    {
        click(settingsButton);
        return new eSettingsPage(driver);
    }

    public eAccountPage openAccountSettings()
	{
		click(accountSettings);
		return new eAccountPage(driver);
	}

    public void unauthorize()
    {
        mainPage.openSidePage();
        click(authorizationButton);
    }

    public void authorize()
    {
		if(isPresent(authorizeButton))
		{
			authorize("digit", getDefaultPasscode());
		}
    }

    public void authorize(String passType,String passValue)
    {
        click(authorizationButton);
        switch (passType)
        {
            case "digit":
            {
				waitForLoadShort(enterPasscodeInputField);
				sendKeys(passValue);
                break;
            }
            case "custom":
            {
                input(customPassInput,passValue);
                click(okButton);
                break;
            }
            default:break;
        }
        waitForLoadShort(authorizedStatus);
    }

    public void openWebsite()
    {
        click(aboutButton);
        click(websiteLink);

        waitThenClick(openUrlInChrome);
    }

	public void verifyWebsite()
	{
		By urlBarWithWebSite = By.id("com.android.chrome:id/url_bar");
		waitForLoadShort(urlBarWithWebSite);
	}

    public void openFeedback()
    {
        click(aboutButton);
        scrollDownUntilElementVisible(feedbackLink);
        click(feedbackLink);

        waitThenClick(openUrlInChrome);
    }

	public void verifyFeedback()
	{
	    waitForLoadShort(urlAdressInChrome);
	}

	public void invokeAuthorizationBan()
	{
		click(authorizationButton);
		waitForLoadShort(enterPasscodeInputField);
		sendKeys(getProperty("invalid4digitPasscode"));

		int attempt = 0;
		while(attempt++ < 2)
		{
			Assert.assertEquals(getText(authorizationErrorContainer), "Incorrect Passcode");
			clearPasscode();
			sendKeys(getProperty("invalid4digitPasscode"));
		}
	}

    public void verifyAuthorizationBan(String phoneNumberValue, long minutes)
    {
        long currentTime				= new Date().getTime();
        Date expectedBanDate			= new Date (currentTime + minutes*60000);
        String actualBruteForceMessage	= getText(authorizationErrorContainer);

        verifyBruteForceBanTimeOnAuthorization(expectedBanDate);

        inputPasscode(getProperty("default4digitPass"));
		Assert.assertTrue(actualBruteForceMessage.contains(actualBanTime));

		authorizationAfterBanExpiration(phoneNumberValue, expectedBanDate);
	}

	private void authorizationAfterBanExpiration(String phoneNumberValue, Date expectedBanDate) {
		By authorizationStatus			= By.xpath(authorizationStatusXPath.replace("PHONE_NUMBER", phoneNumberValue));
		long expectedBanTime 			= expectedBanDate.getTime();
		long currentTime				= new Date().getTime();

		await(expectedBanTime - currentTime);

		inputPasscode(getProperty("default4digitPass"));
		Assert.assertEquals(getText(authorizationStatus), stealthModeEnabledLabel);
	}

	public void verifyBruteForceBanTimeOnAuthorization(Date expectedBanTime)
	{
		String actualBruteForceMessage	= getText(authorizationErrorContainer);
		Assert.assertTrue(actualBruteForceMessage.contains(authorizationBruteForceBanLabel));
		actualBanTime = actualBruteForceMessage.replace(authorizationBruteForceBanLabel, "");
		verifyBruteForceTime(actualBanTime, expectedBanTime);
	}
}
