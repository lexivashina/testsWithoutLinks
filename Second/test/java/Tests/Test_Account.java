package Tests;

import Pages.eAccountPage;
import Pages.eChatPage;
import Pages.eMainPage;
import STUtils.eTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eSTProperties.getConfigProperty;
import static STUtils.eSTProperties.getProperty;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_Account
{
	private eMainPage eMainPage;
	private eAccountPage eAccountPage;
	private eChatPage eChatPage;

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
	private String  secondDevice = getConfigProperty(getProperty("secondDevice")+"AccountName");

	private void setUp()
	{
		eMainPage = new eMainPage(getDriver(FIRST_DEVICE));
		eAccountPage = new eAccountPage(getDriver(FIRST_DEVICE));
		eChatPage = new eChatPage(getDriver(FIRST_DEVICE));
	}

	private void setUpClearChat()
	{
		setUp();
		eMainPage.openStealthtalkTab();
		eMainPage.openChat(secondDevice);
		eChatPage.sendMessage(RandomStringUtils.randomAlphanumeric(5));
		eChatPage.getBack();
		eMainPage.openAccountSettings();

	}

	@Test
	public void setAvatarCameraAndGallery()
	{
		setUp();

		eAccountPage.verifyAvatarFromCamera();
		eAccountPage.verifyAvatarFromGallery();

		tearDown();
	}

	@Test
	public void clearChatHistory()
	{
		setUpClearChat();

		eAccountPage.clearChatHistory();
		eMainPage.verifyClearChatHistory();

		tearDown();
	}

	private void tearDown()
	{
		turnOffDrivers();
	}
}
