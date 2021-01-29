package Pages;

import STUtils.eDriverHandler;
import STUtils.eUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;

import java.util.List;

import static Pages.eCommonMethods.Permission.MICROPHONE;
import static Pages.eCommonMethods.Permission.СAMERA;
import static STUtils.eDriverHandler.Device;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.PhoneModel.HuaweiP;
import static STUtils.eDriverHandler.getPhoneModel;
import static STUtils.eSTProperties.getDevicePhoneNumber;
import static STUtils.eSTProperties.getProperty;
import static STUtils.eUtils.await;
import static STUtils.eUtils.reloadDevice;

public class eMainPage extends eCommonMethods
{
    private By profilePhoneNumber			= By.xpath("(//*[@resource-id='com.app.stealthtalk.testers:id/activity_app_bar_layout']/*/android.widget.TextView)[1]");
    private By authorizationStatus			= By.id("com.app.stealthtalk.testers:id/drawer_header_user_authorization_status");
    private By usualCallMenu                = By.xpath("//android.widget.TextView[@text='Secure Call']");
    private By securedCallButton            = By.xpath("//android.widget.TextView[@text='Stealth Call']");
    private By archiveButton				= By.xpath("//android.widget.TextView[@text='Archive']");
    private By contactInfo					= By.xpath("//android.widget.TextView[@text='Contact info']");
    private By passInput					= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_pin_edit");
    private By emptyList                    = By.id("com.app.stealthtalk.testers:id/list_empty_view_text");
    private By favoriteTab					= By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]");
    private By recentTab					= By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[2]");
    private By stealthtalkTab				= By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[3]");
    private By contactsTab					= By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[4]");
    private By sidePageContainer			= By.id("com.app.stealthtalk.testers:id/design_navigation_view");
    private By searchButton					= MobileBy.AccessibilityId("Search");
    private By searchInput					= By.id("com.app.stealthtalk.testers:id/search_src_text");
    private By searchItems					= By.id("com.app.stealthtalk.testers:id/ab_record_name");
    private By searchItemsRecentTab			= By.id("com.app.stealthtalk.testers:id/list_recent_item_name");
    private By backButton					= MobileBy.AccessibilityId("Collapse");
    private By backButtonFromChat			= MobileBy.AccessibilityId("Navigate up");
    private By contextMenuTitle				= By.id("com.app.stealthtalk.testers:id/bottom_sheet_title");
    private By acceptButton					= By.id("com.app.stealthtalk.testers:id/fragment_multi_choice_contacts_send");
    private By sendMessage					= By.id("com.app.stealthtalk.testers:id/menu_share");
    private By invite						= By.id("com.app.stealthtalk.testers:id/ab_property_logo");
    private By messageText					= By.xpath("//*[contains(@text, 'To connect, make sure to have my number in your Contacts.')]");
    private By updateLater					= By.id("com.wssyncmldm:id/bt_later");
	private By closeStealthTalkApp			= MobileBy.xpath("//*[@content-desc='Eve Talk']/following-sibling::*[@resource-id='com.android.systemui:id/dismiss_task']");
    private By messageNotification			= By.xpath("//android.widget.TextView[@text='New message']");
    private By secureMessageNotification	= By.xpath("//*[@resource-id='android:id/text' and @text='Stealth message']");
	private By avatar						= By.id("com.app.stealthtalk.testers:id/drawer_header_avatar");
	private By takeNewPhotoButton			= By.xpath("//android.widget.TextView[@text='Take new photo']");
	private By selectPhotoFromGalleryButton	= By.xpath("//android.widget.TextView[@text='Choose from Gallery']");
	private By removeAvatar					= By.xpath("//android.widget.TextView[@text='Delete']");
	private By cancelButton					= By.xpath("//android.widget.Button[@text='CANCEL']");
	private By avatarPreview				= By.id("com.app.stealthtalk.testers:id/fragment_avatar_preview_image_view");
	private By doneButton					= By.xpath("//android.widget.TextView[@text='DONE']");
	private By imageThumbnail				= By.id("com.android.documentsui:id/thumbnail");
	private By photosPermissionRequest		= By.xpath("//*[@text='Allow StealthTalk to access photos, media, and files on your device?']");
	private By sharingThroughMessages		= By.xpath("//android.widget.LinearLayout/android.widget.TextView[@text='Messages']");
	private By inviteToStealthTalkTitle		= By.xpath("//*[@text='Invite to StealthTalk']");
	private By qrButton						= By.id("com.app.stealthtalk.testers:id/activity_main_fab");
	private By accountSettings				= By.xpath("//android.widget.CheckedTextView[@text='Account']");
	private By inviteButtonInContactInfo 	= By.id("com.app.stealthtalk.testers:id/activity_collapsed_toolbar_fab");
	private By closeStealthTalkAppHuawei	= MobileBy.AccessibilityId("Close all recent apps");
	private By contactInContactsList		= By.id("com.app.stealthtalk.testers:id/list_contacts_item");
    private By addContactByQrButton		    = By.id("com.app.stealthtalk.testers:id/activity_main_fab");
    private By qrScanner                    = By.id("com.app.stealthtalk.testers:id/qr_code_scan_camera_preview");
    private By qrCode                       = By.id("com.app.stealthtalk.testers:id/qr_code_image_preview");

	private eSettingsPage	settingsPage;
	private By favoriteContactName;

	static String selectedAttributeLabel                = "selected";
	static String stealthModeEnabledLabel               = "Stealth mode enabled";
	static String stealthModeDisabledLabel              = "Stealth mode disabled";
	static String inviteViaSmsTextMessage               = "Please install StealthTalk to have secure chats and team calls\n" +
    "\n" + "It's Cyber Defense Technology:\n" + "https://StealthTalk.com\n" + "\n" + "To connect, make sure to have my number in your Contacts.";

    private String contactForAction_xPath               = "//*[@text='%s']";
    private String messageFromNotification_xPath        = "//android.widget.TextView[@text='New message' or @text='%s']";
    private String inviteButton_xPath                   = "//*[@text='%s']/following-sibling::*[@text='INVITE']";


	public eMainPage(Device device)
	{
		super(eDriverHandler.getDriver(device));
		clickIfPossible(updateLater);
		settingsPage = new eSettingsPage(driver);
	}

    public eMainPage(AndroidDriver driver)
    {
        super(driver);
        clickIfPossible(updateLater);
        settingsPage = new eSettingsPage(driver);
    }

    public void openFavoriteTab()
    {
        waitThenClick(favoriteTab);
    }

    @Deprecated
    public AndroidDriver getDriver()
    {
        return driver;
    }

    @Deprecated
    public eCallPage getCallPage()
    {
        return new eCallPage(driver);
    }

    public eChatPage openChat(String name)
    {

        waitThenClick(stealthtalkTab);
        verticalScrollUp();
        return openChatFromTab(name);
    }

    public void openChat_getBack(String name)
    {
        verticalScrollUp();
        eChatPage eChatPage = openChatFromTab(name);
        eChatPage.getBack();
    }

    public void verifyDropVerificationOnLock()
    {
        openSidePage();
        Assert.assertEquals(waitThenGetText(authorizationStatus), stealthModeEnabledLabel);
        reloadDevice(driver);
        Assert.assertEquals(waitThenGetText(authorizationStatus), stealthModeDisabledLabel);
        click(eSidePanelPage.authorizeButton);
        inputPasscode();
		eSidePanelPage eSidePanelPage = openSidePage();
		Assert.assertEquals(waitThenGetText(authorizationStatus), stealthModeEnabledLabel);
		eSidePanelPage.openAccountSettings();
    }

    public eContactInfoPage openContactInfo(String name)
    {
        waitThenClick(stealthtalkTab);
        verticalScrollUp();

        openContextMenu(name);

        click(contactInfo);

        return new eContactInfoPage(driver);
    }

    public eSidePanelPage openSidePage()
    {
        waitForLoad(profilePhoneNumber);
        int counter = 0;
        while (isAbsent(authorizationStatus) && counter++ != 5)
        {
            scrollForSideBar();
            await(1000);
        }
        return new eSidePanelPage(driver);
    }

    public boolean isAuthorized()
    {
        openSidePage();

        boolean isAuthorized = waitThenGetText(authorizationStatus).equals(stealthModeEnabledLabel);
        leftHorizontalScroll();
        return isAuthorized;
    }

    public eCallPage executeUsualCall_FromContextMenu(String name)
    {
        waitThenClick(stealthtalkTab);
        verticalScrollUp();

        return executeUsualCall_fromOpenTab(name);
    }

    public eCallPage executeSecuredCallFromContextMenu(String name)
    {
        waitThenClick(stealthtalkTab);
        verticalScrollUp();

        return executeSecuredCallFromTab(name);
    }

    public void openContextMenu(String name)
    {
        By contact = By.xpath(String.format(contactForAction_xPath, name));
        waitForLoad(stealthtalkTab);
        scrollDownUntilElementVisible(contact);
        longClick(contact);
        verifyContextMenuIsOpened(name);

    }

    public void verifyFavoriteListIsEmpty ()
    {
        Assert.assertTrue(isPresent(emptyList));
    }

    public void verifyFavoriteContactAdded(String contactName)
    {
        openFavoriteTab();
        By favoriteContactName = By.xpath(String.format(contactForAction_xPath, contactName));
        Assert.assertTrue(isPresent(favoriteContactName));
    }

    private void scrollForSideBar()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(1, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width-100, windowSize.height/2);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        await(1000);
    }


    private void leftHorizontalScroll()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(windowSize.width-100, windowSize.height/2);
        PointOption finishPoint = PointOption.point(100, windowSize.height/2);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        await(1000);
    }

    public void rightHorizontalScroll()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(100, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width-100, windowSize.height/2);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        await(1000);
    }

    public String favoriteTabIsSelected()
    {
        return getAttribute(favoriteTab, selectedAttributeLabel);
    }

    public String recentTabIsSelected()
    {
        return getAttribute(recentTab, selectedAttributeLabel);
    }

    public String stealthtalkTabIsSelected()
    {
        return getAttribute(stealthtalkTab, selectedAttributeLabel);
    }

    public String contactsTabIsSelected()
    {
        return getAttribute(contactsTab, selectedAttributeLabel);
    }

    public void openContactsTab()
    {
        waitThenClick(contactsTab);
        await(1000);
    }

    public void openSidePageBySwipe()
    {
        waitForLoad(stealthtalkTab);

        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(1, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width-100, windowSize.height/2);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
    }

    public void verifySidePageIsOpened()
    {
        verifyAvailability(sidePageContainer);
    }

    public eContactInfoPage openContactInfoFromContextMenu(String name)
    {
        openContextMenu(name);
        click(contactInfo);
        return new eContactInfoPage(driver);
    }

    public void verticalScrollUp()
    {
        await(1000);

        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint = PointOption.point(windowSize.width/2, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width/2, windowSize.height - 1);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();

        await(1000);
    }

    public void openStealthtalkTab()
    {
        waitThenClick(stealthtalkTab);
    }

    public eChatPage openChatFromTab(String contactName)
    {
        By contactToOpen = By.xpath(String.format(contactForAction_xPath, contactName));
        scrollDownUntilElementVisible(contactToOpen);
        click(contactToOpen);
        eChatPage chatPage = new eChatPage(driver);
        Assert.assertEquals(chatPage.getChatTitle(), contactName);
        return chatPage;
    }

    public eCallPage executeUsualCall_fromOpenTab(String name)
    {
        By contactToCall = By.xpath(String.format(contactForAction_xPath, name));
        scrollDownUntilElementVisible(contactToCall);
        openContextMenu(name);

        click(usualCallMenu);
        allowPermission(MICROPHONE);

        return new eCallPage(driver);
    }

    public void executeUsualCall_fromTab_getBack(String name)
    {
        eCallPage eCallPage = executeUsualCall_fromOpenTab(name);
        Assert.assertEquals(name, eCallPage.getProfileNameInOpenCallView());
        eCallPage.cancelCall();
    }

    public void verifyAuthorizationDropped(String name)
    {
    	//TODO REFACTORING Replace following code with check if passcode request appears.
        click(stealthtalkTab);
        eCallPage callPage = executeSecuredCallFromTab(name);
        callPage.verifySecureCall();
        callPage.cancelCall();
    }

    public eCallPage executeSecuredCallFromTab(String name)
    {
        openContextMenu(name);
        click(securedCallButton);
        waitForAbsenceShort(securedCallButton);
        checkMicrophonePermissionRequest();
        inputPasscode();
        allowPermission(MICROPHONE);
        return new eCallPage(driver);
    }

    public void executeSecuredCall_fromTab_getBack(String name)
    {
        eCallPage eCallPage = executeSecuredCallFromTab(name);
        Assert.assertEquals(name, eCallPage.getProfileNameInOpenCallView());
        eCallPage.cancelCall();
    }

    public void archiveChat(String name)
    {
        openContextMenu(name);
        click(archiveButton);
        By contactToArchive = By.xpath(String.format(contactForAction_xPath, name));
        verifyQuantity(contactToArchive,0);
    }

    public void verifyRecentAdded(String name)
    {
        By recentContact = By.xpath(String.format(contactForAction_xPath, name));
        verifyAvailability(recentContact);
    }

    public void search(String text)
    {
        click(searchButton);
        input(searchInput, text);
    }

    public void search_getBack(String text)
    {
        searchNonExistentUser();
        search(text);
        verifySearch(text);
        getBack();
    }

    public void searchRecent_getBack(String text)
    {
        search(text);
        verifySearchRecent(text);
        getBack();
    }

    public void verifySearch(String text)
    {
        waitForLoadShort(searchItems);
        List<AndroidElement> searchResult = returnList(searchItems);
        Assert.assertEquals(searchResult.get(0).getText(), text);
    }

    public void verifySearchRecent(String text)
    {
        waitForLoadShort(searchItemsRecentTab);
        List<AndroidElement> searchResult = returnList(searchItemsRecentTab);
        Assert.assertEquals(searchResult.get(0).getText(), text);
    }

    public void getBack()
    {
        click(backButton);
    }

    public void getBackFromChat ()
    {
        click(backButtonFromChat);
    }

    public void openRecentTab()
    {
        waitThenClick(recentTab);
    }

    public void verifyContextMenuIsOpened(String name)
    {
        String actualName = waitThenGetText(contextMenuTitle);
        Assert.assertEquals(actualName,name);
    }

    public void closeContextMenu()
    {
        Dimension windowSize = driver.manage().window().getSize();
        new TouchAction<>(driver).press(PointOption.point(windowSize.width/2,windowSize.height/2)).release().perform();
    }

    public void share(String name)
    {
        By contactToShare = By.xpath(String.format(contactForAction_xPath, name));
        waitForLoad(contactInContactsList);
        scrollDownUntilElementVisible(contactToShare);
        click(contactToShare);
        click(acceptButton);
        await(3000);
        checkStoragePermission();
        checkPhotosPermission();
        await(15000);
        click(sendMessage);
    }

	public void verifyContactInContactTab (String contactName)
	{
        By contact = By.xpath(String.format(contactForAction_xPath, contactName));
		waitForLoadShort(contactsTab);
		click(contactsTab);
		waitForLoadShort(contact);
	}

	public void verifyNoContactInContactTab (String contactName)
	{
        By contact = By.xpath(String.format(contactForAction_xPath, contactName));
		waitForLoadShort(contactsTab);
		click(contactsTab);
		await(5000);
		Assert.assertTrue(isAbsent(contact));
	}

    public void invite(String name)
    {
        search(name);
        By inviteButton = By.xpath(String.format(inviteButton_xPath, name));
    	click(inviteButton);
    	waitForLoadShort(inviteToStealthTalkTitle);
    	pressAndroidBackButton();

    	click(backButton);
        By contactToInvite = By.xpath(String.format(contactForAction_xPath, name));
        scrollDownUntilElementVisible(contactToInvite);
        waitThenClick(contactToInvite);
        click(inviteButtonInContactInfo);

        verifyInvite();
    }

    public void verifyInvite()
    {
    	verticalScrollDown();
    	click(sharingThroughMessages);
    	click(MobileBy.AccessibilityId(getProperty("contactToInvite")));
        Assert.assertEquals(waitThenGetText(messageText), inviteViaSmsTextMessage);
    }

    public void closeStealthTalkApp(Boolean lock)
	{
        eUtils.invokeAndroidEvent(driver, AndroidKey.APP_SWITCH);
		if (getPhoneModel(driver).equals(HuaweiP)) click(closeStealthTalkAppHuawei);
		else waitThenClick(closeStealthTalkApp);
        eUtils.invokeAndroidEvent(driver, AndroidKey.HOME);
        if (lock) driver.lockDevice();
    }

    public eChatPage openMessageFromNotification(String message)
    {
        By messageFromNotification = By.xpath(String.format(messageFromNotification_xPath, message));
        driver.openNotifications();
        waitThenClick(messageFromNotification);
        return new eChatPage(driver);
    }

    public eChatPage openSecureMessageFromNotification()
    {
        driver.openNotifications();
        waitThenClick(secureMessageNotification);
        return new eChatPage(driver);
    }

	public eMainPage verifyRegistration(String phoneNumber)
	{
		Assert.assertTrue(getText(profilePhoneNumber).contains(phoneNumber));
		return this;
	}

	public void openSettings()
	{
		openSidePage().openSettings();
	}

	public void openAccountSettings()
	{
		openSidePage().openAccountSettings();
	}

	public void verifyAbsenceQrButton ()
	{
		openContactsTab();
		verifyAbsence(qrButton);
	}

	public void searchNonExistentUser()
    {
        search(RandomStringUtils.randomAlphanumeric(5));
        verifyAvailability(emptyList);
        getBack();
    }

    public void verifyQrView()
    {
        click(addContactByQrButton);
        allowPermission(СAMERA);
        waitForLoadShort(qrScanner);
        waitForLoadLong(qrCode);
        click(backButtonFromChat);
        verifyRegistration(getDevicePhoneNumber(FIRST_DEVICE));
    }

    public void verifyClearChatHistory()
    {
        click(recentTab);
        verifyAvailability(emptyList);
    }
}