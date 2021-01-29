package Pages;


import STUtils.eUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;

import static Pages.eCommonMethods.Permission.LOCATION;
import static Pages.eCommonMethods.Permission.MICROPHONE;
import static STUtils.eDriverHandler.Device;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.await;

public class eContactInfoPage extends eCommonMethods
{
    private By usualCall                        = By.id("com.app.stealthtalk.testers:id/fragment_contact_info_call_block");
    private By secureCallButton                 = By.id("com.app.stealthtalk.testers:id/fragment_contact_info_call_secure_block");
    private By passInput						= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_passcode_edit");
    private By verification						= By.id("com.app.stealthtalk.testers:id/fragment_contact_info_trust_conditions_block");
    private By callToVerifyButton				= By.id("com.app.stealthtalk.testers:id/verification_level_verify_button");
    private By getBack							= MobileBy.AccessibilityId("Navigate up");
    private By verificationStatus				= By.id("com.app.stealthtalk.testers:id/activity_collapsed_toolbar_subtitle");
    private By unVerifyButton                   = By.xpath("//*[@text='Remove' or @text='REMOVE']");
    private By verifyInPerson					= By.xpath("(//*[@resource-id='com.app.stealthtalk.testers:id/verification_level_verify_button'])[2]");
    private By addToFavorite					= MobileBy.AccessibilityId("Add to Favorites");
    private By removeFromFavorites				= MobileBy.AccessibilityId("Remove from Favorites");
    private By contactInfoContainer				= By.id("com.app.stealthtalk.testers:id/activity_collapsed_toolbar_appbar_layout");
    private By tryAgainButton					= By.id("com.app.stealthtalk.testers:id/activity_in_person_verification_try_again");
    private By verificationMessage				= By.id("com.app.stealthtalk.testers:id/activity_in_person_verification_comment");
    private By firstUserStatusVerifiedInPerson	= By.xpath("//*[*[@text='"+getFirstDeviceProperty("AccountName")+"']]/*[@resource-id='com.app.stealthtalk.testers:id/ab_record_status' and @text='Verified in person']");
    private By secondUserAdvancedSecurityStatus = By.xpath("//*[*[@text='"+getSecondDeviceProperty("AccountName")+"']]/*[@resource-id='com.app.stealthtalk.testers:id/ab_record_status' and @text='Advanced security']");
    private By firstUserIcon					= By.xpath("//*[*[@text='"+getFirstDeviceProperty("AccountName")+"']]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status']");
    private By secondUserIcon					= By.xpath("//*[*[@text='"+getSecondDeviceProperty("AccountName")+"']]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status']");
    private By authenticationProceedButton		= By.id("com.app.stealthtalk.testers:id/fragment_verification_in_person_permission_proceed");
    private By verificationStatusIcon			= By.id("com.app.stealthtalk.testers:id/contact_view_verification_status");
	private By callBar							= By.id("com.app.stealthtalk.testers:id/activity_call_bar_layout");
	private By authorizationIcon				= By.xpath("//android.widget.ImageView[@resource-id='com.app.stealthtalk.testers:id/contact_view_authorization_status' and @content-desc='Authorized']");

	public eContactInfoPage(Device device)
	{
		super(getDriver(device));
	}

    public eContactInfoPage(AndroidDriver driver)
    {
        super(driver);
    }

    public eCallPage executeSecuredCall()
    {
        click(usualCall);
        eUtils.allowPermission(driver);
        return new eCallPage(driver);
    }

    public eCallPage executeStealthCall()
    {
        click(secureCallButton);
		//waitForAnyElementShort(callBar, enterPasscodeInputField);
        waitForAbsenceShort(secureCallButton);
		if(isPresent(enterPasscodeInputField))
		{
			sendKeys(getDefaultPasscode());
		}
		allowPermission(MICROPHONE);
        return new eCallPage(driver);
    }

    public void openVerificationPage()
	{
		click(verification);
		waitForLoadShort(callToVerifyButton);
	}

    public eCallPage callToVerify()
    {
		click(callToVerifyButton);
		waitForLoadShort(enterPasscodeInputField);
        if(isPresent(enterPasscodeInputField))
        {
			inputPasscode();
		}
		allowPermission(MICROPHONE);
        return new eCallPage(driver);
    }

    public void verifyInPerson()
    {
        scrollDown();
        click(verifyInPerson);
		eUtils.allowPermission(driver);
		if(isPresent(enterPasscodeInputField))
		{
			inputPasscode();
		}
    }

    public void getBack()
    {
        click(getBack);
    }

    public void checkVerificationStatus(VerificationType verificationType)
    {
    	scrollUpUntilElementVisible(verificationStatus);
    	switch (verificationType)
		{
			case VIA_CALL_AUTHORIZED:
				Assert.assertEquals(waitThenGetText(verificationStatus),"Advanced security");
				Assert.assertEquals(getContentDescription(verificationStatusIcon), "BY_CALL auth/true");
				Assert.assertTrue(isPresent(authorizationIcon));
				break;
			case VIA_CALL_UNAUTHORIZED:
				Assert.assertEquals(waitThenGetText(verificationStatus),"Stealth mode disabled");
				Assert.assertEquals(getContentDescription(verificationStatusIcon), "BY_CALL auth/false");
				Assert.assertFalse(isPresent(authorizationIcon));
				break;
			case VIA_BLUETOOTH_AUTHORIZED:
				Assert.assertEquals(waitThenGetText(verificationStatus),"Advanced security");
				Assert.assertEquals(getContentDescription(verificationStatusIcon), "IN_PERSON auth/true");
				Assert.assertTrue(isPresent(authorizationIcon));
				break;
			case VIA_BLUETOOTH_UNAUTHORIZED:
				Assert.assertEquals(waitThenGetText(verificationStatus),"Stealth mode disabled");
				Assert.assertEquals(getContentDescription(verificationStatusIcon), "IN_PERSON auth/false");
				Assert.assertFalse(isPresent(authorizationIcon));
				break;
			case NOT_VERIFIED_AUTHORIZED:
				Assert.assertEquals(waitThenGetText(verificationStatus),"High security");
				Assert.assertEquals(getContentDescription(verificationStatusIcon), "NOT_VERIFIED auth/true");
				Assert.assertTrue(isPresent(authorizationIcon));
				break;
			case NOT_VERIFIED_UNAUTHORIZED:
				Assert.assertEquals(waitThenGetText(verificationStatus),"High security");
				Assert.assertEquals(getContentDescription(verificationStatusIcon), "NOT_VERIFIED auth/false");
				Assert.assertFalse(isPresent(authorizationIcon));
				break;
		}
    }

    public void dropVerification()
    {
        click(verification);
		scrollDownUntilElementVisible(unVerifyButton);
        click(unVerifyButton);
    }

    public void addToFavorite()
    {
        waitForAnyElementShort(addToFavorite, removeFromFavorites);
        click(addToFavorite);
        getBack();
    }

    public void removeFromFavorites()
    {
        waitForAnyElementShort(addToFavorite, removeFromFavorites);
        click(removeFromFavorites);
    }

    public void verifyContactInfoIsOpened(String contactName)
    {
    	By contactInfoName = By.xpath("//android.widget.TextView[@resource-id='com.app.stealthtalk.testers:id/activity_collapsed_toolbar_title'  and @text='" + contactName + "']");
    	waitForLoadShort(contactInfoName);
    	getBack();
    }

    private void scrollDown()
    {
        await(1000);

        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(windowSize.width/2, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width/2, 1);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();

        await(1000);
    }

    public void verifyContactVerificationInContactList()
    {
        waitForLoad(secondUserAdvancedSecurityStatus);
        Assert.assertEquals(getAttribute(secondUserIcon, "content-desc"), "IN_PERSON auth/true");
    }

    public void allowBluetoothAuth()
	{
		clickIfPossible(authenticationProceedButton);
		waitForAbsenceShort(authenticationProceedButton);
		allowPermission(LOCATION);
	}
}
