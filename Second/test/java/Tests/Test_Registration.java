package Tests;

import Pages.eAccountPage;
import Pages.eMainPage;
import Pages.eRegistrationPage;
import Pages.eSettingsPage;
import STUtils.eTestListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static Pages.eCommonMethods.Permission.CONTACTS;
import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.*;

@Listeners(eTestListener.class)
public class Test_Registration
{
    private eMainPage			eMainPage;
    private eRegistrationPage	eRegistrationPage;
    private eSettingsPage		eSettingsPage;
    private eAccountPage		eAccountPage;

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

    private void setUp(Device device)
    {
        reInstallApp(device);
        eMainPage				= new eMainPage(device);
        eRegistrationPage		= new eRegistrationPage(device);
		eSettingsPage			= new eSettingsPage(device);
		eAccountPage			= new eAccountPage(device);
    }

//    @Test
//    public void Registration_NewUser()
//    {
//        String randomPhoneNumber = generateRandomPhoneNumber("73140");
//
//        reInstallApp(getDriver(FIRST_DEVICE));
//        eMainPage			= new eMainPage(getDriver(FIRST_DEVICE));
//        eRegistrationPage	= new eRegistrationPage(getDriver(FIRST_DEVICE));
//
//        eRegistrationPage.pressSkipButton();
//        eRegistrationPage.selectCountryCode(getDeviceCountry(FIRST_DEVICE));
//        eRegistrationPage.inputPhoneNumber(randomPhoneNumber);
//        eRegistrationPage.inputVerificationCode();
//        eRegistrationPage.setStandardPassCode();
//        //eRegistrationPage.setStandardRestoreInfo();
//
//        relaunchApp(FIRST_DEVICE);
//		eMainPage.verifyRegistration(randomPhoneNumber);
//        turnOffDrivers();
//    }

    /*
     *  This method is for automated runs only!
     *  The time to completely delete StealthTalk account is 24 hours, so the test uses two phone numbers depending on
     *  day of the week. If run manually it will most likely fail since phone number was used in the night run and
     *  account isn't deleted yet.
     *  This method also tests setting up Restore Info first time through settings
     */
	@Test
	public void RegisterAndDeleteNewUser()
	{
		setUp(FIRST_DEVICE);
		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode(getDeviceCountry(FIRST_DEVICE));
		eRegistrationPage.inputPhoneNumber(getGeneratedPhoneNumber());
		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.setStandardPassCode();
        eRegistrationPage.skipCongratulationsViewIfPossible();
        eRegistrationPage.setRecoveryInfo();
        eRegistrationPage.skipCongratulationsViewIfPossible();
        eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.openAccountSettings();
		eAccountPage.deleteAccount();
		eRegistrationPage.pressSkipButton();
		turnOffDrivers();
	}

	@Test
    public void Registration_NegativeCase_SecretQuestion()
    {
        setUp(FIRST_DEVICE);
        eRegistrationPage.pressSkipButton();
        eRegistrationPage.verifyInvalidPhoneInput();
        eRegistrationPage.verifyIncorrectVerificationCode();
        eRegistrationPage.verifyIncorrectApproveCode();
		eRegistrationPage.setSecretQuestionSelectorMenu();
        eRegistrationPage.allowPermission(CONTACTS);

		eMainPage.verifyRegistration(getProperty("phoneNumberForSecretQuestionTesting"));

		eAccountPage.setRecoveryQuestion_PreDefined_Verify();
		eAccountPage.changeRecoveryEmail_Verify();
		setUp(FIRST_DEVICE);
		eRegistrationPage.registrationWith_PreDefinedQuestion_Email();
        eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getProperty("phoneNumberForSecretQuestionTesting"));
		tearDown_RecoveryQuestion();


    }

    @Test
    public void Registration_NegativeCase_Passcode()
    {
        setUp(FIRST_DEVICE);
        eRegistrationPage.pressSkipButton();
        eRegistrationPage.selectCountryCode(getDeviceCountry(FIRST_DEVICE));
        eRegistrationPage.inputPhoneNumber(getProperty("phoneNumberForPasscodeTesting"));
        eRegistrationPage.inputVerificationCode();
        eRegistrationPage.verifyIncorrectPasscode();
		restartStealthTalk(FIRST_DEVICE);
		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getProperty("phoneNumberForPasscodeTesting"));
        tearDown();
    }

    @Test
	public void Navigation()
	{
		setUp(FIRST_DEVICE);
		String randomPhoneNumber = generateRandomPhoneNumber("709");

		eRegistrationPage.pressSkipButton();
		eRegistrationPage.selectCountryCode("Canada");
		eRegistrationPage.inputPhoneNumber(randomPhoneNumber);
		eRegistrationPage.inputVerificationCode();
		eRegistrationPage.verifyNavigationOnSetPasscodeView();
        eRegistrationPage.verifyNavigationOnSetRestoreInfoView();
        eRegistrationPage.skipCongratulationsViewIfPossible();
        eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(randomPhoneNumber);

		setUp(FIRST_DEVICE);
		String phoneNumber			= getDevicePhoneNumber(FIRST_DEVICE);
		String formattedPhoneNumber	= getDeviceFormattedPhoneNumber(FIRST_DEVICE);
		String country				= getDeviceCountry(FIRST_DEVICE);

		eRegistrationPage.verifyNavigationOnPhoneNumberView(phoneNumber, country);
		eRegistrationPage.verifyNavigationOnVerificationCodeView(formattedPhoneNumber);
//		eRegistrationPage.verifyNavigationOnRestoreInfoView();
        eRegistrationPage.inputDefaultPasscode();

		eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(phoneNumber);
		tearDown();
	}

    @DataProvider(name = "Devices")
    public static Object[] devices()
    {
        return new Device[]
		{
        	FIRST_DEVICE,
			SECOND_DEVICE,
			//THIRD_DEVICE
		};
    }

    @Test(dataProvider = "Devices")
    public void ReRegistration(Device device)																			// BaseTest
    {
        setUp(device);
        eRegistrationPage.pressSkipButton();
        eRegistrationPage.selectCountryCode(getDeviceCountry(device));
        eRegistrationPage.inputPhoneNumber(getDevicePhoneNumber(device));
        eRegistrationPage.inputVerificationCode();
        eRegistrationPage.inputDefaultPasscode();
        eRegistrationPage.allowPermission(CONTACTS);
		eMainPage.verifyRegistration(getDevicePhoneNumber(device));
        restartStealthTalk(device);
        eMainPage.verifyRegistration(getDevicePhoneNumber(device));
		tearDown();
    }

    private void tearDown_RecoveryQuestion()
	{
		eAccountPage.setRecoveryQuestion_Custom();
		eAccountPage.setDefaultEmail();
		tearDown();
	}

    private void tearDown()
    {
        turnOffDrivers();
    }
}