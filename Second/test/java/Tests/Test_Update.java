package Tests;

import Pages.eChatPage;
import Pages.eMainPage;
import Pages.eRegistrationPage;
import STUtils.eTestListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Listeners(eTestListener.class)
public class Test_Update
{
	private eMainPage			FIRST_DEVICE_eMainPage;
	private eRegistrationPage	FIRST_DEVICE_eRegistrationPage;
	private eChatPage			FIRST_DEVICE_eChatPage;

	private eMainPage			SECOND_DEVICE_eMainPage;
	private eRegistrationPage	SECOND_DEVICE_eRegistrationPage;
	private eChatPage			SECOND_DEVICE_eChatPage;

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
		installStealthTalk(FIRST_DEVICE, getConfigProperty("oldStealthTalkInstallerLink"));
		FIRST_DEVICE_eMainPage			= new eMainPage(getDriver(FIRST_DEVICE));
		FIRST_DEVICE_eRegistrationPage	= new eRegistrationPage(getDriver(FIRST_DEVICE));
		FIRST_DEVICE_eChatPage			= new eChatPage(getDriver(FIRST_DEVICE));

		FIRST_DEVICE_eRegistrationPage.legacyRegistration("Kiribati", getDevicePhoneNumber(FIRST_DEVICE));

		installStealthTalk(SECOND_DEVICE, getConfigProperty("oldStealthTalkInstallerLink"));
		SECOND_DEVICE_eMainPage			= new eMainPage(getDriver(SECOND_DEVICE));
		SECOND_DEVICE_eRegistrationPage	= new eRegistrationPage(getDriver(SECOND_DEVICE));
		SECOND_DEVICE_eChatPage			= new eChatPage(getDriver(SECOND_DEVICE));

		SECOND_DEVICE_eRegistrationPage.legacyRegistration("Kiribati", getDevicePhoneNumber(SECOND_DEVICE));

		SECOND_DEVICE_eMainPage.openChat(getConfigProperty(getProperty("firstDevice")+"AccountName"));
		FIRST_DEVICE_eMainPage.openChat(getConfigProperty(getProperty("secondDevice")+"AccountName"));
	}

	@Test
	public void Update()
	{
		String[] randomMessages = {randomAlphanumeric(10), randomAlphanumeric(10), randomAlphanumeric(10), randomAlphanumeric(10)};

		setUp();

		FIRST_DEVICE_eChatPage.sendMessage(randomMessages[0]);
		SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessages[0], "Seen", "Default");
		FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessages[0], "Seen", "Default");

		FIRST_DEVICE_eChatPage.sendMessage_legacy(randomMessages[1], true);
		SECOND_DEVICE_eChatPage.openLastSecuredMessage_legacy();
		SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessages[1], "Seen", "Secure");
		FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessages[1], "Seen", "Secure");

		updateApp(getDriver(FIRST_DEVICE), getAppPath());
		FIRST_DEVICE_eMainPage.openChat(getConfigProperty(getProperty("secondDevice")+"AccountName"));

		FIRST_DEVICE_eChatPage.verifyMessageAvailability(randomMessages[0]);
		FIRST_DEVICE_eChatPage.verifySecureMessageAvailability(randomMessages[1]);

		FIRST_DEVICE_eChatPage.sendMessage(randomMessages[2]);
		SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessages[2], "Seen", "Default");
		FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessages[2], "Seen", "Default");

		FIRST_DEVICE_eChatPage.sendMessage(randomMessages[3], true);
		SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessages[3], "Seen", "Secure");
		FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessages[3], "Seen", "Secure");

		updateApp(getDriver(SECOND_DEVICE), getAppPath());
		SECOND_DEVICE_eMainPage.openChat(getConfigProperty(getProperty("firstDevice")+"AccountName"));

		SECOND_DEVICE_eChatPage.verifyMessageAvailability(randomMessages[2]);
		SECOND_DEVICE_eChatPage.verifySecureMessageAvailability(randomMessages[3]);

		turnOffDrivers();
	}
}
