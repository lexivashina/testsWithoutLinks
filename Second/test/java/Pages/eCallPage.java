package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.Assert;

import java.util.List;

import static Pages.eCommonMethods.Permission.MICROPHONE;
import static STUtils.eDriverHandler.Device;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eSTProperties.VerificationType;
import static STUtils.eSTProperties.getThirdDeviceProperty;
import static STUtils.eUtils.await;

public class eCallPage extends eCommonMethods
{
    By passInput								= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_pin_edit");
    By secureCallIcon							= By.id("com.app.stealthtalk.testers:id/fragment_call_active_private_line_image");
	By secureCallLabel							= By.id("com.app.stealthtalk.testers:id/fragment_call_outcome_animation_label");
    By verifyButton								= By.id("com.app.stealthtalk.testers:id/fragment_verification_call_dialog_verify_button");
    By terminateVerifyCall						= By.id("com.app.stealthtalk.testers:id/fragment_verification_call_cancel_button");
    By attachMember								= By.id("com.app.stealthtalk.testers:id/fragment_call_active_attach");
    By memberPhoto								= By.id("com.app.stealthtalk.testers:id/contact_view_photo");
    By messageButton							= By.id("com.app.stealthtalk.testers:id/fragment_call_active_chat");
    By profileName								= By.xpath("//android.widget.LinearLayout[@resource-id='com.app.stealthtalk.testers:id/fragment_call_partner']/android.widget.TextView");
    By firstStatus								= By.id("com.app.stealthtalk.testers:id/fragment_call_active_timer");
    By soundVerification						= By.id("com.app.stealthtalk.testers:id/fragment_call_active_debug_info_label");
    By soundVerificationSecondary				= By.id("com.app.stealthtalk.testers:id/fragment_call_active_debug_info_label_ex");
    By muteButtonOff							= MobileBy.AccessibilityId("default");
    By muteButtonOn								= MobileBy.AccessibilityId("muted");
    By speakerButtonOff							= MobileBy.AccessibilityId("normal");
    By speakerButtonOn							= MobileBy.AccessibilityId("speaker");
    By memberToAttach							= By.xpath("//*[@text='" + getThirdDeviceProperty("AccountName") + "']");
    By stealthTabButton                         = By.xpath("//androidx.appcompat.app.ActionBar.Tab[3]");
    By callNotification							= By.id("com.app.stealthtalk.testers:id/activity_call_bar_layout");
    By answerCallButton							= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_call_action_trigger']/android.widget.ImageButton[1]");
    By answerVerificationCallButton				= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_verification_call_action_trigger']/android.widget.ImageButton[1]");
    By contactVerificationStatus				= By.id("com.app.stealthtalk.testers:id/fragment_call_active_count_participant");
	By contactVerificationIcon					= By.id("com.app.stealthtalk.testers:id/contact_view_verification_status");
    By terminateCallButton						= By.xpath("//android.widget.FrameLayout[@resource-id='com.app.stealthtalk.testers:id/fragment_call_action_trigger']/android.widget.ImageButton[last()]");
	By authorizationIcon						= By.xpath("//android.widget.ImageView[@resource-id='com.app.stealthtalk.testers:id/contact_view_authorization_status' and @content-desc='Authorized']");
	By rejectCallButton							= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_call_action_trigger' or @resource-id='com.app.stealthtalk.testers:id/fragment_verification_call_action_trigger']/android.widget.ImageButton[2]");
//	By verificationStatusVerifiedViaCall		= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_call_active_count_participant' and @text='Verified via call']");
//	By verificationIconVerifiedViaCall			= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='BY_CALL auth/true']");
//	By verificationStatusNotVerified			= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_call_active_count_participant' and@text='Not verified']");
//	By verificationIconNotVerifiedAuthorized	= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='NOT_VERIFIED auth/true']");

	public eCallPage(Device device)
	{
		super(getDriver(device));
	}

    public eCallPage(AndroidDriver<AndroidElement> driver)
    {
        super(driver);
    }

    public void answerSecureCall()
    {
		if (isAbsent(answerCallButton))
		{
			tapCallNotification();
		}
        horizontalScroll(waitThenReturn(answerCallButton));
		allowPermission(MICROPHONE);
    }

    public void answerVerificationCall()
    {
        if (! isPresent(answerVerificationCallButton))
        {
            tapCallNotification();
        }
        horizontalScroll(waitThenReturn(answerVerificationCallButton));
        inputPasscode();
		allowPermission(MICROPHONE);
    }

    public void answerStealthCall()
    {
        answerSecureCall();
        if(isPresent(enterPasscodeInputField))
		{
			inputPasscode();
		}
    }

    public String openCallViewAndGetProfileName()
    {
        click(callNotification);
        return getProfileNameInOpenCallView();
    }

    public String getProfileNameInOpenCallView()
    {
        return getText(profileName);
    }

    public String getCallStatus()
    {
        if (! isPresent(firstStatus))
        {
            tapCallNotification();
        }
//        wait.shortWaitForLoad(callNotification, firstStatus);
//        wait.clickIfPossible(callNotification);
        return waitThenGetText(firstStatus);
    }

    public void cancelCall()
    {
        click(terminateCallButton);
    }

    public void verifySecureCall()
    {
        clickIfPossible(callNotification);
        waitForAnyElementShort(secureCallLabel);
    }

    private void horizontalScroll(MobileElement element)
    {
        //await(2000);
		Dimension windowSize = driver.manage().window().getSize();
        Point answerButtonLocation = element.getLocation();
        PointOption startPoint = PointOption.point(answerButtonLocation.getX(),answerButtonLocation.getY());
        PointOption finishPoint;

        if( answerButtonLocation.getX() > windowSize.width/2)
		{
			finishPoint = PointOption.point(1, answerButtonLocation.getY());
		}
        else
		{
			finishPoint = PointOption.point(windowSize.width-1, answerButtonLocation.getY());
		}

        new TouchAction<>(driver).press(startPoint).moveTo(finishPoint).release().perform();
        await(1000);
    }

    public void verify()
    {
        Assert.assertEquals(getContentDescription(contactVerificationIcon), "NOT_VERIFIED auth/true");
        waitThenClick(verifyButton);
    }

    public void terminateVerificationCall()
    {
        click(terminateVerifyCall);
    }

    public void attachMember()
    {
        await(3000);
        clickIfPossible(callNotification);
        click(attachMember);
        scrollTop();
        if (returnList(memberToAttach).size()==0) scrollBottom();
        click(stealthTabButton);
        click(memberToAttach);
    }

    private  void scrollTop()
    {
        await(1000);

        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(windowSize.width/2, 100);
        PointOption finishPoint = PointOption.point(windowSize.width/2,  windowSize.height/2);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();

        await(1000);
    }

    private  void scrollBottom()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();
        PointOption startPoint = PointOption.point(windowSize.width/2, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width/2,  1);
        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        await(1000);
    }

    public void verifyMemberAttached()
    {
        List<AndroidElement> photos = returnList(memberPhoto);
        Assert.assertEquals(photos.size(),3);
    }

    public eChatPage openChat()
    {
        clickIfPossible(callNotification);
        waitThenClick(messageButton);
        return new eChatPage(driver);
    }

    //TODO REFACTORING New solution for sound testing required
    public void verifySound()
    {
//        String soundDebug = waitThenGetText(soundVerification);
//        Assert.assertFalse(soundDebug.isEmpty());
    }

    //TODO REFACTORING New solution for sound testing required
    public void verifySoundInGroup()
    {
        verifySound();
//        String soundDebug = waitThenGetText(soundVerificationSecondary);
//        Assert.assertFalse(soundDebug.isEmpty());
    }

    public void verifyMuteButton()
    {
        clickIfPossible(callNotification);
        click(muteButtonOff);
        click(muteButtonOn);
    }

    public void verifySpeakerButton()
    {
        click(speakerButtonOff);
        click(speakerButtonOn);
    }

	public void checkVerificationStatus(VerificationType verificationType)
	{
		if(isPresent(callNotification))
		{
			click(callNotification);
		}

		switch (verificationType)
		{
			case VIA_CALL_AUTHORIZED:
				Assert.assertEquals(getText(contactVerificationStatus), "Advanced security");
				Assert.assertEquals(getContentDescription(contactVerificationIcon), "BY_CALL auth/true");
				Assert.assertTrue(isPresent(authorizationIcon));
				break;
			case VIA_CALL_UNAUTHORIZED:
				Assert.assertEquals(getText(contactVerificationStatus), "Stealth mode disabled");
				Assert.assertEquals(getContentDescription(contactVerificationIcon), "BY_CALL auth/false");
				Assert.assertFalse(isPresent(authorizationIcon));
				break;
			case VIA_BLUETOOTH_AUTHORIZED:
				Assert.assertEquals(getText(contactVerificationStatus), "Advanced security");
				Assert.assertEquals(getContentDescription(contactVerificationIcon), "IN_PERSON auth/true");
				Assert.assertTrue(isPresent(authorizationIcon));
				break;
			case VIA_BLUETOOTH_UNAUTHORIZED:
				Assert.assertEquals(getText(contactVerificationStatus), "Stealth mode disabled");
				Assert.assertEquals(getContentDescription(contactVerificationIcon), "IN_PERSON auth/false");
				Assert.assertFalse(isPresent(authorizationIcon));
				break;
			case NOT_VERIFIED_AUTHORIZED:
				Assert.assertEquals(getText(contactVerificationStatus), "High security");
				Assert.assertEquals(getContentDescription(contactVerificationIcon), "NOT_VERIFIED auth/true");
				Assert.assertTrue(isPresent(authorizationIcon));
				break;
			case NOT_VERIFIED_UNAUTHORIZED:
				Assert.assertEquals(getText(contactVerificationStatus), "High security");
				Assert.assertEquals(getContentDescription(contactVerificationIcon), "NOT_VERIFIED auth/false");
				Assert.assertFalse(isPresent(authorizationIcon));
				break;
		}
	}

    public void rejectCall()
    {
        if (! isPresent(rejectCallButton))
        {
            tapCallNotification();
        }
        tapCallNotification();
        horizontalScroll(waitThenReturn(rejectCallButton));
    }
}

