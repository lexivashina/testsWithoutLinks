package Tests;

import Pages.*;
import STUtils.eSTProperties;
import STUtils.eTestListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import static Pages.eBillingPageWindows.setWebDriver;
import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.*;
import static STUtils.eSTProperties.*;
import static STUtils.eSTProperties.PromoCode.DISCOUNT;
import static STUtils.eSTProperties.PromoCode.PREPAID;
import static STUtils.eSTProperties.StealthTalkAppVersion.CUSTOMER;
import static STUtils.eSTProperties.StealthTalkAppVersion.ENTERPRISE;
import static STUtils.eSTProperties.EnterpriseBillingState.EXPIRED;
import static STUtils.eSTProperties.EnterpriseBillingState.ACTIVE;
import static STUtils.eUtils.*;

@Listeners({eTestListener.class})
public class Test_Billing
{
	private eBillingPageMobile	billingPageMobile;
	private eBillingPageWindows billingPageWindows;
	private eRegistrationPage 	registrationPage;
	private eMainPage 			mainPage;
	private eAccountPage 		accountPage;

	private static String phoneNumber;
	private static String username;

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

	private void setUpPages(Device device)
	{
		billingPageMobile 	= new eBillingPageMobile(getDriver(device));
		registrationPage 	= new eRegistrationPage(getDriver(device));
		mainPage 			= new eMainPage(getDriver(device));
		accountPage 		= new eAccountPage(getDriver(device));
	}

	private void setUp(Device device)
	{
		reInstallApp(device);

		setUpPages(device);

		phoneNumber = getProperty("phoneNumberForBillingTesting");

		registrationPage.reRegistrationForBilling(phoneNumber);
		mainPage.verifyRegistration(phoneNumber);
	}

	private void cleanSetup(Device device, eSTProperties.StealthTalkAppVersion appVersion, boolean executeRegistration)
	{
		switch (appVersion)
		{
			case ENTERPRISE:
				reInstallEnterpriseApp(device);
				break;
			case CUSTOMER:
				reInstallApp(device);
				break;
		}

		setUpPages(device);

		username = getGeneratedUsername();
		phoneNumber = getGeneratedPhoneNumber(eSTProperties.CountryCode.Kiribaty);

		if (executeRegistration)
		{
			registrationPage.registrationForBilling(phoneNumber);
			mainPage.verifyRegistration(phoneNumber);
		}
	}

	private void cleanSetup(Device device) { cleanSetup(device, CUSTOMER, true); }

	@Test
	public void verifyChangingSubscriptionStates_Free_Pro_Pro()
	{
		setUp(FIRST_DEVICE);

		accountPage.verifyFreeSubscriptionPlan();

		billingPageMobile.reNewSubscriptionByRenewNow(phoneNumber, PREPAID);

		accountPage.verifyProSubscriptionPlan();

		accountPage.openSubscriptionMenu();
		billingPageMobile.reNewSubscriptionByRenewNow(phoneNumber, DISCOUNT);

		accountPage.verifyProSubscriptionPlan();
		accountPage.verifySummingSubscriptions();
		accountPage.waitUntilSubscriptionExpires();
	}

	@DataProvider(name = "PromoCodes")
	public static Object[][] getPromoCode()
	{
		return new Object[][] { { DISCOUNT }, { PREPAID } };
	}

	@Test(dataProvider = "PromoCodes")
	public void rechargeBlockedAccountWithSignIn(eSTProperties.PromoCode code)
	{
		setUp(FIRST_DEVICE);

		accountPage.verifyFreeSubscriptionPlan();

		billingPageMobile.reNewSubscriptionBySignIn(phoneNumber, code);

		accountPage.verifyProSubscriptionPlan();
		accountPage.waitUntilSubscriptionExpires();
		accountPage.verifyFreeSubscriptionPlan();
	}

	@Test
	public void registerAndRechargeEnterpriseAccount()
	{
		cleanSetup(FIRST_DEVICE, ENTERPRISE, false);

		billingPageWindows 	= new eBillingPageWindows(setWebDriver(), getDriver(FIRST_DEVICE));

		billingPageWindows.openBillingEnterpriseWeb();
		billingPageWindows.loginToBillingEnterpriseWeb();

		billingPageWindows.addNewUserOnEnterpriseBillingSite_NegativeCase(username, getProperty("phoneNumberForBillingTesting"));
		billingPageWindows.addNewUserOnEnterpriseBillingSite_PositiveCase(username, phoneNumber);

		billingPageWindows.verifyUserOnUsersTab(username, phoneNumber, EXPIRED);

		registrationPage.registrationForBilling(phoneNumber);
		mainPage.verifyRegistration(phoneNumber);

		accountPage.verifyFreeEnterpriseSubscriptionPlan();

		billingPageWindows.rechargeEnterpriseAccount(phoneNumber);

		accountPage.verifyProEnterpriseSubscriptionPlan();
		accountPage.waitUntilSubscriptionExpires();
		accountPage.verifyFreeEnterpriseSubscriptionPlan();
	}

//*******************************************************************************************//
//
//	@Deprecated
//	@Test
//	public void rechargeBlockedAccountWeb()
//	{
//		cleanSetupWeb(FIRST_DEVICE);
//
//		billingPageWindows.signInBillingSite();
//		billingPageWindows.rechargeAccountPromo();
//		accountPage.verifyActive();
//		billingPageWindows.rechargeAccountPrepeid();
//		accountPage.verifyProlong();
//	}
//*******************************************************************************************//

	@AfterTest
	private void tearDown()
	{
		turnOffDrivers();
		billingPageWindows.turnOffWebDriver();
	}
}
