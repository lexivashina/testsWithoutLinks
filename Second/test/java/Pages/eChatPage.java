package Pages;

import STUtils.eUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static Pages.eCommonMethods.Permission.*;
import static STUtils.eDriverHandler.*;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.await;
import static STUtils.eUtils.verifyTime;

public class eChatPage extends eCommonMethods
{
    private eMainPage mainPage;

    By chatTitle 												= By.id("com.app.stealthtalk.testers:id/toolbar_field_title_text");
    By messageInput 											= By.id("com.app.stealthtalk.testers:id/chat_edit_edit");
    By sendMessageButton 										= By.id("com.app.stealthtalk.testers:id/chat_edit_main_action");
    By sentMessage 												= By.xpath("(//*[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_outgoing_text'])[last()]");
    By passInput 												= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_pin_edit");
    By okButton													= By.xpath("//android.widget.Button[@text='OK']");
    By usualCall                                                = MobileBy.AccessibilityId("Secure Call");
    By stealthCallButton										= MobileBy.AccessibilityId("Stealth Call");
    By timeValuePicker 											= By.xpath("//android.widget.NumberPicker/android.widget.Button[2]");
    By selectedTimeValue 										= By.id("android:id/numberpicker_input");
    By recentChatItem 											= By.id("com.app.stealthtalk.testers:id/list_recent_item_view");
    By sendNewPhoto 											= By.xpath("//android.widget.TextView[@text='Take new photo']");
    By sendGalleryPhoto 										= By.xpath("//android.widget.TextView[@text='Select from Gallery']");
    By sentPicture 												= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()]//android.widget.ImageView[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_image_outgoing_image_picture']");
    By receivedPicture                                          = By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()]//android.widget.ImageView[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_incoming_image_picture']");
    //By sentPicture 											= By.id("com.app.stealthtalk.testers:id/list_chat_item_image_outgoing_image_picture");
    By logoIcon 												= By.id("com.app.stealthtalk.testers:id/toolbar_field_logo_image");
    By backButton 												= MobileBy.AccessibilityId("Navigate up");
    By photoToSent 												= By.xpath("(//*[@resource-id='com.android.documentsui:id/icon_thumb']/ancestor::*)[last()-1]");
//    By photoToSent 												= By.id("com.android.documentsui:id/icon_thumb");
    By deleteSelectedMessage 									= MobileBy.AccessibilityId("Delete");
    By forwardSelectedMessage 									= MobileBy.AccessibilityId("Forward");
    By sendForwardMessageToSelectedChat 						= By.id("com.app.stealthtalk.testers:id/fragment_multi_choice_contacts_send");
    By shareMessage 											= By.id("com.app.stealthtalk.testers:id/menu_share");
    By callBar                                                  = By.id("com.app.stealthtalk.testers:id/activity_call_bar_layout");
    By timer 													= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_chat_chat']/android.widget.TextView");
    By galleryMenu 												= MobileBy.AccessibilityId("Show roots");
    By imagesFolder 											= By.xpath("//android.widget.TextView[@text='Images']");
    By downloadFolder 											= By.xpath("//android.widget.TextView[@text='Download']");
    By imagesFolderOnXT1053                                     = By.xpath("//*[@text='Images']");
    By imageInGalleryOnXT1053                                   = By.id("com.android.documentsui:id/icon_thumb");
    By copyMessageButton 										= By.id("com.app.stealthtalk.testers:id/chat_menu_action_mode_copy");
    By thirdDeviceChat 											= By.xpath("//*[@text = '" + getThirdDeviceProperty("AccountName") + "']");
    By selectPhotoSource 										= By.xpath("//android.widget.TextView[@resource-id='com.app.stealthtalk.testers:id/alertTitle' and @text='Select source']");
    By cancelButton 											= By.xpath("//android.widget.Button[@text='CANCEL' or @text='Cancel']");
    By emptyMessageInputField 									= By.xpath("//android.widget.EditText[@resource-id='com.app.stealthtalk.testers:id/chat_edit_edit' and @text='Type your message']");
    By chatView 												= By.id("com.app.stealthtalk.testers:id/chat_content");
    By lastReceivedMessage 										= By.xpath("(//*[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_incoming_text'])[last()]");
    By lastReceivedSecureMessage 								= By.xpath("(//*[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_blocked_icon'])[last()]");
    By lastReceivedMessageWithSecuredContent 					= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout or android.widget.ImageView][last()]/android.widget.ImageView[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_blocked_icon']");
    By enableSecureForwardButton 								= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_share_send_chat_edit']/android.widget.ImageButton");
    //By blockedIconOnLastSentMessage 							= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()]/android.widget.TextView[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_secure_icon']");
    By lastMessageText 											= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[@class='android.view.ViewGroup' or @class='android.widget.FrameLayout'][last()]/*/android.widget.TextView");
    By lastMessage 												= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()]/android.widget.TextView[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_secure_icon']");
    By lastMessageStatus 										= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[@class='android.view.ViewGroup' or @class='android.widget.FrameLayout'][last()]/*/*/android.widget.TextView");
    By lastMessageWithBlockedIcon 								= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()]/android.widget.ImageView[@resource-id='com.app.stealthtalk.testers:id/list_chat_item_blocked_icon']");
    By galleryIconSamsungS5 									= By.xpath("//*[*[*[@text='Gallery']]]");
	By photoInGallerySamsungS5									= By.className("com.sec.samsung.gallery.glview.composeView.PositionControllerBase$ThumbObject");
	By blockedMessageIcon										= By.id("com.app.stealthtalk.testers:id/list_chat_item_blocked_icon");
	By secureModeButton											= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/chat_edit_edit']//following-sibling::android.widget.ImageButton[1]");
	By lastMessageVerificationIconVerifiedViaCallAuthorized		= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='BY_CALL auth/true']");
	By lastMessageVerificationIconVerifiedViaCallUnauthorized	= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='BY_CALL auth/false']");
	By endCallButton											= By.id("com.app.stealthtalk.testers:id/call_bar_end_btn");
	By lastMessageVerificationIconNotVerifiedAuthorized			= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='NOT_VERIFIED auth/true']");
	By lastMessageVerificationIconNotVerifiedUnauthorized		= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='NOT_VERIFIED auth/false']");
	By lastMessageVerificationIconVerifiedInPersonAuthorized	= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='IN_PERSON auth/true']");
	By lastMessageVerificationIconVerifiedInPersonUnauthorized	= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_verification_status' and @content-desc='IN_PERSON auth/false']");
	By lastMessageAuthorizationIcon								= By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/android.view.ViewGroup[last()]//*[@resource-id='com.app.stealthtalk.testers:id/contact_view_authorization_status' and @content-desc='Authorized']");
	By photosFolderAndDriveSamsungS6							= By.xpath("//*[@text='Photos']");
	By firstPhotoSamsungS6  									= By.xpath("//*[contains(@content-desc, 'Photo taken')]");
	By showRoots                                                = MobileBy.AccessibilityId("Show roots");
	By imageFolderHuawei                                        = By.xpath("//*[@resource-id='android:id/title' and @text='Images']");
	By albumHuawei                                              = By.xpath("//*[@resource-id='com.android.documentsui:id/thumbnail']");
	By pictureNameHuawei                                        = By.xpath("//*[@resource-id='com.android.documentsui:id/nameplate']");

	public static String messageSeenLabel								= "Seen";
	public static String messageSentLabel 								= "Sent";
	public static String messageDeliveredLabel							= "Delivered";
	public static String messageTypeDefault 							= "Default";
	public static String messageTypeSecure 								= "Secure";
	public static String messageTypeSecureSelfDestruct 					= "Secure & Self-Destructed";
	public static String messageTypeSelfDestruct						= "Self-Destructed";
	public static String messageToSend 									= "Text";

	public eChatPage(AndroidDriver driver)
    {
        super(driver);
        mainPage = new eMainPage(driver);
    }

	public eChatPage(Device device)
	{
		super(getDriver(device));
		mainPage = new eMainPage(getDriver(device));
	}

	public AndroidElement getLastMessage() {
		return driver.findElement(lastMessage);
	}

	public String getChatTitle()
    {
        return getText(chatTitle);
    }

    public String getLastSentMessage()
    {
        return waitThenGetText(sentMessage);
    }

    public void verifyMessageNotExists(String message)
	{
		Assert.assertFalse(isPresent(By.xpath("//*[@text='" + message + "']")));
	}

    public String getLastReceivedMessage()
    {
        return getLastReceivedMessage(false);
    }

    public String getLastReceivedSecureMessage()
    {
        return waitThenGetText(lastReceivedMessage);
    }

    public String getLastReceivedMessage(boolean secured)
    {
        if (secured)
        {
            turnOnSecuredMode();
        }
        await(1000);
        List<AndroidElement> messages = returnList(lastReceivedMessage);
        if (messages.isEmpty())
            return null;
        else
            return messages.get(0).getText();
    }

    public String sendMessage(String textToSend)
    {
        return sendMessage(textToSend,false,false,"");
	}

	public void sendMessages(String[] messages)
	{
		String[] deviceTime = new String[messages.length];

		for(int x = 0; x < messages.length; x++)
		{
			input(messageInput, messages[x]);
			click(sendMessageButton);
		}
	}

    public String sendMessage(String textToSend,boolean secured)
    {
        return sendMessage(textToSend,secured,false,"");
    }

    public String sendMessage(String textToSend,boolean secured, boolean time, String timeValue)
    {
        if (secured) turnOnSecuredMode();
        if (time) turnOnTimeMode(timeValue);

        input(messageInput, textToSend);
        String deviceTime = driver.getDeviceTime();
        click(sendMessageButton);
        await(3000);
        return deviceTime;
    }

    private void turnOnSecuredMode()
    {
    	if(isAbsent(stealthCallButton))
		{
			click(secureModeButton);
			waitForAnyElementShort(enterPasscodeInputField, stealthCallButton);
			if(isPresent(enterPasscodeInputField))
			{
				inputPasscode();
			}
		}
    }

    private void turnOnTimeMode(String timeValue)
    {
        //TODO Try using setValue() to set timeMode
        click(timer);
        await(1000);
        scrollForTimeValue(timeValue);
        clickByCoordinates(okButton);
    }

    public void turnOffTimeMode()
    {
        click(timer);
        await(1000);
        scrollForTimeValue("Off");
        click(okButton);
    }

    public void executeUsualCall()
    {
        click(usualCall);
        waitForAbsenceShort(usualCall);
        allowPermission(MICROPHONE);
    }

	public void executeStealthCall()
    {
        new eChatPage(driver).turnOnSecuredMode();
        click(stealthCallButton);
        waitForAbsenceShort(stealthCallButton);
        allowPermission(MICROPHONE);
    }

    private void scrollForTimeValue(String timeValue)
    {
        MobileElement button = waitThenReturn(timeValuePicker);
        Point center = button.getCenter();

        LongPressOptions longPressOptions = new LongPressOptions();
        longPressOptions.withPosition(PointOption.point(center.getX(),center.getY()));
        longPressOptions.withDuration(Duration.ofMillis(500));
        do
        {
            new TouchAction<>(driver).longPress(longPressOptions).release().perform();
        }
        while (!waitThenGetText(selectedTimeValue).equals(timeValue));
    }

    public void horizontalScroll()
    {
        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(100, windowSize.height / 2);
        PointOption finishPoint = PointOption.point(windowSize.width - 100, windowSize.height / 2);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
    }

    public void verifyRecentTabOpened()
    {
        waitForAbsenceShort(sendMessageButton);
        isPresent(recentChatItem);
    }

    public void attachPhotoFromCamera()
    {
        click(sendMessageButton);
		click(sendNewPhoto);
		waitForAbsenceShort(sendNewPhoto);
		takePhoto();
//		allowCameraPermission();
//		await(5000);
//        switch(getPhoneModel(driver))
//        {
//			case MOTOGPLAY:
//				driver.pressKey(new KeyEvent(AndroidKey.CAMERA));
//				await(7000);
//				new TouchAction<>(driver).tap(PointOption.point(437, 1037)).perform();
//				break;
//			case XT1053:
//				click(By.id("com.motorola.camera:id/preview_surface"));
//				click(By.id("com.motorola.camera:id/review_approve"));
//				break;
//			default:
//				driver.pressKey(new KeyEvent(AndroidKey.CAMERA));
//				waitThenClick(commonСameraOkButton);
//				break;
//        }
    }

    //Device specific SAMSUNG_S5 XT1053
    public void attachPhotoFromGallery()
    {
        click(sendMessageButton);
        click(sendGalleryPhoto);
        allowPermission(STORAGE);
        allowPermission(PHOTOS);

        switch(getPhoneModel(driver))
		{
			case SAMSUNG_S5:
				click(galleryIconSamsungS5);
				click(photoInGallerySamsungS5);
				break;
			case XT1053:
				click(galleryMenu);
				click(imagesFolderOnXT1053);
				click(imageInGalleryOnXT1053);
				click(imageInGalleryOnXT1053);
				break;
			case SAMSUNG_S6:
				click(photosFolderAndDriveSamsungS6);
				click(photosFolderAndDriveSamsungS6);
				click(firstPhotoSamsungS6);
				break;
            case HuaweiP:
                click(showRoots);
                click(imageFolderHuawei);
                click(albumHuawei);
                click(pictureNameHuawei);
                break;
			default:
				click(photoToSent);
				break;
		}
    }

    public void verifyAttachPhotoCancel()
    {
        click(sendMessageButton);
        click(cancelButton);
        waitForAbsenceShort(selectPhotoSource);
    }

    public void verifySentPhoto()
    {
        waitForLoadShort(sentPicture);
    }

    public void verifyReceivedPicture()
    {
		waitForLoadShort(receivedPicture);
    }

    public eContactInfoPage openContactInfo()
    {
        click(logoIcon);
        return new eContactInfoPage(driver);
    }

    public eMainPage getBack()
    {
        click(backButton);
        return new eMainPage(driver);
    }

    @Deprecated //TODO REFACTORING REMOVE THIS METHOD
    private void longPressOnElement(By locator)
    {
        new TouchAction<>(driver).
                longPress(LongPressOptions.longPressOptions().
                withElement(ElementOption.element(waitThenReturn(locator))))
                .waitAction().release().perform();
    }

    public String deleteMessage()
    {
        await(1000);
        String message = getLastSentMessage();
        longPressOnElement(sentMessage);
        click(deleteSelectedMessage);
        return message;
    }

    //TODO REFACTORING Specify contact name to forward message to
    public void forwardMessageToThirdDevice(Boolean asSecureMessage)
    {
        await(1000);
        longPressOnElement(lastReceivedMessage);
        click(forwardSelectedMessage);
        click(thirdDeviceChat);
        click(sendForwardMessageToSelectedChat);
        if (asSecureMessage)
        {
            click(enableSecureForwardButton);
        }
        click(shareMessage);
        waitForLoad(backButton);
        click(backButton);
        mainPage.openChatFromTab(getThirdDeviceProperty("AccountName"));
//        String message = getLastSentMessage();
//        click(backButton);
//        return message;
    }


    public String verifySecureMessage ()
    {
        click(lastReceivedSecureMessage);
        inputPasscode();
        eUtils.allowPermission(driver);
        return getLastReceivedSecureMessage();
    }

    public String verifySecureMessageNotAuthorized ()
    {
        click(lastReceivedMessageWithSecuredContent);
        inputPasscode();
        eUtils.allowPermission(driver);
        return getLastReceivedSecureMessage();
    }

    //TODO REFACTORING DEVICE: SAMSUNG_S5, XT1053
    public void verifyCopyMessage(String message)
    {
        longPressOnElement(sentMessage);
        click(copyMessageButton);

        switch(getPhoneModel(driver))
        {
            case SAMSUNG_S5:
            case XT1053:
                Assert.assertEquals(driver.getClipboardText(), message + "\n");
                break;
            default:
                click(backButton);
                if (!isPresentWithWait(thirdDeviceChat,2))
                scrollDownUntilElementVisible(thirdDeviceChat);
                click(thirdDeviceChat);
                waitForLoadShort(emptyMessageInputField);
                driver.pressKey(new KeyEvent(AndroidKey.PASTE));
                click(sendMessageButton);
                Assert.assertEquals(getLastSentMessage(), message);
                break;
        }
    }

    public void verifyChatOpenedFromCall()
    {
        waitForLoadShort(messageInput);
        verifyAvailability(callBar);
    }

    public void verifyMessageAbsence(String message)
    {
        By messageSelector = By.xpath("//androidx.viewpager.widget.ViewPager//android.widget.TextView[@text='" + message + "']");
        Assert.assertFalse(isPresent(messageSelector));
    }

    public void verifyMessageAvailability(String message)
    {
        waitForLoadShort(By.xpath("//androidx.viewpager.widget.ViewPager//android.widget.TextView[@text='" + message + "']"));
    }

	public void verifySecureMessageAvailability(String message)
	{
		if(isPresent(blockedMessageIcon))
		{
			openLastSecuredMessage();
		}
		verifyMessageAvailability(message);
	}

    public void verifyNewMessageThroughNotification(String message)
    {
        driver.openNotifications();
        By messageSelector = By.xpath("//android.widget.TextView[@text='" + message + "']");
        click(messageSelector);
    }

	public void verifyLastMessage(String expectedMessage)
	{
		By lastMessageStatus = By.xpath(String.format("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()][*/*[@text='%s']]", expectedMessage));
		waitForLoadShort(lastMessageStatus);
	}

	public void verifyLastSecureMessage(String expectedMessage)
	{
		verifyLastMessage(expectedMessage);
		Assert.assertTrue(isPresent(lastMessage));
	}

    public void verifyLastMessage(String expectedMessage, String expectedStatus)
    {
        //By lastMessageStatus = By.xpath(String.format("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[@class='android.view.ViewGroup' or @class='android.widget.FrameLayout'][last()][*/*[@text='%s']]//*[contains(@text,'%s')]", expectedMessage, expectedStatus));
		By lastMessageStatus = By.xpath(String.format("//*[@resource-id='com.app.stealthtalk.testers:id/chat_content_recycler']/*[android.widget.FrameLayout][last()][*/*[@text='%s']]//*[contains(@text,'%s')]", expectedMessage, expectedStatus));
        long controlTime = System.currentTimeMillis();
        String messageStatus = getText(lastMessageStatus);
        verifyTime(messageStatus, controlTime);
    }

    public void verifyLastMessage(String message, String status, String type)
    {
        verifyLastMessage(message, status);
		verifyLastMessageType(type);
	}

	public void verifyLastMessageType(String type)
	{
		switch(type) {
			case "Default":
				Assert.assertFalse(isPresent(lastMessage));
				break;
			case "Secure":
				Assert.assertEquals(getAttribute(lastMessage, "content-desc"), "secure");
				break;
			case "Self-Destructed":
				Assert.assertEquals(getAttribute(lastMessage, "content-desc"), "self-destruct");
				break;
			case "Secure & Self-Destructed":
				Assert.assertEquals(getAttribute(lastMessage, "content-desc"), "secure.self-destruct");
				break;
			default: Assert.fail("Unknown type of message");
		}
	}

	public void verifyLastMessage(String message, String status, String messageType, String time)
    {
        if(status.equals(messageSeenLabel) && time.equals("5s"))
        {
            verifyLastMessage(message, status);
        }
        else if(status.equals(messageSeenLabel))
        {
            verifyLastMessage(message, status, messageType);
            Assert.assertFalse(waitThenGetText(lastMessage).isEmpty());
        }
        else
        {
            verifyLastMessage(message, status, messageType);
            Assert.assertEquals(waitThenGetText(lastMessage).trim(), time);
        }
    }

    public void openLastSecuredMessage()
    {
        waitThenClick(lastMessageWithBlockedIcon);
		waitForLoadShort(enterPasscodeInputField);
		sendKeys(getDefaultPasscode());
	}

	public void checkMessageSenderVerificationStatus(VerificationType verificationType)
	{
		switch (verificationType)
		{
			case VIA_CALL_AUTHORIZED:
				waitForLoadShort(lastMessageVerificationIconVerifiedViaCallAuthorized);
				Assert.assertTrue(isPresent(lastMessageAuthorizationIcon));
				break;
			case VIA_CALL_UNAUTHORIZED:
				waitForLoadShort(lastMessageVerificationIconVerifiedViaCallUnauthorized);
				Assert.assertFalse(isPresent(lastMessageAuthorizationIcon));
				break;
			case VIA_BLUETOOTH_AUTHORIZED:
				waitForLoadShort(lastMessageVerificationIconVerifiedInPersonAuthorized);
				Assert.assertTrue(isPresent(lastMessageAuthorizationIcon));
				break;
			case VIA_BLUETOOTH_UNAUTHORIZED:
				waitForLoadShort(lastMessageVerificationIconVerifiedInPersonUnauthorized);
				Assert.assertFalse(isPresent(lastMessageAuthorizationIcon));
				break;
			case NOT_VERIFIED_AUTHORIZED:
				waitForLoadShort(lastMessageVerificationIconNotVerifiedAuthorized);
				Assert.assertTrue(isPresent(lastMessageAuthorizationIcon));
				break;
			case NOT_VERIFIED_UNAUTHORIZED:
				waitForLoadShort(lastMessageVerificationIconNotVerifiedUnauthorized);
				Assert.assertFalse(isPresent(lastMessageAuthorizationIcon));
				break;
		}
	}

	public void terminateCall()
	{
		click(endCallButton);
	}

	public void openLastSecuredMessage_legacy()
	{
		waitThenClick(lastMessageWithBlockedIcon);
		enterPasscode_legacy();
	}

	private void enterPasscode_legacy()
	{
		input(passcodeInput_legacy, getDefaultPasscode());
	}

	public String sendMessage_legacy(String textToSend, boolean secured)
	{
		if (secured)
		{
			if(isAbsent(stealthCallButton))
			{
				click(secureModeButton);
				waitForAnyElementShort(enterPasscodeInputField_legacy, stealthCallButton);
				if(isPresent(enterPasscodeInputField_legacy))
				{
					sendKeys(getDefaultPasscode());
				}
			}
		}

		input(messageInput, textToSend);
		String deviceTime = driver.getDeviceTime();
		click(sendMessageButton);
		await(3000);
		return deviceTime;
	}

	public void verifySecureMessageAvailability_legacy(String message)
	{
		if(isPresent(blockedMessageIcon))
		{
			waitThenClick(lastMessageWithBlockedIcon);
			waitForLoadShort(enterPasscodeInputField_legacy);
			sendKeys(getDefaultPasscode());
		}
		verifyMessageAvailability(message);
	}

	By enterPasscodeInputField_legacy		= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_dash_block");
	private By passcodeInput_legacy			= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_pin_edit");
}
