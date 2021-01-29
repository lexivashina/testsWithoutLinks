package Tests;

import Pages.eMainPage;
import Pages.eRegistrationPage;
import Pages.eSidePanelPage;
import STUtils.eTestListener;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static Pages.eCommonMethods.Permission.CONTACTS;
import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.reInstallApp;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_CounterBruteForce
{
	private eMainPage			eMainPage;
	private eRegistrationPage	eRegistrationPage;
    private eSidePanelPage eSidePanelPage;

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

	private void setUpNoReInstall(Device device)
	{
		eMainPage			= new eMainPage(getDriver(device));
		eRegistrationPage	= new eRegistrationPage(getDriver(device));
		eSidePanelPage      = eMainPage.openSidePage();
	}

	private void setUp(Device device)
	{
		reInstallApp(device);
		eMainPage			= new eMainPage(getDriver(device));
		eRegistrationPage	= new eRegistrationPage(getDriver(device));
	}

	@Test
	public void VerificationCodeBan()
	{
		setUp(SECOND_DEVICE);
		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode(getDeviceCountry(SECOND_DEVICE));
		eRegistrationPage.inputPhoneNumber(getDevicePhoneNumber(SECOND_DEVICE));

		eRegistrationPage.verifyBruteForceBan_VerificationCode(getDevicePhoneNumber(SECOND_DEVICE), 5);

		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.inputDefaultPasscode();
		//eRegistrationPage.inputRestoreInfo();

		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getDevicePhoneNumber(FIRST_DEVICE));
		tearDown();
	}

	@Deprecated /*Registration necessary with a secret question - re-registration through a passcode is impossible */
	@Test
	public void PasscodeBan()
	{
		setUp(FIRST_DEVICE);
		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode(getDeviceCountry(FIRST_DEVICE));
		eRegistrationPage.inputPhoneNumber(getProperty("phoneNumberForPasscodeTesting"));
		eRegistrationPage.inputVerificationCode();

		eRegistrationPage.verifyBruteForceBanOnPasscode(getProperty("phoneNumberForPasscodeTesting"), 5);

		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.inputDefaultPasscode();
		restartStealthTalk(FIRST_DEVICE);
		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getProperty("phoneNumberForPasscodeTesting"));
		tearDown();
	}

	@Test
	public void SecretQuestionBan()
	{
		setUp(FIRST_DEVICE);
		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode(getDeviceCountry(FIRST_DEVICE));
		eRegistrationPage.inputPhoneNumber(getDevicePhoneNumber(FIRST_DEVICE));
		eRegistrationPage.inputVerificationCode();

		eRegistrationPage.verifyBruteForceBanOnSecretQuestion(5);

		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.inputRestoreInfo();
		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getDevicePhoneNumber(FIRST_DEVICE));
		tearDown();
	}

	@Test
	public void EmailVerificationCodeBan()
	{
		setUp(FIRST_DEVICE);
		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode(getDeviceCountry(FIRST_DEVICE));
		eRegistrationPage.inputPhoneNumber(getDevicePhoneNumber(FIRST_DEVICE));
		eRegistrationPage.inputVerificationCode();

		eRegistrationPage.verifyBruteForceBanOnEmailVerificationCode(5);

		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.inputRestoreInfo();
		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getDevicePhoneNumber(FIRST_DEVICE));
		tearDown();
	}

	@Test
	public void AuthorizationBan()
	{
		setUpNoReInstall(FIRST_DEVICE);
		eSidePanelPage.invokeAuthorizationBan();
		eSidePanelPage.verifyAuthorizationBan(getDevicePhoneNumber(FIRST_DEVICE), 5);
		tearDown();
	}

	private void tearDown()
	{
		turnOffDrivers();
	}
}
