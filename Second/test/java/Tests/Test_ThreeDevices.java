package Tests;


import Pages.eCallPage;
import Pages.eChatPage;
import Pages.eMainPage;
import Pages.eRegistrationPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static Pages.eCommonMethods.Permission.CONTACTS;
import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.*;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.reInstallApp;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_ThreeDevices {

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

	private String firstDeviceChat 		= getConfigProperty(getProperty("firstDevice")+"AccountName");
	private String secondDeviceChat		= getConfigProperty(getProperty("secondDevice")+"AccountName");
	private String thirdDeviceChat 		= getConfigProperty(getProperty("thirdDevice")+"AccountName");

	private eMainPage					eMainPage;
	private eRegistrationPage 			eRegistrationPage;

	private eChatPage 					FIRST_DEVICE_eChatPage;
	private eCallPage 					FIRST_DEVICE_eCallPage;
	private eMainPage 					FIRST_DEVICE_eMainPage;

	private eMainPage 					SECOND_DEVICE_eMainPage;
	private eCallPage 					SECOND_DEVICE_eCallPage;

	private eMainPage 					THIRD_DEVICE_eMainPage;
	private eCallPage 					THIRD_DEVICE_eCallPage;

	private eChatPage 					SECOND_DEVICE_eChatPage;
	private eChatPage 					THIRD_DEVICE_eChatPage;


	private void setUp(Device device)
	{
		reInstallApp(device);
		eMainPage				= new eMainPage(device);
		eRegistrationPage		= new eRegistrationPage(device);
	}


	private void setUpThreeDevicesCall()
	{
		FIRST_DEVICE_eMainPage = new eMainPage(FIRST_DEVICE);
		FIRST_DEVICE_eCallPage = new eCallPage(FIRST_DEVICE);

		SECOND_DEVICE_eMainPage = new eMainPage(SECOND_DEVICE);
		SECOND_DEVICE_eCallPage = new eCallPage(SECOND_DEVICE);

		THIRD_DEVICE_eMainPage = new eMainPage(eDriverHandler.getDriver(THIRD_DEVICE));
	}

	private void setUpThreeDevicesMessage()
	{
		FIRST_DEVICE_eMainPage	= new eMainPage(getDriver(FIRST_DEVICE));
		FIRST_DEVICE_eCallPage	= new eCallPage(getDriver(FIRST_DEVICE));
		FIRST_DEVICE_eChatPage	= FIRST_DEVICE_eMainPage.openChat(secondDeviceChat);

		SECOND_DEVICE_eMainPage	= new eMainPage(getDriver(SECOND_DEVICE));
		SECOND_DEVICE_eCallPage	= new eCallPage(getDriver(SECOND_DEVICE));
		SECOND_DEVICE_eChatPage	= SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);

		THIRD_DEVICE_eMainPage = new eMainPage(getDriver(THIRD_DEVICE));
		THIRD_DEVICE_eCallPage = new eCallPage(getDriver(THIRD_DEVICE));
		THIRD_DEVICE_eChatPage = THIRD_DEVICE_eMainPage.openChat(firstDeviceChat);
	}


	@Test
	public void ReRegistration()
	{
		setUp(THIRD_DEVICE);
		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode(getDeviceCountry(THIRD_DEVICE));
		eRegistrationPage.inputPhoneNumber(getDevicePhoneNumber(THIRD_DEVICE));
		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.inputPasscode();
//		eRegistrationPage.inputRestoreInfo();
		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getDevicePhoneNumber(THIRD_DEVICE));
		restartStealthTalk(THIRD_DEVICE);
		eMainPage.verifyRegistration(getDevicePhoneNumber(THIRD_DEVICE));
		tearDown();
	}

	@Test
	public void attachMemberToCall()
	{
		setUpThreeDevicesCall();
		THIRD_DEVICE_eMainPage = new eMainPage(getDriver(THIRD_DEVICE));
		THIRD_DEVICE_eCallPage = THIRD_DEVICE_eMainPage.getCallPage();

		FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
		FIRST_DEVICE_eChatPage.executeUsualCall();
		SECOND_DEVICE_eCallPage.answerSecureCall();

		FIRST_DEVICE_eCallPage.attachMember();

		THIRD_DEVICE_eCallPage.answerSecureCall();

		FIRST_DEVICE_eCallPage.verifyMemberAttached();

		FIRST_DEVICE_eCallPage.verifySoundInGroup();
		SECOND_DEVICE_eCallPage.verifySoundInGroup();
		THIRD_DEVICE_eCallPage.verifySoundInGroup();

		FIRST_DEVICE_eCallPage.cancelCall();
		tearDown();
	}

	@Test
	public void offlineMessage()
	{
		String message = RandomStringUtils.randomAlphanumeric(5);
		String secureMessage = RandomStringUtils.randomAlphanumeric(5);

		setUpThreeDevicesMessage();

		FIRST_DEVICE_eMainPage.closeStealthTalkApp(false);
		SECOND_DEVICE_eChatPage.sendMessage(message, false);
		FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openMessageFromNotification(message);
		Assert.assertEquals(FIRST_DEVICE_eChatPage.getLastReceivedMessage(), message);
		Assert.assertEquals(FIRST_DEVICE_eChatPage.getChatTitle(), secondDeviceChat);

		FIRST_DEVICE_eMainPage.closeStealthTalkApp(false);
		THIRD_DEVICE_eChatPage.sendMessage(secureMessage,true);
		FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openSecureMessageFromNotification();
		Assert.assertEquals(secureMessage, FIRST_DEVICE_eChatPage.verifySecureMessage());
		Assert.assertEquals(FIRST_DEVICE_eChatPage.getChatTitle(), thirdDeviceChat);
		tearDown();
	}

	private void tearDown()
	{
		turnOffDrivers();
	}




}
