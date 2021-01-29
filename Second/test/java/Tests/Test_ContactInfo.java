package Tests;

import Pages.eCallPage;
import Pages.eChatPage;
import Pages.eContactInfoPage;
import Pages.eMainPage;
import STUtils.eTestListener;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_ContactInfo
{
    private eMainPage			FIRST_DEVICE_eMainPage;
    private eContactInfoPage	FIRST_DEVICE_eContactInfoPage;
    private eChatPage			FIRST_DEVICE_eChatPage;
    private eCallPage			FIRST_DEVICE_eCallPage;

	private eMainPage			SECOND_DEVICE_eMainPage;
	private eCallPage			SECOND_DEVICE_eCallPage;

	private String secondDeviceContactName = getSecondDeviceProperty("AccountName");

    private String  secondDeviceChat = getConfigProperty(getProperty("secondDevice")+"AccountName");

    @BeforeSuite
    public void beforeSuite(ITestContext testContext)
    {
        if (toSetRetry())
        {
            for (ITestNGMethod method : testContext.getAllTestMethods())
            {
                method.setRetryAnalyzer(new eTestListener());
            }
        }
    }

    private void setUp()
    {
		AndroidDriver driver = getDriver(FIRST_DEVICE);

		FIRST_DEVICE_eMainPage			= new eMainPage(driver);
		FIRST_DEVICE_eContactInfoPage	= new eContactInfoPage(driver);
		FIRST_DEVICE_eChatPage			= new eChatPage(driver);
		FIRST_DEVICE_eCallPage			= new eCallPage(driver);
    }

	private void setUpTwoDevices()
	{
		setUp();

		SECOND_DEVICE_eMainPage = new eMainPage(getDriver(SECOND_DEVICE));
		SECOND_DEVICE_eCallPage = new eCallPage(getDriver(SECOND_DEVICE));
	}

    @Test
	public void Navigation()
	{
		setUp();
		FIRST_DEVICE_eMainPage.openContactsTab();
		FIRST_DEVICE_eMainPage.openContactInfoFromContextMenu(secondDeviceContactName);
		FIRST_DEVICE_eContactInfoPage.addToFavorite();

		FIRST_DEVICE_eMainPage.openFavoriteTab();
		FIRST_DEVICE_eMainPage.openContactInfoFromContextMenu(secondDeviceContactName);
		FIRST_DEVICE_eContactInfoPage.removeFromFavorites();
		FIRST_DEVICE_eContactInfoPage.getBack();

		FIRST_DEVICE_eMainPage.openStealthtalkTab();
		FIRST_DEVICE_eMainPage.openContactInfoFromContextMenu(secondDeviceContactName);
		FIRST_DEVICE_eContactInfoPage.verifyContactInfoIsOpened(secondDeviceContactName);

//		TODO Not implemented in StealthTalk yet
//		FIRST_DEVICE_eMainPage.openRecentTab();
//		FIRST_DEVICE_eMainPage.openContactInfoFromContextMenu(secondDeviceContactName);
//		FIRST_DEVICE_eContactInfoPage.verifyContactInfoIsOpened(secondDeviceContactName);

		FIRST_DEVICE_eMainPage.openChat(secondDeviceContactName);
		FIRST_DEVICE_eChatPage.openContactInfo();
		FIRST_DEVICE_eContactInfoPage.verifyContactInfoIsOpened(secondDeviceContactName);
		tearDown();
	}

	@Test
	public void AddAndRemoveFromFavorites()
	{
		setUp();
		FIRST_DEVICE_eMainPage.openContactInfo(secondDeviceChat);
		FIRST_DEVICE_eContactInfoPage.addToFavorite();

		FIRST_DEVICE_eMainPage.openFavoriteTab();
		FIRST_DEVICE_eMainPage.openContactInfoFromContextMenu(secondDeviceChat);
		FIRST_DEVICE_eContactInfoPage.removeFromFavorites();

		tearDown();
	}

	@Test
	public void UsualCall()
	{
		setUpTwoDevices();

		FIRST_DEVICE_eContactInfoPage	= FIRST_DEVICE_eMainPage.openContactInfo(secondDeviceContactName);
		FIRST_DEVICE_eCallPage			= FIRST_DEVICE_eContactInfoPage.executeSecuredCall();

		Assert.assertEquals(secondDeviceContactName, FIRST_DEVICE_eCallPage.getProfileNameInOpenCallView());

		String callStatus1 = SECOND_DEVICE_eCallPage.getCallStatus();
		String callStatus2 = FIRST_DEVICE_eCallPage.getCallStatus();

		Assert.assertEquals(callStatus1,"Is calling...");
		Assert.assertEquals(callStatus2,"Calling...");

		SECOND_DEVICE_eCallPage.answerSecureCall();

		Assert.assertNotEquals(callStatus1, FIRST_DEVICE_eCallPage.getCallStatus());
		Assert.assertNotEquals(callStatus2, SECOND_DEVICE_eCallPage.getCallStatus());

		FIRST_DEVICE_eCallPage.verifySound();
		SECOND_DEVICE_eCallPage.verifySound();

		tearDown();
	}

	@Test
	public void SecureCall()
	{
		setUpTwoDevices();

		FIRST_DEVICE_eContactInfoPage	=  FIRST_DEVICE_eMainPage.openContactInfo(secondDeviceContactName);
		FIRST_DEVICE_eCallPage			= FIRST_DEVICE_eContactInfoPage.executeStealthCall();

		Assert.assertEquals(secondDeviceContactName, FIRST_DEVICE_eCallPage.getProfileNameInOpenCallView());

		String callStatus1 = SECOND_DEVICE_eCallPage.getCallStatus();
		String callStatus2 = FIRST_DEVICE_eCallPage.getCallStatus();
		Assert.assertEquals(callStatus1,"Is calling...");
		Assert.assertEquals(callStatus2,"Calling...");

		FIRST_DEVICE_eCallPage.verifySecureCall();
		SECOND_DEVICE_eCallPage.verifySecureCall();

		SECOND_DEVICE_eCallPage.answerStealthCall();

		Assert.assertNotEquals(callStatus1, FIRST_DEVICE_eCallPage.getCallStatus());
		Assert.assertNotEquals(callStatus2, SECOND_DEVICE_eCallPage.getCallStatus());

		FIRST_DEVICE_eCallPage.verifySound();
		SECOND_DEVICE_eCallPage.verifySound();

		FIRST_DEVICE_eCallPage.verifySecureCall();
		SECOND_DEVICE_eCallPage.verifySecureCall();

		tearDown();
	}
	private void tearDown()
    {
        turnOffDrivers();
    }
}
