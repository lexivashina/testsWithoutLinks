package Tests;

import Pages.eAccountPage;
import Pages.eMainPage;
import Pages.eSidePanelPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.toSetRetry;
import static STUtils.eUtils.waitTime;

@Listeners({eTestListener.class})
public class Test_Authorization
{
    private eAccountPage	eAccountPage;
    private eSidePanelPage eSidePanelPage;
    private eMainPage		eMainPage;

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
        eMainPage = new eMainPage(eDriverHandler.getDriver(FIRST_DEVICE));
        eSidePanelPage = eMainPage.openSidePage();
    }


    private void setUpSettings()
    {
        setUp();
		eAccountPage = eSidePanelPage.openAccountSettings();
    }

	@Test
	public void ForgotPasscode()
	{
		setUpSettings();
		eAccountPage.openForgotPasscodeView();
		eAccountPage.selectNew4DigitPasscode();
		eAccountPage.getBack();
		eMainPage.openSidePage().unauthorize();
		eMainPage.openSidePage().authorize("digit", getProperty("new4digitPass"));
		Assert.assertTrue(eMainPage.isAuthorized());

		eMainPage.openSidePage().openAccountSettings();
		eAccountPage.openAuthorizationSettings("digit", getNew4digitPass());
		eAccountPage.changePasscode("digit", getDefaultPasscode());
		tearDown();
	}

	@Test
	public void testAuthorization()
	{
		setUp();
		eSidePanelPage.authorize();
		Assert.assertTrue(eMainPage.isAuthorized());
		tearDown();
	}

	@Test
	void testDropAuthorization()
	{
		setUp();
		eSidePanelPage.authorize();
		Assert.assertTrue(eMainPage.isAuthorized());

		eSidePanelPage.unauthorize();
		Assert.assertFalse(eMainPage.isAuthorized());
		tearDown();
	}

	@DataProvider(name = "timeoutValues")
	public Object[] getTimeoutValues()
	{
		return new Object[]{/*"15 minutes","30 minutes","60 minutes",*/"5 minutes"};
	}

    @Test(dataProvider = "timeoutValues")
	public void testAuthorizationTimeout(String timeoutValue)
	{
        setUpSettings();
		eAccountPage.openAuthorizationTimeout();
		eAccountPage.setAuthorizationTimeout(timeoutValue);
		eAccountPage.getBack();

        eMainPage.openSidePage().authorize();
        Assert.assertTrue(eMainPage.isAuthorized());

        waitTime(timeoutValue);
        Assert.assertFalse(eMainPage.isAuthorized());
        tearDown();
    }

    @Test
    public void testDropAuthorizationOnLock()
    {
        setUpSettings();
		eAccountPage.turnOnDropAuthorizationOnLock();
        eMainPage.openSidePage().authorize();
		eMainPage.verifyDropVerificationOnLock();
		eAccountPage.turnOffDropAuthorizationOnLock();
        tearDown();
    }

	@DataProvider(name = "passCombinations")
	public Object[][] passCombination()
	{
		return new Object[][]
		{
			{"digit", getNew4digitPass()},
			{"digit", getNew6digitPass()},
			{"custom", getNewCustomNumPass()},
			{"custom", getNewCustomAlphaNumPass()}
		};
	}

	@Test(dataProvider = "passCombinations")
	public void ChangePasscode(String passType, String passValue)
	{
		setUpSettings();
		eAccountPage.openAuthorizationSettings("digit", getDefaultPasscode());
		eAccountPage.changePasscode(passType, passValue);
		eAccountPage.getBack();
		eAccountPage.getBack();

		eMainPage.openSidePage().unauthorize();
		eMainPage.openSidePage().authorize(passType, passValue);
		Assert.assertTrue(eMainPage.isAuthorized());

		eSidePanelPage = eMainPage.openSidePage();
		eAccountPage = eSidePanelPage.openAccountSettings();
		eAccountPage.openAuthorizationSettings(passType, passValue);
		eAccountPage.changePasscode("digit", getDefaultPasscode());
		eAccountPage.getBack();
		tearDown();
	}

    private void tearDown()
    {
        turnOffDrivers();
    }

}
